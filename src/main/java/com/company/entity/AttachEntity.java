package com.company.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "attach")
public class AttachEntity {
    //    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Id
    private String id;
    @Column(nullable = false)
    private String originalName;
    @Column(nullable = false)
    private String extension;
    @Column(nullable = false)
    private Long size;
    @Column(nullable = false)
    private String path;
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    public AttachEntity() {
    }

    public AttachEntity(String id) {
        this.id = id;
    }
}