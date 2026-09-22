package com.ibm.digicusthub.service;

import com.ibm.digicusthub.dto.OtpValidateDTO;
import com.ibm.digicusthub.entity.CustomerDetailsEntity;
import com.ibm.digicusthub.entity.OtpDetailsEntity;
import com.ibm.digicusthub.enums.VerificationTypeEnum;
import com.ibm.digicusthub.exception.UserExistsException;
import com.ibm.digicusthub.repository.CustomerDetailsRepository;
import com.ibm.digicusthub.repository.OtpDetailsRepository;
import com.ibm.digicusthub.utility.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OtpDetailsService {


    private final OtpDetailsRepository otpDetailsRepository;
    private final CustomerDetailsRepository customerDetailsRepository;

    public OtpDetailsService(com.ibm.digicusthub.repository.OtpDetailsRepository otpDetailsRepository, CustomerDetailsRepository customerDetailsRepository) {
        this.otpDetailsRepository = otpDetailsRepository;
        this.customerDetailsRepository = customerDetailsRepository;
    }


    /**
     * Service to generate and send OTP to mobile and email
     * @param savedCustomer
     */
    public void sendAndSaveOtpData(CustomerDetailsEntity savedCustomer) {
        OtpDetailsEntity mobileOtp = createOtp(savedCustomer, VerificationTypeEnum.MOBILE);
        OtpDetailsEntity emailOtp  = createOtp(savedCustomer, VerificationTypeEnum.EMAIL);

        otpDetailsRepository.saveAll(List.of(mobileOtp, emailOtp));

        // call third-party API to send OTP (mobile/email)
    }

    private OtpDetailsEntity createOtp(CustomerDetailsEntity customer,
                                       VerificationTypeEnum type) {

        OtpDetailsEntity otp = new OtpDetailsEntity();
        otp.setOtp(Integer.parseInt(AppUtil.generateOtp()));
        otp.setEnabled(true);
        otp.setExpiry(LocalDateTime.now().plusMinutes(10));
        otp.setOtpType(type);
        otp.setCustomer(customer);

        return otp;
    }


    /*
        * Service to validate OTP for email and mobile
        * @param dto
        * @return
     */
    public String validateOtp(OtpValidateDTO dto) {
        log.info("Validating OTP for type: {}", dto.getOtpType());

        VerificationTypeEnum verificationType;
        switch (dto.getOtpType().toLowerCase()) {
            case "mobile":
                verificationType = VerificationTypeEnum.MOBILE;
                break;
            case "email":
                verificationType = VerificationTypeEnum.EMAIL;
                break;
            default:
                return "INVALID OTP TYPE";
        }

        CustomerDetailsEntity customer = getCustomer(dto.getIdentityData(), verificationType);
        return validateOtpForCustomer(String.valueOf(dto.getOtpData()), customer, verificationType);
    }

    private CustomerDetailsEntity getCustomer(String identity, VerificationTypeEnum type) {
        Optional<CustomerDetailsEntity> customerOpt =
                type == VerificationTypeEnum.MOBILE
                        ? customerDetailsRepository.findByMobNo(identity)
                        : customerDetailsRepository.findByEmail(identity);

        return customerOpt.orElseThrow(() ->
                new UserExistsException("User not found with " + type.name().toLowerCase() + ": " + identity)
        );
    }

    private String validateOtpForCustomer(String providedOtp, CustomerDetailsEntity customer, VerificationTypeEnum type) {
        OtpDetailsEntity otpData =
                otpDetailsRepository.findOtpByCustomerAndType(type, customer);

        if (otpData == null || !otpData.isEnabled()) {
            return "OTP EXPIRED OR INVALID";
        }

        if (!providedOtp.equals(String.valueOf(otpData.getOtp()))) {
            return "INVALID OTP";
        }

        otpData.setEnabled(false);

        if (type == VerificationTypeEnum.MOBILE) {
            customer.setMobileVerified(true);
        } else {
            customer.setEmailVerified(true);
        }

        otpDetailsRepository.save(otpData);
        return "OTP VALIDATED";
    }


}
