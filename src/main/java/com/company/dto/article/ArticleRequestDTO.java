package com.company.dto.article;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@Setter
public class ArticleRequestDTO {
    private List<String> idList;
}
