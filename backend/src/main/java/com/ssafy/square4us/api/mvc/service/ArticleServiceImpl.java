package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.ArticleDTO;
import com.ssafy.square4us.api.mvc.model.dto.FileDTO;
import com.ssafy.square4us.api.mvc.model.entity.Article;
import com.ssafy.square4us.api.mvc.model.entity.FileEntity;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import com.ssafy.square4us.api.mvc.model.repository.*;
import com.ssafy.square4us.common.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepo;
    private final ArticleRepositorySupport articleRepositorySupport;
    private final MemberRepository memberRepo;
    private final StudyRepository studyRepo;
    private final FileRepository fileRepo;
    private final S3Util s3Util;

    @Override
    @Transactional
    public ArticleDTO createArticle(Long studyId, Long memberId, ArticleDTO.WritePostReq req) {
        Optional<Study> study = studyRepo.findById(studyId);
        Optional<Member> member = memberRepo.findById(memberId);

        if(!study.isPresent() || !member.isPresent()) {
            return null;
        }

        Article article = articleRepo.save(
                Article.builder()
                        .category(req.getCategory())
                        .content(req.getContent())
                        .member(member.get())
                        .study(study.get())
                        .title(req.getTitle())
                        .build()
        );

        article = articleRepo.save(article);

        return new ArticleDTO(article);
    }

    @Transactional(rollbackFor = IOException.class)
    public void saveFiles(Article article, MultipartFile[] files) throws IOException {
        LocalDate today = LocalDate.now();
        String path = "article/" + today.getYear() + "/" + today.getMonth() + "/" + today.getDayOfMonth();
        List<FileEntity> list = new ArrayList<>();
        for(MultipartFile file: files) {
            FileDTO fd = null;
            try {
                fd = s3Util.upload(file, path);
                FileEntity fe = FileEntity.builder()
                        .member(null)
                        .article(article)
                        .meeting(null)
                        .filePath(fd.getFilePath())
                        .fileName(fd.getFileName())
                        .fileOriginName(fd.getFileOriginName())
                        .contentType(fd.getContentType())
                        .build();

                fe = fileRepo.save(fe);
                list.add(fe);
            } catch (IOException e) {
                throw new IOException("파일 저장 실패!");
            }
        }
        article.setFiles(list);
    }

    @Override
    public PageImpl<ArticleDTO> findStudiesWithPaging(Pageable pageable, Long studyId) {
        return articleRepositorySupport.findArticlesWithPaging(pageable, studyId);
    }

    @Override
    @Transactional
    public ArticleDTO readArticle(Long articleId) {
        Article article = articleRepo.findById(articleId).get();
        if(article == null) {
            return null;
        }
        article.setHit(article.getHit() + 1);
        return new ArticleDTO(article);
    }

    @Override
    @Transactional
    public ArticleDTO getArticle(Long articleId) {
        Article article = articleRepo.findById(articleId).get();
        if(article == null) {
            return null;
        }
        return new ArticleDTO(article);
    }

    @Override
    @Transactional
    public void deleteByArticleId(Long articleId) {
        Optional<Article> article = articleRepo.findById(articleId);
        if(!article.isPresent()) {
            return;
        }
        Article art = article.get();
        deletePrevFiles(art);
        articleRepo.delete(art);
    }

    @Override
    @Transactional
    public void evalArticle(Long articleId, String what) {
        Optional<Article> article = articleRepo.findById(articleId);
        if(!article.isPresent()) {
            return;
        }
        Article art = article.get();
        if(what.equals("like")) {
            art.setGood(art.getGood() + 1);
        } else {
            art.setDislike(art.getDislike() + 1);
        }
    }

    @Override
    @Transactional
    public void updateArticle(Long articleId, ArticleDTO.WritePostReq req, MultipartFile[] files) {
        Optional<Article> article = articleRepo.findById(articleId);
        if(!article.isPresent()) {
            return;
        }
        Article art = article.get();
        deletePrevFiles(art);
        art.setCategory(req.getCategory());
        art.setTitle(req.getTitle());
        art.setContent(req.getContent());
        saveFiles(art, files);
    }

    @Override
    @Transactional
    public void uploadFiles(Long articleId, MultipartFile[] files) throws IOException {
        Optional<Article> find = articleRepo.findById(articleId);
        if(!find.isPresent()) {
            return;
        }
        saveFiles(find.get(), files);
    }

    @Transactional
    public void deletePrevFiles(Article article) {
        List<FileEntity> prevFiles = article.getFiles();
        if(prevFiles != null && prevFiles.size() > 0) {
            for(FileEntity fe: prevFiles) {
                s3Util.delete(fe);
                fileRepo.delete(fe);
            }
        }
    }
}
