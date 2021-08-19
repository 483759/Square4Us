package com.ssafy.square4us.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;
import java.util.UUID;

import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.ssafy.square4us.api.mvc.model.dto.FileDTO;
import com.ssafy.square4us.api.mvc.model.entity.FileEntity;
import com.ssafy.square4us.api.mvc.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class S3Util {

    private final AmazonS3Client amazonS3Client;
    private final FileService fileService;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public FileDTO upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
        String uploadImageUrl = upload(uploadFile, dirName);
        int idx = uploadImageUrl.lastIndexOf('/');
        FileDTO f = FileDTO.builder()
                        .id(null)
                        .contentType(multipartFile.getContentType())
                        .fileOriginName(multipartFile.getOriginalFilename())
                        .filePath(uploadImageUrl.substring(0, idx))
                        .fileName(uploadImageUrl.substring(idx + 1))
                        .build();
        return f;
    }

    public ResponseEntity<byte[]> download(Long fileId) throws IOException {
        FileEntity file = fileService.findById(fileId);
        StringBuilder sb = new StringBuilder();
        sb.append(file.getFilePath().split(".com/")[1]).append('/').append(file.getFileName());
        String fullFilePath = sb.toString();
        return download(fullFilePath, file.getFileOriginName());
    }

    public ResponseEntity<byte[]> download(String name, String originName) throws IOException {
        S3Object o = amazonS3Client.getObject(new GetObjectRequest(bucket, name));
        S3ObjectInputStream ois = o.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(ois);

        String fileName = URLEncoder.encode(originName, "UTF-8").replaceAll("\\+", "%20");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    public void delete(FileEntity fe) {
        StringBuilder sb = new StringBuilder();
        sb.append(fe.getFilePath().split(".com/")[1]).append("/").append(fe.getFileName());
        delete(sb.toString());
    }

    public void delete(String fullFilePath) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fullFilePath));
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName().substring(uploadFile.getName().lastIndexOf('.'));
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" +
                file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try(FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

}
