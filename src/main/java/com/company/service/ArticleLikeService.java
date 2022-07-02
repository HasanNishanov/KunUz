package com.company.service;

import com.company.dto.article.ArticleDTO;
import com.company.dto.article.ArticleLikeDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.ArticleLikeEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ArticleStatus;
import com.company.enums.LikeStatus;
import com.company.exps.ItemNotFoundException;
import com.company.repository.ArticleLikeRepository;
import com.company.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ArticleLikeService {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public void articleLike(String articleId, Integer pId) {
        log.info("Request for articleLike {}");
        likeDislike(articleId, pId, LikeStatus.LIKE);
    }

    public void articleDisLike(String articleId, Integer pId) {
        log.info("Request for articleDisLike {}");
        likeDislike(articleId, pId, LikeStatus.DISLIKE);
    }

    private void likeDislike(String articleId, Integer pId, LikeStatus status) {
        log.info("Request for likeDislike {}");
        Optional<ArticleLikeEntity> optional = articleLikeRepository.findExists(articleId, pId);
        if (optional.isPresent()) {
            ArticleLikeEntity like = optional.get();
            like.setStatus(status);
            articleLikeRepository.save(like);
            return;
        }
        boolean articleExists = articleRepository.existsById(articleId);
        if (!articleExists) {
            throw new ItemNotFoundException("Article NotFound");
        }

        ArticleLikeEntity like = new ArticleLikeEntity();
        like.setArticle(new ArticleEntity(articleId));
        like.setProfile(new ProfileEntity(pId));
        like.setStatus(status);
        articleLikeRepository.save(like);
    }

    public void removeLike(String articleId, Integer pId) {
        log.info("Request for removeLike {}");
       /* Optional<ArticleLikeEntity> optional = articleLikeRepository.findExists(articleId, pId);
        optional.ifPresent(articleLikeEntity -> {
            articleLikeRepository.delete(articleLikeEntity);
        });*/
        articleLikeRepository.delete(articleId, pId);
    }

    public ArticleLikeDTO likeCountAndDislikeCount(String articleId) {
        log.info("Request for likeCountAndDislikeCount {}");
//        int like = articleLikeRepository.countByArticleAndStatus(new ArticleEntity(articleId), LikeStatus.LIKE);
//        int dislike = articleLikeRepository.countByArticle(articleId, LikeStatus.DISLIKE);

        Map<String, Integer> map = articleLikeRepository.countByArticleNative(articleId);

        ArticleLikeDTO dto = new ArticleLikeDTO();
        dto.setLikeCount(map.get("like_count"));
        dto.setDislikeCount(map.get("dislike_count"));
        return dto;
    }
}