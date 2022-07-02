package com.company.dto.integration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SmsRequestDTO {
    private String key;
    private String phone;
    private String message;
}
