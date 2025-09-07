package electricity.billing.system;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Project extends JFrame implements ActionListener{

    String atype, meter;
    JPanel sidebar, mainContent;
    JLabel welcomeLabel, userTypeLabel;
    
    Project(String atype, String meter) {
        this.atype = atype;
        this.meter = meter;
        
        setTitle("Electricity Billing Management System - " + atype);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initializeModernUI();
        setVisible(true);
    }
    
    private void initializeModernUI() {
        setLayout(new BorderLayout());
        
        // Create modern sidebar
        createModernSidebar();
        
        // Create main content area
        createMainContent();
        
        // Add components
        add(sidebar, BorderLayout.WEST);
        add(mainContent, BorderLayout.CENTER);
    }
    
    private void createModernSidebar() {
        sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                Color color1 = new Color(44, 62, 80);  // Dark blue-gray
                Color color2 = new Color(52, 73, 94);  // Lighter blue-gray
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Header section
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 30, 20));
        
        JLabel titleLabel = new JLabel("⚡ ELECTRICITY", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Billing System", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(189, 195, 199));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);
        
        sidebar.add(headerPanel);
        
        // User info section
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setOpaque(false);
        userPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 30, 20));
        
        JLabel userIcon = new JLabel(atype.equals("Admin") ? "👤" : "🏠", JLabel.CENTER);
        userIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        userIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel userTypeLabel = new JLabel(atype.toUpperCase(), JLabel.CENTER);
        userTypeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        userTypeLabel.setForeground(new Color(52, 152, 219));
        userTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel meterLabel = new JLabel("Meter: " + (meter != null ? meter : "N/A"), JLabel.CENTER);
        meterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        meterLabel.setForeground(new Color(149, 165, 166));
        meterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        userPanel.add(userIcon);
        userPanel.add(Box.createVerticalStrut(5));
        userPanel.add(userTypeLabel);
        userPanel.add(Box.createVerticalStrut(3));
        userPanel.add(meterLabel);
        
        sidebar.add(userPanel);
        
        // Navigation buttons
        if (atype.equals("Admin")) {
            addNavButton("👥", "Customer Management", "New Customer");
            addNavButton("📊", "Customer Details", "Customer Details");
            addNavButton("💳", "Deposit Details", "Deposit Details");
            addNavButton("🧮", "Calculate Bill", "Calculate Bill");
        } else {
            addNavButton("ℹ️", "View Information", "View Information");
            addNavButton("✏️", "Update Information", "Update Information");
            addNavButton("💰", "Pay Bill", "Pay Bill");
            addNavButton("🧾", "Bill Details", "Bill Details");
            addNavButton("📋", "Generate Bill", "Generate Bill");
        }
        
        // Utilities section
        sidebar.add(Box.createVerticalStrut(20));
        JPanel utilitySection = new JPanel();
        utilitySection.setOpaque(false);
        utilitySection.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        JButton notepadBtn = createUtilityButton("📝", "Notepad");
        JButton calcBtn = createUtilityButton("🔢", "Calculator");
        
        utilitySection.add(notepadBtn);
        utilitySection.add(calcBtn);
        sidebar.add(utilitySection);
        
        // Logout button at bottom
        sidebar.add(Box.createVerticalGlue());
        JButton logoutBtn = createStyledButton("🚪 Logout", new Color(231, 76, 60), Color.WHITE);
        logoutBtn.setActionCommand("Exit");
        logoutBtn.setMaximumSize(new Dimension(240, 45));
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(logoutBtn);
        sidebar.add(Box.createVerticalStrut(20));
    }
    
    private void addNavButton(String icon, String text, String command) {
        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(new Color(41, 128, 185));
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(52, 152, 219));
                } else {
                    g2.setColor(new Color(127, 140, 141, 30));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                super.paintComponent(g);
                g2.dispose();
            }
        };
        
        button.setText("  " + icon + "  " + text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setActionCommand(command);
        button.addActionListener(this);
        button.setMaximumSize(new Dimension(260, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(5));
    }
    
    private JButton createUtilityButton(String icon, String command) {
        JButton button = createStyledButton(icon, new Color(95, 106, 106), Color.WHITE);
        button.setActionCommand(command);
        button.setPreferredSize(new Dimension(50, 35));
        button.setToolTipText(command);
        return button;
    }
    
    private void createMainContent() {
        mainContent = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                Color color1 = new Color(236, 240, 241);  // Light gray
                Color color2 = new Color(189, 195, 199);  // Medium gray
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainContent.setLayout(new BorderLayout());
        
        // Welcome section
        JPanel welcomePanel = new JPanel();
        welcomePanel.setOpaque(false);
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        if (atype.equals("Admin")) {
            welcomeLabel = new JLabel("Admin Dashboard", JLabel.CENTER);
            welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
            welcomeLabel.setForeground(new Color(44, 62, 80));
            welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel descLabel = new JLabel("Electricity Billing Management System", JLabel.CENTER);
            descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            descLabel.setForeground(new Color(127, 140, 141));
            descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel statusLabel = new JLabel("🟢 System Online - Ready for Operations", JLabel.CENTER);
            statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            statusLabel.setForeground(new Color(39, 174, 96));
            statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            welcomePanel.add(welcomeLabel);
            welcomePanel.add(Box.createVerticalStrut(10));
            welcomePanel.add(descLabel);
            welcomePanel.add(Box.createVerticalStrut(20));
            welcomePanel.add(statusLabel);
            
            // Admin Stats panel
            JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
            statsPanel.setOpaque(false);
            statsPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
            
            statsPanel.add(createStatCard("👥", "Active Customers", "8"));
            statsPanel.add(createStatCard("💡", "Monthly Bills", "18"));
            statsPanel.add(createStatCard("💰", "Revenue", "₹12,456"));
            
            welcomePanel.add(statsPanel);
        } else {
            // Customer Dashboard
            welcomeLabel = new JLabel("Customer Portal", JLabel.CENTER);
            welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
            welcomeLabel.setForeground(new Color(44, 62, 80));
            welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel descLabel = new JLabel("Welcome to Your Electricity Account", JLabel.CENTER);
            descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            descLabel.setForeground(new Color(127, 140, 141));
            descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel meterLabel = new JLabel("Your Meter: " + (meter != null ? meter : "Not Assigned"), JLabel.CENTER);
            meterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            meterLabel.setForeground(new Color(39, 174, 96));
            meterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            welcomePanel.add(welcomeLabel);
            welcomePanel.add(Box.createVerticalStrut(10));
            welcomePanel.add(descLabel);
            welcomePanel.add(Box.createVerticalStrut(20));
            welcomePanel.add(meterLabel);
            
            // Customer Stats panel - show their personal stats
            JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
            statsPanel.setOpaque(false);
            statsPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
            
            statsPanel.add(createStatCard("💳", "Current Bill", "₹498.75"));
            statsPanel.add(createStatCard("⚡", "Last Month Usage", "135 Units"));
            statsPanel.add(createStatCard("💰", "Payment Status", "Pending"));
            
            welcomePanel.add(statsPanel);
        }
        
        mainContent.add(welcomePanel, BorderLayout.CENTER);
    }
    
    private JPanel createStatCard(String icon, String title, String value) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.setColor(new Color(189, 195, 199, 100));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        card.setOpaque(false);
        
        JLabel iconLabel = new JLabel(icon, JLabel.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(new Color(127, 140, 141));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value, JLabel.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        valueLabel.setForeground(new Color(44, 62, 80));
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(3));
        card.add(valueLabel);
        
        return card;
    }
    
    private JButton createStyledButton(String text, Color bgColor, Color textColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);
        
        // Add hover effects
        button.addMouseListener(new MouseAdapter() {
            Color originalColor = bgColor;
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(originalColor.brighter());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });
        
        return button;
    }
    
    public void actionPerformed(ActionEvent ae) {
        String msg = ae.getActionCommand();
        if (msg.equals("New Customer")) {
            new NewCustomer();
        } else if (msg.equals("Customer Details")) {
            new CustomerDetails();
        } else if (msg.equals("Deposit Details")) {
            new DepositDetails();
        } else if (msg.equals("Calculate Bill")) {
            new CalculateBill();
        } else if (msg.equals("View Information")) {
            new ViewInformation(meter);
        } else if (msg.equals("Update Information")) {
            new UpdateInformation(meter);
        } else if (msg.equals("Bill Details")) {
            new BillDetails(meter);
        } else if (msg.equals("Notepad")) {
            try {
                Runtime.getRuntime().exec("notepad.exe");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (msg.equals("Calculator")) {
            try {
                Runtime.getRuntime().exec("calc.exe");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (msg.equals("Exit")) {
            setVisible(false);
            new Login();
        } else if (msg.equals("Pay Bill")) {
            new PayBill(meter);
        } else if (msg.equals("Generate Bill")) {
            new GenerateBill(meter);
        }
    }

    public static void main(String[] args) {
        new Project("", "");
    }
}
