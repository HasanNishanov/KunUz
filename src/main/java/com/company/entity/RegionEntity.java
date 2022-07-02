package com.company.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "region")
public class RegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String key;

    @Column(nullable = false, name = "name_uz")
    private String nameUz;

    @Column(nullable = false, name = "name_ru")
    private String nameRu;

    @Column(nullable = false, name = "name_en")
    private String nameEn;

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;


    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();


}
