package com.ibm.digicusthub.enums;

import org.springframework.stereotype.Component;


public enum VerificationTypeEnum {
    EMAIL("email"),
    MOBILE("mobile");

    private final String type;

     VerificationTypeEnum(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }


}
