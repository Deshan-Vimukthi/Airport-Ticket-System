package com.example.airport.invoices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvoiceController {
    @Autowired
    private InvoiceRepository invoiceDao;
}
