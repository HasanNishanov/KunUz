package com.company.repository;

import com.company.entity.CategoryEntity;
import com.company.entity.RegionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

    Optional<CategoryEntity> findByKey(String key);

    List<CategoryEntity> findAllByVisible(Boolean b);


}
