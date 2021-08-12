package com.ssafy.square4us.api.mvc.model.dto;

import com.ssafy.square4us.api.mvc.model.entity.FileEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileDTO {
    private Long id;
    private String fileName;
    private String filePath;
    private String fileOriginName;
    private String contentType;

    public FileDTO(FileEntity file) {
        this.id = file.getId();
        this.fileName = file.getFileName();
        this.filePath = file.getFilePath();
        this.fileOriginName = file.getFileOriginName();
        this.contentType = file.getContentType();
    }

    @Builder
    public FileDTO(Long id, String fileName, String filePath, String fileOriginName, String contentType) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
        this.contentType = contentType;
    }
}
