package com.company.controller;

import com.company.dto.*;
import com.company.service.AuthService;
import com.company.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "Authorization and Registration")
@RestController
@RequestMapping("/auth")
public class AuthController {
    //    Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthService authService;

    @ApiOperation(value = "Registration", notes = "Method for registration")
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDTO dto) {
        log.info("Request for login {}", dto);
        String response = authService.registration(dto);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "Login", notes = "Method for Authorization")
    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthDTO dto) {
        log.trace("Request for login {}", dto);
        log.debug("Request for login {}", dto);
        log.info("Request for login {}", dto);
        log.warn("Request for login {}", dto);
        log.error("Request for login {}", dto);

        ProfileDTO profileDto = authService.login(dto);
        return ResponseEntity.ok(profileDto);
    }

    @ApiOperation(value = "Sms verification", notes = "Method for Sms verification")
    @PostMapping("/verification")
    public ResponseEntity<String> login(@RequestBody VerificationDTO dto) {
        String response = authService.verification(dto);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Email verification", notes = "Method for Email verification")
    @GetMapping("/email/verification/{id}")
    public ResponseEntity<String> emailVerification(@PathVariable("id") Integer id) {
        String response = authService.emailVerification(id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Resend Sms Code", notes = "Method For resend Sms verification")
    @GetMapping("/resend/{phone}")
    public ResponseEntity<ResponseInfoDTO> resendSms(@ApiParam(value = "phone", required = true, example = "998909123456")
                                                     @PathVariable("phone") String phone) {
        ResponseInfoDTO response = authService.resendSms(phone);
        return ResponseEntity.ok(response);
    }


}
