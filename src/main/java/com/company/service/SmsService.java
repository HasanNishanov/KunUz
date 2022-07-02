package com.company.service;

import com.company.dto.integration.SmsRequestDTO;
import com.company.dto.integration.SmsResponseDTO;
import com.company.entity.SmsEntity;
import com.company.repository.SmsRepository;
import com.company.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;
@Slf4j
@Service
public class SmsService {
    @Value("${sms.url}")
    private String smsUrl;
    @Value("${sms.key}")
    private String key;
    @Autowired
    private SmsRepository smsRepository;


    public void sendRegistrationSms(String phone) {
        log.info("Request for sendRegistrationSms {}");
        String code = RandomUtil.getRandomSmsCode();
        String message = "Kun.uz Test partali uchun\n registratsiya kodi: " + code;

//        SmsResponseDTO responseDTO = send(phone, message);
        SmsResponseDTO responseDTO = new SmsResponseDTO();
        responseDTO.setSuccess(Boolean.TRUE);

        SmsEntity entity = new SmsEntity();
        entity.setPhone(phone);
        entity.setCode(code);
        entity.setStatus(responseDTO.getSuccess());

        smsRepository.save(entity);
    }


    public Long getSmsCount(String phone){
        log.info("Request for getSmsCount {}");
        return smsRepository.getSmsCount(phone);
    }

    public boolean verifySms(String phone, String code) {
        log.info("Request for verifySms {}");
        Optional<SmsEntity> optional = smsRepository.findTopByPhoneOrderByCreatedDateDesc(phone);
        if (optional.isEmpty()) {
            return false;
        }
        SmsEntity sms = optional.get();
        LocalDateTime validDate = sms.getCreatedDate().plusMinutes(1);

        if (sms.getCode().equals(code) && validDate.isAfter(LocalDateTime.now())) {
            return true;
        }
        return false;
    }

    private SmsResponseDTO send(String phone, String message) {
        log.info("Request for send {}");
        SmsRequestDTO requestDTO = new SmsRequestDTO();
        requestDTO.setKey(key);
        requestDTO.setPhone(phone);
        requestDTO.setMessage(message);
        System.out.println("Sms Request: message " + message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SmsRequestDTO> entity = new HttpEntity<SmsRequestDTO>(requestDTO, headers);

        RestTemplate restTemplate = new RestTemplate();
        SmsResponseDTO response = restTemplate.postForObject(smsUrl, entity, SmsResponseDTO.class);
        System.out.println("Sms Response  " + response);
        return response;
    }


}
