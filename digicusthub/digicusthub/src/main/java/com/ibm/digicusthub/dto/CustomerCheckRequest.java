package com.ibm.digicusthub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class CustomerCheckRequest {

    private String email;
    private String mobNo;
    private String panCard;

    private String firstName;
    private String middleName;
    private String lastName;
    private Date dob;
    private String gender;




}
