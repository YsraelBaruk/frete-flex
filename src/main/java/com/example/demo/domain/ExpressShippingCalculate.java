package com.example.demo.domain;

import org.springframework.stereotype.Component;

@Component(value = "expressCalculator")
public class ExpressShippingCalculate implements ShippingCalculator {
    @Override 
    public Double calculate(Double distance, Double weight){
        return weight * 1.5 + distance * 0.75;
    }
}
