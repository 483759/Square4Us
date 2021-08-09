package com.ssafy.square4us.api.mvc.service;

import com.ssafy.square4us.api.mvc.model.dto.ArticleDTO;
import com.ssafy.square4us.api.mvc.model.entity.Article;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.entity.Study;
import com.ssafy.square4us.api.mvc.model.repository.ArticleRepository;
import com.ssafy.square4us.api.mvc.model.repository.ArticleRepositorySupport;
import com.ssafy.square4us.api.mvc.model.repository.MemberRepository;
import com.ssafy.square4us.api.mvc.model.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepo;
    private final ArticleRepositorySupport articleRepositorySupport;
    private final MemberRepository memberRepo;
    private final StudyRepository studyRepo;

    @Override
    @Transactional
    public ArticleDTO createArticle(Long studyId, Long memberId, ArticleDTO.CreatePostReq req) {
        Optional<Study> study = studyRepo.findById(studyId);
        Optional<Member> member = memberRepo.findById(memberId);

        if(!study.isPresent() || !member.isPresent()) {
            return null;
        }

        System.out.println(member.get().getId());
        System.out.println(study.get().getId());
        System.out.println(req.getCategory());
        System.out.println(req.getTitle());
        System.out.println(req.getContent());

        Article article = articleRepo.save(
                Article.builder()
                        .category(req.getCategory())
                        .content(req.getContent())
                        .member(member.get())
                        .study(study.get())
                        .title(req.getTitle())
                        .build()
        );

        return new ArticleDTO(article);
    }

    @Override
    public PageImpl<ArticleDTO> findStudiesWithPaging(Pageable pageable, Long studyId) {
        return articleRepositorySupport.findArticlesWithPaging(pageable, studyId);
    }

    @Override
    public ArticleDTO readArticle(Long articleId) {
        Optional<Article> article = articleRepo.findById(articleId);
        if(!article.isPresent()) {
            return null;
        }
        return new ArticleDTO(article.get());
    }

    @Override
    public void deleteByArticleId(Long articleId) {
        articleRepo.deleteById(articleId);
    }
}
