package com.ibm.digicusthub.entity;

import com.ibm.digicusthub.enums.VerificationTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="otp_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtpDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int otp;

    @Enumerated(EnumType.STRING)
    private VerificationTypeEnum otpType;

    private LocalDateTime expiry;

    private boolean isEnabled;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cust_id", referencedColumnName = "id")
    private CustomerDetailsEntity customer;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public VerificationTypeEnum getOtpType() {
        return otpType;
    }

    public void setOtpType(VerificationTypeEnum otpType) {
        this.otpType = otpType;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public CustomerDetailsEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDetailsEntity customer) {
        this.customer = customer;
    }
}
