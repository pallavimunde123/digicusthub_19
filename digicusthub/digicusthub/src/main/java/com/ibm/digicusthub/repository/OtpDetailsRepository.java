package com.ibm.digicusthub.repository;

import com.ibm.digicusthub.entity.CustomerDetailsEntity;
import com.ibm.digicusthub.entity.OtpDetailsEntity;
import com.ibm.digicusthub.enums.VerificationTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpDetailsRepository extends JpaRepository<OtpDetailsEntity,Long> {

    @Query(value = "select a from OtpDetailsEntity a where a.isEnabled= true and " +
            "a.otpType = :otpType and a.customer = :customerDetails")
    public OtpDetailsEntity findOtpByCustomerAndType(VerificationTypeEnum otpType, CustomerDetailsEntity customerDetails);
}
