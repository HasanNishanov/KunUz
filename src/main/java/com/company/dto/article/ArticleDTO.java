package com.company.dto.article;

import com.company.dto.CategoryDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.RegionDTO;
import com.company.entity.CategoryEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleDTO {
    private String id;

    private String title;

    private String content;

    private RegionDTO region;

    private CategoryDTO category;

    private ProfileDTO moderator;

    private ProfileDTO publisher;

    private String description;

    private LocalDateTime createdDate;

    private LocalDateTime publishDate;

    private Integer viewCount;

    private Integer sharedCount;

    private ArticleStatus status;

    private Boolean visible = Boolean.TRUE;

    private ArticleLikeDTO like;



}
