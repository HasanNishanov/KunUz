package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.swing.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class AttachDTO {
    private String id;
    private String originalName;
    private String extension;
    private Long size;
    private String path;
    private LocalDateTime createdDate;
    private String url;
}
