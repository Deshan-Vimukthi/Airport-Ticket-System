package com.example.airport.paymentType;

import com.example.airport.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payment-type")
public class PaymentTypeController {
    @Autowired
    private PaymentTypeRepository paymentTypeDao;

    @GetMapping("")
    public ResponseEntity<?> getPaymentTypeList(){
        List<PaymentType> paymentTypes = paymentTypeDao.findAll();
        return ResponseEntity.ok(
                ApiResponse.success(paymentTypes)
        );
    }


}
