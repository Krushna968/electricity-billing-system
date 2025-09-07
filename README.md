# ⚡ Electricity Billing System

A comprehensive **Electricity Billing System** built with Java Swing for desktop GUI and MySQL database. This system provides separate interfaces for administrators and customers to manage electricity billing operations efficiently.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![NetBeans](https://img.shields.io/badge/NetBeans-1B6AC6?style=for-the-badge&logo=apachenetbeanside&logoColor=white)

## 🌟 Features

### 👨‍💼 Admin Features
- **Dashboard**: Revenue analytics, customer statistics, and system overview
- **Customer Management**: Add, update, and view customer information
- **Bill Generation**: Generate monthly electricity bills
- **Meter Management**: Manage meter information and readings
- **Tax Configuration**: Set electricity rates and tax parameters
- **Payment Tracking**: Monitor payment status and history
- **Utilities**: Built-in calculator and notepad

### 👤 Customer Features
- **Personal Dashboard**: View bills, usage history, and account details
- **Bill Viewing**: Access current and historical electricity bills
- **Information Updates**: Update personal contact information
- **Payment Status**: Check payment history and pending dues
- **Usage Analytics**: Monitor electricity consumption patterns

## 🛠️ Tech Stack

- **Frontend**: Java Swing (GUI)
- **Backend**: Java (Core)
- **Database**: MySQL 8.0
- **IDE**: Apache NetBeans
- **Build Tool**: Apache Ant (via NetBeans)
- **Architecture**: MVC Pattern

## 🗄️ Database Schema

The system uses 5 main tables:

### `customer`
- `name` - Customer full name
- `meter_no` - Unique meter identifier (Primary Key)
- `address` - Customer address
- `city` - City name
- `state` - State name
- `email` - Email address
- `phone` - Phone number

### `login`
- `meter_no` - Meter number (Foreign Key)
- `username` - Login username
- `name` - Display name
- `password` - User password
- `user` - Role (Admin/Customer)

### `bill`
- `meter_no` - Meter number (Foreign Key)
- `month` - Billing month
- `units` - Units consumed
- `total_bill` - Total bill amount
- `status` - Payment status (Paid/Not Paid)

### `meter_info`
- `meter_no` - Meter number (Foreign Key)
- `meter_location` - Physical location of meter
- `meter_type` - Type (Digital/Smart Meter)
- `phase_code` - Phase code identifier
- `bill_type` - Billing category
- `days` - Billing cycle days

### `tax`
- `cost_per_unit` - Rate per unit (₹3.00)
- `meter_rent` - Monthly meter rent (₹50.00)
- `service_charge` - Service charges (₹20.00)
- `swacch_bharat_tax` - Government tax (₹15.00)
- `fixed_tax` - Fixed charges (₹25.00)

## 🚀 Getting Started

### Prerequisites
- Java JDK 8 or higher
- MySQL Server 8.0+
- Apache NetBeans IDE (recommended)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Krushna968/electricity-billing-system.git
   cd electricity-billing-system
   ```

2. **Setup MySQL Database**
   ```sql
   CREATE DATABASE ebs;
   USE ebs;
   ```

3. **Import Database Schema**
   ```bash
   mysql -u root -p ebs < database/schema.sql
   mysql -u root -p ebs < database/sample_data.sql
   ```

4. **Configure Database Connection**
   - Update database credentials in `src/electricity/billing/system/Conn.java`
   ```java
   String url = "jdbc:mysql://localhost:3306/ebs";
   String username = "root";
   String password = "your_password";
   ```

5. **Run the Application**
   - Open project in NetBeans
   - Build and run the project
   - Or compile manually:
   ```bash
   javac -cp "lib/*" src/electricity/billing/system/*.java
   java -cp "lib/*:src" electricity.billing.system.Splash
   ```

## 🔐 Default Login Credentials

### Admin Access
- **Username**: `Parth`
- **Password**: `Parth123`

### Customer Access
- **Username**: `Customer` | **Password**: `Customer123`
- **Username**: `rajesh2024` | **Password**: `raj@123`
- **Username**: `priya2024` | **Password**: `pri@123`
- **Username**: `amit2024` | **Password**: `amt@123`

## 📁 Project Structure

```
electricity-billing-system/
├── src/
│   └── electricity/billing/system/
│       ├── Splash.java              # Splash screen
│       ├── Login.java               # Login interface
│       ├── Project.java             # Main dashboard
│       ├── Conn.java                # Database connection
│       ├── NewCustomer.java         # Add customer form
│       ├── CustomerDetails.java     # View customers
│       ├── UpdateInformation.java   # Update customer info
│       ├── BillDetails.java         # View bills
│       ├── GenerateBill.java        # Generate new bills
│       ├── PayBill.java             # Payment interface
│       ├── CalculateBill.java       # Bill calculator
│       └── Meter_Info.java          # Meter management
├── lib/                            # MySQL connector JAR
├── database/
│   ├── schema.sql                  # Database schema
│   └── sample_data.sql             # Sample data
├── nbproject/                      # NetBeans project files
├── Dockerfile                      # Docker configuration
├── docker-compose.yml             # Docker compose setup
└── README.md                       # This file
```

## 🎯 Key Features Implemented

- ✅ **Modern UI**: Clean, professional interface with consistent styling
- ✅ **Role-Based Access**: Separate dashboards for admins and customers
- ✅ **Bill Management**: Complete billing lifecycle management
- ✅ **Payment Integration**: Payment gateway integration with multiple options
- ✅ **Data Validation**: Input validation and error handling
- ✅ **Database Operations**: CRUD operations with proper SQL handling
- ✅ **Utility Tools**: Built-in calculator and notepad functionality

## 🔧 Configuration

### Database Configuration
Update `src/electricity/billing/system/Conn.java`:
```java
public class Conn {
    Connection c;
    Statement s;
    
    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ebs", 
                "your_username", 
                "your_password"
            );
            s = c.createStatement();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
```

## 🐳 Docker Deployment

Run with Docker:
```bash
docker-compose up -d
```

This will start:
- MySQL database on port 3306
- Application accessible via X11 forwarding

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit changes (`git commit -am 'Add new feature'`)
4. Push to branch (`git push origin feature/new-feature`)
5. Create a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Built with Java Swing for cross-platform compatibility
- MySQL database for reliable data storage
- NetBeans IDE for development environment

## 📞 Support

For support or questions:
- Create an [issue](https://github.com/Krushna968/electricity-billing-system/issues)
- Contact: [Your Email]

---

**Made with ❤️ by [Krushna](https://github.com/Krushna968)**
