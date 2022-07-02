package com.company.dto.article;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class ArticleCreateDTO {

    @NotNull(message = "Required argument")
    @Size(min = 10, max = 255, message = "About Me must be between 10 and 255 characters")
    private String title;

    @NotNull(message = "Mazgi content qani?")
    private String content;
    @NotBlank(message = "Description  qani?")
    private String description;

    private Integer regionId;
    private Integer categoryId;

    private List<Integer> typesList;
    private List<String> tagList;

    private String imageId;
}
