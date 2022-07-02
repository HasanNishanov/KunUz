package com.company.dto;

import com.company.dto.article.TypesDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TypesPaginationDTO {
    private long totalAmount;
    private List<TypesDTO> list;
}
