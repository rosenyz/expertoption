package com.expertoption.expertoption.component;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;

@Component
public class RandomKeyGenerator {

    @Bean
    public String generateRandomKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[12];
        secureRandom.nextBytes(randomBytes);
        return new BigInteger(1, randomBytes).toString(12).substring(0, 12);
    }
}
