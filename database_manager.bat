@echo off
echo =======================================
echo   ELECTRICITY BILLING SYSTEM DATABASE
echo =======================================
echo.
echo Choose an option:
echo 1. View all customers
echo 2. View all login accounts  
echo 3. View all bills
echo 4. View tax configuration
echo 5. Open MySQL command line
echo 6. Exit
echo.
set /p choice=Enter your choice (1-6): 

if "%choice%"=="1" (
    echo.
    echo === ALL CUSTOMERS ===
    mysql -u root -pPass@123 -D ebs -e "SELECT meter_no, name, city, state, phone FROM customer;"
    pause
    goto menu
)

if "%choice%"=="2" (
    echo.
    echo === ALL LOGIN ACCOUNTS ===
    mysql -u root -pPass@123 -D ebs -e "SELECT meter_no, username, name, user FROM login;"
    pause
    goto menu
)

if "%choice%"=="3" (
    echo.
    echo === ALL BILLS ===
    mysql -u root -pPass@123 -D ebs -e "SELECT meter_no, month, units, total_bill, status FROM bill ORDER BY meter_no, month;"
    pause
    goto menu
)

if "%choice%"=="4" (
    echo.
    echo === TAX CONFIGURATION ===
    mysql -u root -pPass@123 -D ebs -e "SELECT * FROM tax;"
    pause
    goto menu
)

if "%choice%"=="5" (
    echo.
    echo Opening MySQL command line...
    echo Type 'USE ebs;' to select the database
    echo Type 'SHOW TABLES;' to see all tables
    echo Type 'exit' to quit MySQL
    echo.
    mysql -u root -pPass@123
    goto menu
)

if "%choice%"=="6" (
    echo Goodbye!
    exit
)

:menu
cls
goto :eof
