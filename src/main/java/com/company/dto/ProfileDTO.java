package com.company.dto;

import com.company.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Integer id;
    private String name;
    private String surName;

    @Email(message = "Email should be valid", regexp = "^[a-zA-Z0-9._%+-]+@[a-zAx-Z0-9.-]+\\\\.[A-Z]{2,6}$")
    private String email;
    private ProfileRole role;
    private String password;

    private String jwt;

    private String photoId;

}
