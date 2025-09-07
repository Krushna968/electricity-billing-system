package com.electricitybilling.api;

import org.springframework.web.bind.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

// Spring Boot REST Controller for Electricity Billing System
@SpringBootApplication
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow CORS for web frontend
public class ElectricityBillingController {
    
    private ElectricityBillingAPI billingAPI = new ElectricityBillingAPI();
    
    // Authentication endpoint
    @PostMapping("/auth")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest request) {
        String result = billingAPI.authenticateUser(request.username, request.password, request.userType);
        return ResponseEntity.ok(result);
    }
    
    // Get all customers (Admin only)
    @GetMapping("/customers")
    public ResponseEntity<String> getAllCustomers() {
        String result = billingAPI.getAllCustomers();
        return ResponseEntity.ok(result);
    }
    
    // Get customer by meter number
    @GetMapping("/customers/{meterNo}")
    public ResponseEntity<String> getCustomerByMeter(@PathVariable String meterNo) {
        String result = billingAPI.getCustomerByMeter(meterNo);
        return ResponseEntity.ok(result);
    }
    
    // Get bills for a customer
    @GetMapping("/bills/{meterNo}")
    public ResponseEntity<String> getBillsByMeter(@PathVariable String meterNo) {
        String result = billingAPI.getBillsByMeter(meterNo);
        return ResponseEntity.ok(result);
    }
    
    // Add new customer
    @PostMapping("/customers")
    public ResponseEntity<String> addCustomer(@RequestBody CustomerRequest request) {
        String result = billingAPI.addCustomer(
            request.name, request.meterNo, request.address, 
            request.city, request.state, request.email, request.phone
        );
        return ResponseEntity.ok(result);
    }
    
    // Pay bill
    @PostMapping("/bills/pay")
    public ResponseEntity<String> payBill(@RequestBody PaymentRequest request) {
        String result = billingAPI.payBill(request.meterNo, request.month);
        return ResponseEntity.ok(result);
    }
    
    // Calculate bill
    @PostMapping("/bills/calculate")
    public ResponseEntity<String> calculateBill(@RequestBody BillCalculationRequest request) {
        String result = billingAPI.calculateBill(request.meterNo, request.month, request.units);
        return ResponseEntity.ok(result);
    }
    
    // Get meter information
    @GetMapping("/meters/{meterNo}")
    public ResponseEntity<String> getMeterInfo(@PathVariable String meterNo) {
        String result = billingAPI.getMeterInfo(meterNo);
        return ResponseEntity.ok(result);
    }
    
    // Request DTOs
    public static class AuthRequest {
        public String username;
        public String password;
        public String userType;
    }
    
    public static class CustomerRequest {
        public String name;
        public String meterNo;
        public String address;
        public String city;
        public String state;
        public String email;
        public String phone;
    }
    
    public static class PaymentRequest {
        public String meterNo;
        public String month;
    }
    
    public static class BillCalculationRequest {
        public String meterNo;
        public String month;
        public int units;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(ElectricityBillingController.class, args);
    }
}
