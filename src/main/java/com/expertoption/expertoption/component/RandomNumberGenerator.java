package com.expertoption.expertoption.component;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomNumberGenerator {
    @Bean
    public Double generateNumber() {
        Random random = new Random();
        double randomNumber = random.nextDouble();
        return Math.round(randomNumber * 100.0) / 100.0;
    }
}
