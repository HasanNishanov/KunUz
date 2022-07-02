package com.company.service;

import com.company.entity.ArticleEntity;
import com.company.entity.ArticleTypeEntity;
import com.company.entity.TypesEntity;
import com.company.repository.ArticleTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public void create(ArticleEntity article, List<Integer> typesList) {
        log.info("Request for create {}");
        for (Integer typesId : typesList) {
            ArticleTypeEntity articleTypeEntity = new ArticleTypeEntity();
            articleTypeEntity.setArticle(article);
            articleTypeEntity.setTypes(new TypesEntity(typesId));
            articleTypeRepository.save(articleTypeEntity);
        }
    }

}
