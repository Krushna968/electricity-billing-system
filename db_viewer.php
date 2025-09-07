<?php
// Simple Database Viewer for Electricity Billing System
$servername = "localhost";
$username = "root";
$password = "Pass@123";
$dbname = "ebs";

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    echo "<h1>🏡 Electricity Billing System Database</h1>";
    
    // Show customers
    echo "<h2>👥 Customers</h2>";
    $stmt = $pdo->query("SELECT * FROM customer LIMIT 5");
    echo "<table border='1' style='border-collapse: collapse;'>";
    echo "<tr><th>Meter No</th><th>Name</th><th>City</th><th>State</th><th>Phone</th></tr>";
    while($row = $stmt->fetch()) {
        echo "<tr><td>{$row['meter_no']}</td><td>{$row['name']}</td><td>{$row['city']}</td><td>{$row['state']}</td><td>{$row['phone']}</td></tr>";
    }
    echo "</table>";
    
    // Show login accounts
    echo "<h2>🔐 Login Accounts</h2>";
    $stmt = $pdo->query("SELECT meter_no, username, name, user FROM login");
    echo "<table border='1' style='border-collapse: collapse;'>";
    echo "<tr><th>Meter No</th><th>Username</th><th>Name</th><th>Type</th></tr>";
    while($row = $stmt->fetch()) {
        echo "<tr><td>{$row['meter_no']}</td><td>{$row['username']}</td><td>{$row['name']}</td><td>{$row['user']}</td></tr>";
    }
    echo "</table>";
    
    // Show bills
    echo "<h2>💡 Recent Bills</h2>";
    $stmt = $pdo->query("SELECT * FROM bill ORDER BY meter_no LIMIT 10");
    echo "<table border='1' style='border-collapse: collapse;'>";
    echo "<tr><th>Meter No</th><th>Month</th><th>Units</th><th>Total Bill</th><th>Status</th></tr>";
    while($row = $stmt->fetch()) {
        $statusColor = $row['status'] == 'Paid' ? 'green' : 'red';
        echo "<tr><td>{$row['meter_no']}</td><td>{$row['month']}</td><td>{$row['units']}</td><td>₹{$row['total_bill']}</td><td style='color: $statusColor'>{$row['status']}</td></tr>";
    }
    echo "</table>";
    
} catch(PDOException $e) {
    echo "Connection failed: " . $e->getMessage();
}
?>

<style>
    body { font-family: Arial, sans-serif; margin: 20px; }
    table { margin: 10px 0; width: 100%; }
    th { background-color: #f2f2f2; padding: 8px; }
    td { padding: 6px; }
    h1 { color: #2c3e50; }
    h2 { color: #34495e; }
</style>
