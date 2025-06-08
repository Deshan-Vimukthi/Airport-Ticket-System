package com.example.airport.payments;

import com.example.airport.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentRepository paymentDao;

    @GetMapping("/invoice/{id}")
    public ResponseEntity<?> getPaymentListByInvoiceId(@PathVariable("id")Integer id){
        List<Payment> paymentList = paymentDao.getPaymentsByInvoice(id);
        return ResponseEntity.ok(
                ApiResponse.success(paymentList)
        );
    }
}
