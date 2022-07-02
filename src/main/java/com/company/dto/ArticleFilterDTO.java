package com.company.dto;

import com.company.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleFilterDTO {

    private String id;
    private String title;
    private String description;
    private Integer regionId;
    private Integer categoryId;

    private String publishedDateFrom; // yyyy-MM-dd
    private String publishedDateTo;

    private Integer moderatorId;
    private Integer publisherId;

    private ArticleStatus status;
}
