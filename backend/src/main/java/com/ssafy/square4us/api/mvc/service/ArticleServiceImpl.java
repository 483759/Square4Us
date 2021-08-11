package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.ArticleDTO;
import com.ssafy.square4us.api.mvc.model.entity.Article;
import com.ssafy.square4us.api.mvc.model.entity.FileEntity;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import com.ssafy.square4us.api.mvc.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {

    private final static String BASE_PATH = System.getProperty("user.dir") + "\\article";
    private final ArticleRepository articleRepo;
    private final ArticleRepositorySupport articleRepositorySupport;
    private final MemberRepository memberRepo;
    private final StudyRepository studyRepo;
    private final FileRepository fileRepo;

    @Override
    @Transactional
    public ArticleDTO createArticle(Long studyId, Long memberId, ArticleDTO.CreatePostReq req, MultipartFile[] files) {
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

        if(files != null && files.length > 0) {
            int code = saveFiles(article, files);
            if(code == 1) {
                return null;
            }
        }

        return new ArticleDTO(article);
    }

    @Transactional
    public int saveFiles(Article article, MultipartFile[] files) {
        LocalDate today = LocalDate.now();
        String realPath = BASE_PATH + File.separator + today.getYear() + File.separator + today.getMonth() + File.separator + today.getDayOfMonth();
        File path = new File(realPath);
        if(!path.exists()) {
            path.mkdir();
        }
        List<FileEntity> list = new ArrayList<>();
        for(MultipartFile file: files) {
            String originName = file.getOriginalFilename();
            String contentType = file.getContentType();
            String uuid = UUID.randomUUID().toString();
            String saveName = uuid + originName.substring(originName.lastIndexOf('.'));

            FileEntity fe = FileEntity.builder()
                    .member(null)
                    .study(null)
                    .article(article)
                    .meeting(null)
                    .filePath(realPath)
                    .fileName(saveName)
                    .fileOriginName(originName)
                    .contentType(contentType)
                    .build();

            fe = fileRepo.save(fe);
            list.add(fe);
            try {
                file.transferTo(new File(realPath, saveName));
            } catch(IOException e) {
                return 1;
            }
        }
        article.setFiles(list);
        return 2;
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
    public void updateArticle(Long articleId, ArticleDTO.CreatePostReq req, MultipartFile[] files) {
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

    @Transactional
    public void deletePrevFiles(Article article) {
        List<FileEntity> prevFiles = article.getFiles();
        if(prevFiles != null && prevFiles.size() > 0) {
            for(FileEntity fe: prevFiles) {
                File cur = new File(fe.getFilePath(), fe.getFileName());
                cur.delete();
                fileRepo.delete(fe);
            }
        }
    }
}
