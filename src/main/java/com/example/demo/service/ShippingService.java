package com.example.demo.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.domain.ShippingCalculator;

@Service 
public class ShippingService{

    private final ShippingCalculator standardCalculator;
    private final ShippingCalculator expressCalculator;
    
    public ShippingService(
        @Qualifier("standardCalculator") ShippingCalculator standardCalculator, 
        @Qualifier("expressCalculator") ShippingCalculator expressCalculator) {
        this.standardCalculator = standardCalculator;
        this.expressCalculator = expressCalculator;
    }

    public Double calculate(String shippingType, Double distance, Double weight){
        if (shippingType.equalsIgnoreCase("standard")){
            return standardCalculator.calculate(distance, weight);
        } else if (shippingType.equalsIgnoreCase("express")) {
            return expressCalculator.calculate(distance, weight);
        }
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Tipo de frete invalido. Use 'standard' ou 'express'.");
    }
}
