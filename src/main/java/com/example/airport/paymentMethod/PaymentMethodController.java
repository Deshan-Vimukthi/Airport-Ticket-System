package com.example.airport.paymentMethod;

import com.example.airport.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payment-method")
public class PaymentMethodController {
    @Autowired
    private PaymentMethodRepository paymentMethodDao;

    @GetMapping("")
    public ResponseEntity<?>getPaymentMethodList(){
        List<PaymentMethod> paymentMethodList = paymentMethodDao.findAll();
        return ResponseEntity.ok(
                ApiResponse.success(paymentMethodList)
        );
    }

}
