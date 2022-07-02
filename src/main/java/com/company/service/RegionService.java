package com.company.service;

import com.company.dto.RegionDTO;
import com.company.entity.RegionEntity;
import com.company.enums.LangEnum;
import com.company.exps.AlreadyExist;
import com.company.exps.BadRequestException;
import com.company.exps.ItemNotFoundException;
import com.company.repository.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public void create(RegionDTO regionDto) {
        log.info("Request for createRegion {}");
        Optional<RegionEntity> region = regionRepository.findByKey(regionDto.getKey());

        if (region.isPresent()) {
            throw new AlreadyExist("Already exist");
        }

        isValid(regionDto);

        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setKey(regionDto.getKey());
        regionEntity.setNameUz(regionDto.getNameUz());
        regionEntity.setNameRu(regionDto.getNameRu());
        regionEntity.setNameEn(regionDto.getNameEn());

        regionRepository.save(regionEntity);
    }

    private void isValid(RegionDTO dto) {
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

    public List<RegionDTO> getList() {
        log.info("Request for getList {}");

        Iterable<RegionEntity> all = regionRepository.findAllByVisible(true);
        List<RegionDTO> dtoList = new LinkedList<>();

        all.forEach(regionEntity -> {
            RegionDTO dto = new RegionDTO();
            dto.setKey(regionEntity.getKey());
            dto.setNameUz(regionEntity.getNameUz());
            dto.setNameRu(regionEntity.getNameRu());
            dto.setNameEn(regionEntity.getNameEn());
            dtoList.add(dto);
        });
        return dtoList;
    }

    public RegionEntity get(Integer id) {
        log.info("Request for getRegion {}");
        return regionRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Region not found");
        });
    }

    public List<RegionDTO> getListOnlyForAdmin() {
        log.info("Request for getListOnlyForAdmin {}");
        Iterable<RegionEntity> all = regionRepository.findAll();
        List<RegionDTO> dtoList = new LinkedList<>();

        all.forEach(regionEntity -> {
            RegionDTO dto = new RegionDTO();
            dto.setKey(regionEntity.getKey());
            dto.setNameUz(regionEntity.getNameUz());
            dto.setNameRu(regionEntity.getNameRu());
            dto.setNameEn(regionEntity.getNameEn());
            dtoList.add(dto);
        });
        return dtoList;
    }

    public void update(Integer id, RegionDTO dto) {
        log.info("Request for updateRegion {}");
        Optional<RegionEntity> regionEntity = regionRepository.findById(id);

        if (regionEntity.isEmpty()) {
            throw new ItemNotFoundException("not found region");
        }

        if (regionEntity.get().getVisible().equals(Boolean.FALSE)) {
            throw new BadRequestException("is visible false");
        }

        RegionEntity entity = regionEntity.get();

        entity.setKey(dto.getKey());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        regionRepository.save(entity);
    }

    public void delete(Integer id) {
        log.info("Request for deleteRegion {}");
        Optional<RegionEntity> regionEntity = regionRepository.findById(id);

        if (regionEntity.isEmpty()) {
            throw new ItemNotFoundException("not found region");
        }

        if (regionEntity.get().getVisible().equals(Boolean.FALSE)) {
            throw new AlreadyExist("this region already visible false");
        }

        RegionEntity region = regionEntity.get();

        region.setVisible(Boolean.FALSE);

        regionRepository.save(region);
    }

    public RegionDTO get(RegionEntity entity, LangEnum lang) {
        log.info("Request for getRegion {}");
        RegionDTO dto = new RegionDTO();
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
}
