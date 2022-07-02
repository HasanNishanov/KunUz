package com.company.repository;

import com.company.entity.ArticleTagEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArticleTagRepository extends CrudRepository<ArticleTagEntity, Integer> {

}
