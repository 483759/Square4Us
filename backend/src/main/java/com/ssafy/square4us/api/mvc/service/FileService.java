package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.entity.FileEntity;

public interface FileService {
    FileEntity findById(Long fileId);
}
