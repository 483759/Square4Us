package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.entity.FileEntity;
import com.ssafy.square4us.api.mvc.model.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

    private final FileRepository fileRepo;

    @Override
    @Transactional
    public FileEntity findById(Long fileId) {
        Optional<FileEntity> find = fileRepo.findById(fileId);
        if(find.isPresent()) {
            return find.get();
        }
        return null;
    }
}
