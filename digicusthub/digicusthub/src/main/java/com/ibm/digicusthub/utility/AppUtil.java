package com.ibm.digicusthub.utility;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Random;

public class AppUtil {

    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateOtp() {
        String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
        return otp;
    }

}
