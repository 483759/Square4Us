package com.ssafy.square4us.api.mvc.controller;

import com.ssafy.square4us.api.mvc.model.dto.FileDTO;
import com.ssafy.square4us.api.mvc.model.dto.ResponseFactory;
import com.ssafy.square4us.api.mvc.service.FileService;
import com.ssafy.square4us.common.util.S3Util;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/download")
@RequiredArgsConstructor
public class FileController {

    private final S3Util s3Util;

    @GetMapping("{fileId}")
    public ResponseEntity<byte[]> download(@PathVariable("fileId") @Parameter(description = "다운로드 받을 파일 id", required = true) Long fileId) throws IOException {
        return s3Util.download(fileId);
    }
}
