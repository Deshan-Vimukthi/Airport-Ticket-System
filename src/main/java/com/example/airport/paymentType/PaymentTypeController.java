package com.example.airport.paymentType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentTypeController {
    @Autowired
    private PaymentTypeRepository paymentTypeDao;
}
