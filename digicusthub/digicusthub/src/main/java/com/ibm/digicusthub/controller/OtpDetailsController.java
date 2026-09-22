package com.ibm.digicusthub.controller;

import com.ibm.digicusthub.dto.OtpValidateDTO;
import com.ibm.digicusthub.service.OtpDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/otp")
@Slf4j
public class OtpDetailsController {

    private final OtpDetailsService otpDetailsService;

    public OtpDetailsController(OtpDetailsService otpDetailsService) {
        this.otpDetailsService = otpDetailsService;
    }

    /**
     * OTP validation for email and mobile
     * @param otpValidateDTO
     * @return
     */

    @GetMapping("/validate-otp")
    public ResponseEntity<?>validateOtp(@RequestBody OtpValidateDTO otpValidateDTO){
        log.info("Received OTP validation request: {}", otpValidateDTO);
       String response= otpDetailsService.validateOtp(otpValidateDTO);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
