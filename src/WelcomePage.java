import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class WelcomePage extends JFrame {
    private JPanel contentPane;

    public WelcomePage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 800);

        // Load the background image
        Image backgroundImage = new ImageIcon("C:\\Users\\javer\\Downloads\\background.jpg.jpg").getImage();

        // Set up the content pane
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

                // Draw a gradient overlay
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 0, 0, 150), 0, getHeight(), new Color(0, 0, 0, 50));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Draw decorative shapes behind buttons
                g.setColor(new Color(0, 100, 200, 50)); // Semi-transparent color
                g.fillRoundRect(290, 310, 170, 70, 30, 30); // Behind Login Button
                g.fillRoundRect(490, 310, 170, 70, 30, 30); // Behind Signup Button
            }
        };


        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Logo or Icon
        JLabel logoLabel = new JLabel(new ImageIcon("E:/CodingConcepts/logo.png")); // Add your logo image path
        logoLabel.setBounds(350, 20, 200, 100); // Adjust size as needed
        contentPane.add(logoLabel);

        // Title label
        JLabel titleLabel = new JLabel("Welcome to Inventory Management System");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 34));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 130, 900, 80);
        contentPane.add(titleLabel);

        // Subtitle label
        JLabel subtitleLabel = new JLabel("Manage Your Inventory Efficiently");
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Helvetica", Font.ITALIC, 20));
        subtitleLabel.setForeground(new Color(220, 220, 220));
        subtitleLabel.setBounds(0, 210, 900, 30);
        contentPane.add(subtitleLabel);

        // Divider Line
        JSeparator separator = new JSeparator();
        separator.setBounds(150, 250, 600, 2);
        separator.setForeground(Color.WHITE);
        contentPane.add(separator);

        // Description label
        JLabel descriptionLabel = new JLabel("Welcome! Please choose to Login or Signup to manage your inventory.");
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
        descriptionLabel.setForeground(new Color(220, 220, 220));
        descriptionLabel.setBounds(0, 260, 900, 40);
        contentPane.add(descriptionLabel);

        // Rounded Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(0, 100, 200));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginButton.setContentAreaFilled(false);
        loginButton.setOpaque(true);
        loginButton.setBounds(300, 320, 150, 50);
        styleButton(loginButton);
        contentPane.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login loginFrame = new Login();
                loginFrame.setVisible(true);
                setVisible(false);
            }
        });

        // Rounded Signup button
        JButton signupButton = new JButton("Signup");
        signupButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
        signupButton.setForeground(Color.WHITE);
        signupButton.setBackground(new Color(0, 100, 200));
        signupButton.setFocusPainted(false);
        signupButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        signupButton.setContentAreaFilled(false);
        signupButton.setOpaque(true);
        signupButton.setBounds(500, 320, 150, 50);
        styleButton(signupButton);
        contentPane.add(signupButton);

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Signup signupFrame = new Signup();
                signupFrame.setVisible(true);
                setVisible(false);
            }
        });
    }

    // Method to style buttons
    private void styleButton(JButton button) {

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 120, 240)); // Lighter color on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 100, 200)); // Original color
            }
        });
    }
}
