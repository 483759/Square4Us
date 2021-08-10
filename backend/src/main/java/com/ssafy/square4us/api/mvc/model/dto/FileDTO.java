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

    @Builder
    public FileDTO(FileEntity file) {
        this.id = file.getId();
        this.fileName = file.getFileName();
        this.filePath = file.getFilePath();
        this.fileOriginName = file.getFileOriginName();
        this.contentType = file.getContentType();
    }
}
