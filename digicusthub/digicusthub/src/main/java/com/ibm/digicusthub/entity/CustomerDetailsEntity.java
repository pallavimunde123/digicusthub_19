package com.ibm.digicusthub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="customer_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String middleName;
    private String lastName;

    private Date dob;

    private String gender;

    @Column(unique = true)
    private String panCardDetails;

    @Column(unique = true)
    private String aadharCardDetails;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String mobNo;

    private boolean isMobileVerified;
    private boolean isEmailVerified;

    @Lob
    @Column(name="aadhar_docs", columnDefinition = "LONGBLOB")
    private byte[] aadharDocs;

    @Lob
    @Column(name="pancard_docs", columnDefinition = "LONGBLOB")
    private byte[] pancardDocs;

    @Lob
    @Column(name="addressVerification_docs", columnDefinition = "LONGBLOB")
    private byte[] addressDocs;

    @Lob
    @Column(name="signature", columnDefinition = "LONGBLOB")
    private byte[] signature;


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<OtpDetailsEntity> otpDetails;




}
