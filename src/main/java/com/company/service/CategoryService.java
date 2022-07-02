package com.company.service;

import com.company.dto.CategoryDTO;
import com.company.dto.RegionDTO;
import com.company.entity.CategoryEntity;
import com.company.entity.RegionEntity;
import com.company.enums.LangEnum;
import com.company.exps.AlreadyExist;
import com.company.exps.BadRequestException;
import com.company.exps.ItemNotFoundException;
import com.company.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public void create(CategoryDTO categoryDto) {

        Optional<CategoryEntity> category = categoryRepository.findByKey(categoryDto.getKey());

        if (category.isPresent()) {
            throw new AlreadyExist("Already exist");
        }

        isValid(categoryDto);

        CategoryEntity entity = new CategoryEntity();
        entity.setKey(categoryDto.getKey());
        entity.setNameUz(categoryDto.getNameUz());
        entity.setNameRu(categoryDto.getNameRu());
        entity.setNameEn(categoryDto.getNameEn());

        categoryRepository.save(entity);
    }

    public List<CategoryDTO> getList() {

        Iterable<CategoryEntity> all = categoryRepository.findAllByVisible(true);
        List<CategoryDTO> dtoList = new LinkedList<>();

        all.forEach(categoryEntity -> {
            dtoList.add(toDTO(categoryEntity));
        });
        return dtoList;
    }

    public List<CategoryDTO> getListOnlyForAdmin() {

        Iterable<CategoryEntity> all = categoryRepository.findAll();
        List<CategoryDTO> dtoList = new LinkedList<>();

        all.forEach(categoryEntity -> {
            dtoList.add(toDTO(categoryEntity));
        });
        return dtoList;
    }

    public void update(Integer id, CategoryDTO dto) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);

        if (categoryEntity.isEmpty()) {
            throw new ItemNotFoundException("not found category");
        }

        CategoryEntity entity = categoryEntity.get();
        entity.setKey(dto.getKey());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        categoryRepository.save(entity);
    }

    public void delete(Integer id) {

        Optional<CategoryEntity> entity = categoryRepository.findById(id);

        if (entity.isEmpty()) {
            throw new ItemNotFoundException("not found category");
        }

        if (entity.get().getVisible().equals(Boolean.FALSE)) {
            throw new AlreadyExist("this category already visible false");
        }

        CategoryEntity category = entity.get();
        category.setVisible(Boolean.FALSE);
        categoryRepository.save(category);
    }

    public CategoryEntity get(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Region not found");
        });
    }

    public CategoryEntity get(String key) {
        return categoryRepository.findByKey(key).orElseThrow(() -> {
            throw new ItemNotFoundException("Region not found");
        });
    }
    public CategoryDTO get(CategoryEntity entity, LangEnum lang) {
        CategoryDTO dto = new CategoryDTO();
        dto.setKey(entity.getKey());
        switch (lang) {
            case ru:
                dto.setName(entity.getNameRu());
                break;
            case en:
                dto.setName(entity.getNameEn());
                break;
            case uz:
                dto.setName(entity.getNameUz());
                break;
        }
        return dto;
    }
    public CategoryDTO toDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setKey(entity.getKey());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        return dto;
    }

    private void isValid(CategoryDTO dto) {
        if (dto.getKey().length() < 5) {
            throw new BadRequestException("key to short");
        }

        if (dto.getNameUz() == null || dto.getNameUz().length() < 3) {
            throw new BadRequestException("wrong name uz");
        }

        if (dto.getNameRu() == null || dto.getNameRu().length() < 3) {
            throw new BadRequestException("wrong name ru");
        }

        if (dto.getNameEn() == null || dto.getNameEn().length() < 3) {
            throw new BadRequestException("wrong name en");
        }
    }

}
