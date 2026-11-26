package com.example.demo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.domain.ExpressShippingCalculate;
import com.example.demo.domain.StandardShippingCalculator;

@Service 
public class ShippingService{

    private final StandardShippingCalculator standardShippingCalculator;
    private final ExpressShippingCalculate expressShippingCalculate;
    
    public ShippingService(StandardShippingCalculator standardShippingCalculator, ExpressShippingCalculate expressShippingCalculate) {
        this.standardShippingCalculator = standardShippingCalculator;
        this.expressShippingCalculate = expressShippingCalculate;
    }

    public Double calculate(String shippingType, Double distance, Double weight){
        if (shippingType.equalsIgnoreCase("standard")){
            return standardShippingCalculator.calculate(distance, weight);
        } else if (shippingType.equalsIgnoreCase("express")) {
            return expressShippingCalculate.calculate(distance, weight);
        }
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Tipo de frete invalido. Use 'standard' ou 'express'.");
    }
}
