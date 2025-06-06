package com.example.airport.paymentMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payment-method")
public class PaymentMethodController {
    @Autowired
    private PaymentMethodRepository paymentMethodDao;

    @GetMapping("/list/data")
    public List<PaymentMethod> getPaymentMethodList(){
        return paymentMethodDao.findAll();
    }

}
