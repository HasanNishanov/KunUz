package com.company.service;

import com.company.controller.AuthController;
import com.company.dto.*;
import com.company.entity.ProfileEntity;
import com.company.entity.SmsEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exps.BadRequestException;
import com.company.exps.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.repository.SmsRepository;
import com.company.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private SmsService smsService;
    @Autowired
    private SmsRepository smsRepository;
    @Autowired
    private EmailService emailService;

    public ProfileDTO login(AuthDTO authDTO) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(authDTO.getEmail());
        if (authDTO != null) {
            log.error("Item not Found");
            throw new RuntimeException("Item Not Found");
        }
        if (optional.isEmpty()) {
            throw new BadRequestException("User not found");
        }
        ProfileEntity profile = optional.get();
        if (!profile.getPassword().equals(authDTO.getPassword())) {
            throw new BadRequestException("User not found");
        }

        if (!profile.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new BadRequestException("No ruxsat");
        }

        ProfileDTO dto = new ProfileDTO();
        dto.setName(profile.getName());
        dto.setSurName(profile.getSurname());
        dto.setJwt(JwtUtil.encode(profile.getId(), profile.getRole()));


        return dto;
    }

    public String registration(RegistrationDTO dto) {
        log.info("Request for registration {}");
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new BadRequestException("User already exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setStatus(ProfileStatus.NOT_ACTIVE);
        entity.setRole(ProfileRole.USER);
        entity.setPhone(dto.getPhone());
        profileRepository.save(entity);

        // smsService.sendRegistrationSms(dto.getPhone());
        emailService.sendRegistrationEmail(entity.getEmail(), entity.getId());

        return "Message was send";
    }

    public String verification(VerificationDTO dto) {
        log.info("Request for verification {}");
        Optional<SmsEntity> optional = smsRepository.findTopByPhoneOrderByCreatedDateDesc(dto.getPhone());
        if (optional.isEmpty()) {
            return "Phone Not Found";
        }

        SmsEntity sms = optional.get();
        LocalDateTime validDate = sms.getCreatedDate().plusMinutes(1);

        if (!sms.getCode().equals(dto.getCode())) {
            return "Code Invalid";
        }
        if (validDate.isBefore(LocalDateTime.now())) {
            return "Time is out";
        }

        profileRepository.updateStatusByPhone(dto.getPhone(), ProfileStatus.ACTIVE);
        return "Verification Done";
    }

    public ResponseInfoDTO resendSms(String phone) {
        log.info("Request for resendSms {}");
        Long count = smsService.getSmsCount(phone);
        if (count >= 4) {
            return new ResponseInfoDTO(-1, "Limit dan o'tib getgan");
        }
        smsService.sendRegistrationSms(phone);
        return new ResponseInfoDTO(1);
    }

    public String emailVerification(Integer id) {
        log.info("Request for emailVerification {}");
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            return "<h1>User Not Found</h1>";
        }

        ProfileEntity profile = optional.get();
        profile.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(profile);
        return "<h1 style='align-text:center'>Success. Tabriklaymiz.</h1>";
    }
}

