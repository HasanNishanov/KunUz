package com.company.repository;

import com.company.entity.ArticleTypeEntity;
import org.springframework.data.repository.CrudRepository;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer> {
}
