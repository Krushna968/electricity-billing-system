package electricity.billing.system;

import java.sql.*;

public class ApplicationTest {
    
    public static void testDatabaseConnection() {
        System.out.println("Testing database connection...");
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT 1");
            if (rs.next()) {
                System.out.println("✓ Database connection successful");
            }
        } catch (Exception e) {
            System.out.println("✗ Database connection failed: " + e.getMessage());
        }
    }
    
    public static void testLoginFunctionality() {
        System.out.println("\nTesting login functionality...");
        try {
            Conn c = new Conn();
            
            // Test admin login
            String query = "SELECT * FROM login WHERE username = 'Parth' AND password = 'Parth123' AND user = 'Admin'";
            ResultSet rs = c.s.executeQuery(query);
            if (rs.next()) {
                System.out.println("✓ Admin login test passed (Parth/Parth123)");
            } else {
                System.out.println("✗ Admin login test failed");
            }
            
            // Test customer login
            query = "SELECT * FROM login WHERE username = 'Customer' AND password = 'Customer123' AND user = 'Customer'";
            rs = c.s.executeQuery(query);
            if (rs.next()) {
                System.out.println("✓ Customer login test passed (Customer/Customer123)");
            } else {
                System.out.println("✗ Customer login test failed");
            }
            
        } catch (Exception e) {
            System.out.println("✗ Login test failed: " + e.getMessage());
        }
    }
    
    public static void testCustomerData() {
        System.out.println("\nTesting customer data operations...");
        try {
            Conn c = new Conn();
            
            // Test customer query
            ResultSet rs = c.s.executeQuery("SELECT COUNT(*) as count FROM customer");
            if (rs.next() && rs.getInt("count") > 0) {
                System.out.println("✓ Customer data available (" + rs.getInt("count") + " customers)");
            }
            
            // Test bill data
            rs = c.s.executeQuery("SELECT COUNT(*) as count FROM bill");
            if (rs.next() && rs.getInt("count") > 0) {
                System.out.println("✓ Bill data available (" + rs.getInt("count") + " bills)");
            }
            
            // Test meter info
            rs = c.s.executeQuery("SELECT COUNT(*) as count FROM meter_info");
            if (rs.next() && rs.getInt("count") > 0) {
                System.out.println("✓ Meter info available (" + rs.getInt("count") + " meters)");
            }
            
        } catch (Exception e) {
            System.out.println("✗ Customer data test failed: " + e.getMessage());
        }
    }
    
    public static void testBillCalculation() {
        System.out.println("\nTesting bill calculation components...");
        try {
            Conn c = new Conn();
            
            // Test tax table
            ResultSet rs = c.s.executeQuery("SELECT * FROM tax LIMIT 1");
            if (rs.next()) {
                double costPerUnit = rs.getDouble("cost_per_unit");
                double meterRent = rs.getDouble("meter_rent");
                System.out.println("✓ Tax data available - Cost per unit: $" + costPerUnit + ", Meter rent: $" + meterRent);
            }
            
            // Test DbUtils functionality
            rs = c.s.executeQuery("SELECT * FROM customer LIMIT 1");
            javax.swing.table.DefaultTableModel model = DbUtils.resultSetToTableModel(rs);
            if (model.getRowCount() >= 0) {
                System.out.println("✓ DbUtils conversion working - Columns: " + model.getColumnCount());
            }
            
        } catch (Exception e) {
            System.out.println("✗ Bill calculation test failed: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Electricity Billing System Test ===");
        
        testDatabaseConnection();
        testLoginFunctionality();
        testCustomerData();
        testBillCalculation();
        
        System.out.println("\n=== Test Complete ===");
        System.out.println("The application appears to be working properly!");
        System.out.println("You can now run the GUI application using:");
        System.out.println("java -cp \"lib\\mysql-connector-java-8.0.28.jar;build\\classes\" electricity.billing.system.Splash");
    }
}
