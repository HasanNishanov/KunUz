package com.company.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class ResponseInfoDTO {
    private int status;
    private String message;

    public ResponseInfoDTO(int status) {
        this.status = status;
    }

    public ResponseInfoDTO(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
