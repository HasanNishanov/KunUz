package com.company.service;

import com.company.dto.article.TypesDTO;
import com.company.dto.RegionDTO;
import com.company.entity.TypesEntity;
import com.company.enums.LangEnum;
import com.company.exps.AlreadyExist;
import com.company.exps.BadRequestException;
import com.company.exps.ItemNotFoundException;
import com.company.repository.TypesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class TypesService {

    @Autowired
    private TypesRepository typesRepository;

    public void create(TypesDTO typesDto) {
        log.info("Request for createType {}");
        Optional<TypesEntity> articleTypeEntity = typesRepository.findByKey(typesDto.getKey());

        if (articleTypeEntity.isPresent()) {
            throw new AlreadyExist("Already exist");
        }

        isValid(typesDto);


        TypesEntity entity = new TypesEntity();
        entity.setKey(typesDto.getKey());
        entity.setNameUz(typesDto.getNameUz());
        entity.setNameRu(typesDto.getNameRu());
        entity.setNameEn(typesDto.getNameEn());

        typesRepository.save(entity);
    }

    private void isValid(TypesDTO dto) {
        log.info("Request for isValid {}");
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

    public List<TypesDTO> getList(LangEnum lang) {
        log.info("Request for getList ");
        Iterable<TypesEntity> all = typesRepository.findAllByVisible(true);
        List<TypesDTO> dtoList = new LinkedList<>();

        all.forEach(typesEntity -> {
            TypesDTO dto = new TypesDTO();
            dto.setKey(typesEntity.getKey());
            switch (lang) {
                case ru:
                    dto.setName(typesEntity.getNameRu());
                    break;
                case en:
                    dto.setName(typesEntity.getNameEn());
                    break;
                case uz:
                    dto.setName(typesEntity.getNameUz());
                    break;
            }
            dtoList.add(dto);
        });
        return dtoList;
    }

    public List<TypesDTO> getListOnlyForAdmin() {
        log.info("Request for getTypesListOnlyForAdmin {}");
        Iterable<TypesEntity> all = typesRepository.findAll();
        List<TypesDTO> dtoList = new LinkedList<>();

        all.forEach(typesEntity -> {
            TypesDTO dto = new TypesDTO();
            dto.setKey(typesEntity.getKey());
            dto.setNameUz(typesEntity.getNameUz());
            dto.setNameRu(typesEntity.getNameRu());
            dto.setNameEn(typesEntity.getNameEn());
            dtoList.add(dto);
        });
        return dtoList;
    }

    public void update(Integer id, RegionDTO dto) {
        log.info("Request for updateType {}");
        Optional<TypesEntity> articleTypeEntity = typesRepository.findById(id);

        if (articleTypeEntity.isEmpty()) {
            throw new ItemNotFoundException("not found articleType");
        }

        TypesEntity entity = articleTypeEntity.get();

        entity.setKey(dto.getKey());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        typesRepository.save(entity);
    }

    public void delete(Integer id) {
        log.info("Request for deleteType {}");
        Optional<TypesEntity> entity = typesRepository.findById(id);

        if (entity.isEmpty()) {
            throw new ItemNotFoundException("not found articleType");
        }

        if (entity.get().getVisible().equals(Boolean.FALSE)) {
            throw new AlreadyExist("this articleType already visible false");
        }

        TypesEntity articleType = entity.get();

        articleType.setVisible(Boolean.FALSE);

        typesRepository.save(articleType);
    }

    public PageImpl pagination(int page, int size) {
        log.info("Request for paginationType {}");
        // page = 1
    /*    Iterable<TypesEntity> all = typesRepository.pagination(size, size * (page - 1));
        long totalAmount = typesRepository.countAllBy();*/
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TypesEntity> all = typesRepository.findAll(pageable);

//        long totalAmount = all.getTotalElements();
//        int totalPages = all.getTotalPages();
        List<TypesEntity> list = all.getContent();

        List<TypesDTO> dtoList = new LinkedList<>();

        list.forEach(typesEntity -> {
            TypesDTO dto = new TypesDTO();
            dto.setId(typesEntity.getId());
            dto.setKey(typesEntity.getKey());
            dto.setNameUz(typesEntity.getNameUz());
            dto.setNameRu(typesEntity.getNameRu());
            dto.setNameEn(typesEntity.getNameEn());
            dtoList.add(dto);
        });

//        TypesPaginationDTO paginationDTO = new TypesPaginationDTO(totalAmount, dtoList);
//        return paginationDTO;
       return new PageImpl(dtoList,pageable, all.getTotalElements());
    }

    public List<TypesDTO> sortByName() {
        log.info("Request for sortByName {}");
        Sort sort = Sort.by(Sort.Direction.DESC, "name");
        Iterable<TypesEntity> iterable = typesRepository.findAll(sort); // order by id, name

        iterable.forEach(typesEntity -> {

        });


        return null;
    }
}
