package com.example.demo.domain;

import org.springframework.stereotype.Component;

@Component(value = "standardCalculator")
public class StandardShippingCalculator implements ShippingCalculator {
    @Override
    public Double calculate(Double distance, Double weight){
        return weight * 1 + distance * 0.5;
    }
}
