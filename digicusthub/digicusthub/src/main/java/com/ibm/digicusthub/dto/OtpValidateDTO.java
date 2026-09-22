package com.ibm.digicusthub.dto;

import lombok.Data;

@Data
public class OtpValidateDTO {

    private int otpData;    // OTP entered by user
    private String otpType; // Type of OTP (email or mobile)
    private String identityData; // Email or mobile number associated with the OTP

}
