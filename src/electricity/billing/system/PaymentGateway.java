package electricity.billing.system;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PaymentGateway extends JFrame implements ActionListener {
    
    String meter, month, amount;
    JButton payUPI, payCard, payNetBanking, payWallet, backBtn;
    JLabel amountLabel, meterLabel;
    
    PaymentGateway(String meter, String month, String amount) {
        this.meter = meter;
        this.month = month;
        this.amount = amount;
        
        setTitle("Payment Gateway - Electricity Bill Payment");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        initializeUI();
        setVisible(true);
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        
        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                Color color1 = new Color(74, 144, 226);  // Light blue
                Color color2 = new Color(124, 58, 237);  // Purple
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        JLabel titleLabel = new JLabel("💳 Payment Gateway", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Secure & Fast Bill Payment", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(255, 255, 255, 180));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Payment details panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setOpaque(false);
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));
        
        // Bill details card
        JPanel billCard = createInfoCard();
        billCard.setMaximumSize(new Dimension(500, 120));
        billCard.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel billTitle = new JLabel("Bill Details", JLabel.CENTER);
        billTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        billTitle.setForeground(new Color(44, 62, 80));
        
        meterLabel = new JLabel("Meter No: " + meter, JLabel.CENTER);
        meterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        meterLabel.setForeground(new Color(127, 140, 141));
        
        JLabel monthLabel = new JLabel("Month: " + month, JLabel.CENTER);
        monthLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        monthLabel.setForeground(new Color(127, 140, 141));
        
        amountLabel = new JLabel("Amount: ₹" + amount, JLabel.CENTER);
        amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        amountLabel.setForeground(new Color(231, 76, 60));
        
        billCard.add(billTitle);
        billCard.add(Box.createVerticalStrut(10));
        billCard.add(meterLabel);
        billCard.add(monthLabel);
        billCard.add(Box.createVerticalStrut(10));
        billCard.add(amountLabel);
        
        detailsPanel.add(billCard);
        detailsPanel.add(Box.createVerticalStrut(20));
        
        // Payment methods title
        JLabel paymentTitle = new JLabel("Choose Payment Method", JLabel.CENTER);
        paymentTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        paymentTitle.setForeground(Color.WHITE);
        paymentTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailsPanel.add(paymentTitle);
        detailsPanel.add(Box.createVerticalStrut(15));
        
        // Payment methods panel
        JPanel paymentsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        paymentsPanel.setOpaque(false);
        paymentsPanel.setMaximumSize(new Dimension(500, 200));
        
        payUPI = createPaymentButton("📱", "UPI Payment", new Color(76, 175, 80));
        payCard = createPaymentButton("💳", "Credit/Debit Card", new Color(33, 150, 243));
        payNetBanking = createPaymentButton("🏦", "Net Banking", new Color(255, 152, 0));
        payWallet = createPaymentButton("👛", "Digital Wallet", new Color(156, 39, 176));
        
        paymentsPanel.add(payUPI);
        paymentsPanel.add(payCard);
        paymentsPanel.add(payNetBanking);
        paymentsPanel.add(payWallet);
        
        detailsPanel.add(paymentsPanel);
        mainPanel.add(detailsPanel, BorderLayout.CENTER);
        
        // Footer with back button
        JPanel footerPanel = new JPanel(new FlowLayout());
        footerPanel.setOpaque(false);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        
        backBtn = createStyledButton("← Back", new Color(149, 165, 166), Color.WHITE);
        footerPanel.add(backBtn);
        
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
    
    private JPanel createInfoCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255, 255, 255, 240));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.setColor(new Color(189, 195, 199, 100));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        card.setOpaque(false);
        return card;
    }
    
    private JButton createPaymentButton(String icon, String text, Color bgColor) {
        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bgColor.brighter());
                } else {
                    g2.setColor(bgColor);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Add shadow effect
                g2.setColor(new Color(0, 0, 0, 30));
                g2.fillRoundRect(2, 2, getWidth(), getHeight(), 15, 15);
                
                super.paintComponent(g);
                g2.dispose();
            }
        };
        
        button.setLayout(new BoxLayout(button, BoxLayout.Y_AXIS));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setActionCommand(text);
        button.addActionListener(this);
        
        JLabel iconLabel = new JLabel(icon, JLabel.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel textLabel = new JLabel(text, JLabel.CENTER);
        textLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        button.add(iconLabel);
        button.add(Box.createVerticalStrut(5));
        button.add(textLabel);
        
        return button;
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
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
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
        String command = ae.getActionCommand();
        
        if (command.equals("← Back")) {
            dispose();
        } else {
            // Process payment
            processPayment(command);
        }
    }
    
    private void processPayment(String paymentMethod) {
        // Show processing dialog
        JDialog processingDialog = new JDialog(this, "Processing Payment", true);
        processingDialog.setSize(300, 150);
        processingDialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel processingLabel = new JLabel("Processing payment via " + paymentMethod + "...", JLabel.CENTER);
        processingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        
        panel.add(processingLabel, BorderLayout.CENTER);
        panel.add(progressBar, BorderLayout.SOUTH);
        
        processingDialog.add(panel);
        
        // Simulate payment processing
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(3000); // Simulate processing time
                
                // Update database - mark bill as paid
                try {
                    Conn c = new Conn();
                    String query = "UPDATE bill SET status = 'Paid' WHERE meter_no = '" + meter + "' AND month = '" + month + "'";
                    c.s.executeUpdate(query);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                return null;
            }
            
            @Override
            protected void done() {
                processingDialog.dispose();
                
                // Show success dialog
                JDialog successDialog = new JDialog(PaymentGateway.this, "Payment Successful", true);
                successDialog.setSize(400, 200);
                successDialog.setLocationRelativeTo(PaymentGateway.this);
                
                JPanel successPanel = new JPanel();
                successPanel.setLayout(new BoxLayout(successPanel, BoxLayout.Y_AXIS));
                successPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
                successPanel.setBackground(Color.WHITE);
                
                JLabel successIcon = new JLabel("✅", JLabel.CENTER);
                successIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
                successIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                JLabel successText = new JLabel("Payment Successful!", JLabel.CENTER);
                successText.setFont(new Font("Segoe UI", Font.BOLD, 18));
                successText.setForeground(new Color(39, 174, 96));
                successText.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                JLabel detailsText = new JLabel("₹" + amount + " paid via " + paymentMethod, JLabel.CENTER);
                detailsText.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                detailsText.setForeground(new Color(127, 140, 141));
                detailsText.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                JButton okBtn = createStyledButton("OK", new Color(39, 174, 96), Color.WHITE);
                okBtn.addActionListener(e -> {
                    successDialog.dispose();
                    PaymentGateway.this.dispose();
                });
                okBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                successPanel.add(successIcon);
                successPanel.add(Box.createVerticalStrut(10));
                successPanel.add(successText);
                successPanel.add(Box.createVerticalStrut(5));
                successPanel.add(detailsText);
                successPanel.add(Box.createVerticalStrut(20));
                successPanel.add(okBtn);
                
                successDialog.add(successPanel);
                successDialog.setVisible(true);
            }
        };
        
        processingDialog.setVisible(true);
        worker.execute();
    }
    
    public static void main(String[] args) {
        new PaymentGateway("MET001", "February", "498.75");
    }
}
