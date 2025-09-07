package com.electricitybilling.api;

import java.sql.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

// Simple HTTP server-based REST API for Electricity Billing System
// This provides web API endpoints for the desktop application functionality

public class ElectricityBillingAPI {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ebs";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Pass@123";
    
    // Database connection method
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
    
    // Authentication endpoint
    public String authenticateUser(String username, String password, String userType) {
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM login WHERE username = ? AND password = ? AND user = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, userType);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JsonObject response = new JsonObject();
                response.addProperty("success", true);
                response.addProperty("meterNo", rs.getString("meter_no"));
                response.addProperty("name", rs.getString("name"));
                response.addProperty("userType", rs.getString("user"));
                return new Gson().toJson(response);
            } else {
                return "{\"success\":false,\"message\":\"Invalid credentials\"}";
            }
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
    
    // Get all customers
    public String getAllCustomers() {
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM customer";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            List<Map<String, Object>> customers = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> customer = new HashMap<>();
                customer.put("name", rs.getString("name"));
                customer.put("meterNo", rs.getString("meter_no"));
                customer.put("address", rs.getString("address"));
                customer.put("city", rs.getString("city"));
                customer.put("state", rs.getString("state"));
                customer.put("email", rs.getString("email"));
                customer.put("phone", rs.getString("phone"));
                customers.add(customer);
            }
            return new Gson().toJson(customers);
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
    
    // Get customer by meter number
    public String getCustomerByMeter(String meterNo) {
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM customer WHERE meter_no = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, meterNo);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Map<String, Object> customer = new HashMap<>();
                customer.put("name", rs.getString("name"));
                customer.put("meterNo", rs.getString("meter_no"));
                customer.put("address", rs.getString("address"));
                customer.put("city", rs.getString("city"));
                customer.put("state", rs.getString("state"));
                customer.put("email", rs.getString("email"));
                customer.put("phone", rs.getString("phone"));
                return new Gson().toJson(customer);
            } else {
                return "{\"error\":\"Customer not found\"}";
            }
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
    
    // Get bills for a customer
    public String getBillsByMeter(String meterNo) {
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM bill WHERE meter_no = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, meterNo);
            ResultSet rs = stmt.executeQuery();
            
            List<Map<String, Object>> bills = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> bill = new HashMap<>();
                bill.put("meterNo", rs.getString("meter_no"));
                bill.put("month", rs.getString("month"));
                bill.put("units", rs.getString("units"));
                bill.put("totalBill", rs.getString("total_bill"));
                bill.put("status", rs.getString("status"));
                bills.add(bill);
            }
            return new Gson().toJson(bills);
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
    
    // Add new customer
    public String addCustomer(String name, String meterNo, String address, String city, String state, String email, String phone) {
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO customer (name, meter_no, address, city, state, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, meterNo);
            stmt.setString(3, address);
            stmt.setString(4, city);
            stmt.setString(5, state);
            stmt.setString(6, email);
            stmt.setString(7, phone);
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                // Also add login entry
                String loginQuery = "INSERT INTO login (meter_no, username, name, password, user) VALUES (?, '', ?, '', 'Customer')";
                PreparedStatement loginStmt = conn.prepareStatement(loginQuery);
                loginStmt.setString(1, meterNo);
                loginStmt.setString(2, name);
                loginStmt.executeUpdate();
                
                return "{\"success\":true,\"message\":\"Customer added successfully\"}";
            } else {
                return "{\"success\":false,\"message\":\"Failed to add customer\"}";
            }
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
    
    // Update bill payment status
    public String payBill(String meterNo, String month) {
        try (Connection conn = getConnection()) {
            String query = "UPDATE bill SET status = 'Paid' WHERE meter_no = ? AND month = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, meterNo);
            stmt.setString(2, month);
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                return "{\"success\":true,\"message\":\"Bill paid successfully\"}";
            } else {
                return "{\"success\":false,\"message\":\"Bill not found or already paid\"}";
            }
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
    
    // Calculate bill for given units
    public String calculateBill(String meterNo, String month, int units) {
        try (Connection conn = getConnection()) {
            // Get tax information
            String taxQuery = "SELECT * FROM tax LIMIT 1";
            PreparedStatement taxStmt = conn.prepareStatement(taxQuery);
            ResultSet taxRs = taxStmt.executeQuery();
            
            if (taxRs.next()) {
                double costPerUnit = taxRs.getDouble("cost_per_unit");
                double meterRent = taxRs.getDouble("meter_rent");
                double serviceCharge = taxRs.getDouble("service_charge");
                double swacchTax = taxRs.getDouble("swacch_bharat_tax");
                double fixedTax = taxRs.getDouble("fixed_tax");
                
                double totalBill = (units * costPerUnit) + meterRent + serviceCharge + swacchTax + fixedTax;
                
                // Insert bill record
                String billQuery = "INSERT INTO bill (meter_no, month, units, total_bill, status) VALUES (?, ?, ?, ?, 'Not Paid')";
                PreparedStatement billStmt = conn.prepareStatement(billQuery);
                billStmt.setString(1, meterNo);
                billStmt.setString(2, month);
                billStmt.setString(3, String.valueOf(units));
                billStmt.setString(4, String.valueOf(totalBill));
                
                int result = billStmt.executeUpdate();
                if (result > 0) {
                    JsonObject response = new JsonObject();
                    response.addProperty("success", true);
                    response.addProperty("meterNo", meterNo);
                    response.addProperty("month", month);
                    response.addProperty("units", units);
                    response.addProperty("totalBill", totalBill);
                    response.addProperty("costPerUnit", costPerUnit);
                    response.addProperty("meterRent", meterRent);
                    response.addProperty("serviceCharge", serviceCharge);
                    return new Gson().toJson(response);
                } else {
                    return "{\"success\":false,\"message\":\"Failed to save bill\"}";
                }
            } else {
                return "{\"error\":\"Tax configuration not found\"}";
            }
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
    
    // Get meter information
    public String getMeterInfo(String meterNo) {
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM meter_info WHERE meter_no = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, meterNo);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Map<String, Object> meterInfo = new HashMap<>();
                meterInfo.put("meterNo", rs.getString("meter_no"));
                meterInfo.put("location", rs.getString("meter_location"));
                meterInfo.put("type", rs.getString("meter_type"));
                meterInfo.put("phaseCode", rs.getString("phase_code"));
                meterInfo.put("billType", rs.getString("bill_type"));
                meterInfo.put("days", rs.getString("days"));
                return new Gson().toJson(meterInfo);
            } else {
                return "{\"error\":\"Meter information not found\"}";
            }
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
    
    // Simple main method for testing
    public static void main(String[] args) {
        ElectricityBillingAPI api = new ElectricityBillingAPI();
        
        System.out.println("=== Electricity Billing REST API ===");
        System.out.println("Testing API endpoints...");
        
        // Test authentication
        System.out.println("Admin login: " + api.authenticateUser("admin", "admin123", "Admin"));
        System.out.println("Customer login: " + api.authenticateUser("john123", "pass123", "Customer"));
        
        // Test data retrieval
        System.out.println("All customers: " + api.getAllCustomers());
        System.out.println("Bills for meter 10001: " + api.getBillsByMeter("10001"));
        
        System.out.println("API is ready to be integrated with a web server framework!");
    }
}
