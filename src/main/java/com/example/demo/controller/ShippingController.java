package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ShippingResponse;
import com.example.demo.service.ShippingService;


@RestController 
public class ShippingController {
    
    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @GetMapping(value="/shipping/calculate")
    public ResponseEntity<ShippingResponse> calculate(@RequestParam("type") String Shippingtype,
                                       @RequestParam("distance") Double distance,
                                       @RequestParam("weight") Double weight) {

        var cost = shippingService.calculate(Shippingtype, distance, weight);
        return ResponseEntity.ok(new ShippingResponse(cost));
    }
    
}
