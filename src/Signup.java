import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class Signup extends JFrame {
    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private Image backgroundImage;

    public Signup() {
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

                // Create a gradient overlay
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 0, 0, 150), 0, getHeight(), new Color(0, 0, 0, 50));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel("Signup Page");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 44));
        titleLabel.setForeground(new Color(220, 220, 220)); // Adjusted text color
        titleLabel.setBounds(-10, 50, 900, 80);  // Centered at the top
        contentPane.add(titleLabel);

        // Username label
        JLabel usernameLabel = new JLabel("Username*");
        usernameLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        usernameLabel.setForeground(new Color(220, 220, 220)); // Adjusted text color
        usernameLabel.setBounds(300, 250, 100, 19);  // Centered in the left section
        contentPane.add(usernameLabel);

        // Username text field
        usernameField = new JTextField();
        usernameField.setBounds(300, 280, 300, 30);  // Centered in the left section
        contentPane.add(usernameField);
        usernameField.setColumns(10);

        // Password label
        JLabel passwordLabel = new JLabel("Password*");
        passwordLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(220, 220, 220)); // Adjusted text color
        passwordLabel.setBounds(300, 320, 100, 19);  // Centered in the left section
        contentPane.add(passwordLabel);

        // Password field
        passwordField = new JPasswordField();
        passwordField.setBounds(300, 350, 300, 30);  // Centered in the left section
        contentPane.add(passwordField);

        // Email label
        JLabel emailLabel = new JLabel("Email*");
        emailLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        emailLabel.setForeground(new Color(220, 220, 220)); // Adjusted text color
        emailLabel.setBounds(300, 390, 100, 19);  // Centered in the left section
        contentPane.add(emailLabel);

        // Email text field
        emailField = new JTextField();
        emailField.setBounds(300, 420, 300, 30);  // Centered in the left section
        contentPane.add(emailField);
        emailField.setColumns(10);

        // Signup button
        JButton signupButton = new JButton("Signup");
        signupButton.setFont(new Font("Helvetica", Font.PLAIN, 14));
        signupButton.setForeground(Color.WHITE);
        signupButton.setBackground(new Color(0, 100, 200));
        signupButton.setBounds(300, 470, 100, 30);  // Positioned below the email field
        contentPane.add(signupButton);

        // Signup button action listener

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e1) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();
                String url = "jdbc:sqlserver://Javeria\\SQLEXPRESS;databaseName=SCD;encrypt=false;trustServerCertificate=true;user=Javeria;password=JAVERIANOOR123";

                // Validate password before entering the try-catch block
                if (!isValidPassword(password)) {
                    JOptionPane.showMessageDialog(null, "Password must be at least 6 characters long and contain at least one digit.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return; // Stop execution if validation fails
                }

                // Validate email before entering the try-catch block
                if (!isValidEmail(email)) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid email address.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return; // Stop execution if validation fails
                }

                // Database operations inside try-catch
                try {
                    Connection connection = DriverManager.getConnection(url);
                    System.out.println("Connected to the database successfully.");

                    Statement statement = connection.createStatement();
                    statement.executeUpdate("INSERT INTO CREDENTIALS (username, password, email) VALUES ('" + username + "', '" + password + "','" + email + "')");
                    connection.close();

                    JOptionPane.showMessageDialog(null, "Signup successful!");
                    CategoryPage categorypage=new CategoryPage();
                    categorypage.setVisible(true);


                } catch (SQLException e) {
                    System.out.println("Connection failed.");
                    e.printStackTrace();
                }
            }
            // Password validation method
            private boolean isValidPassword(String password) {
                return password.length() >= 6 && password.matches(".*\\d.*");
            }

            // Email validation method
            private boolean isValidEmail(String email) {
                // Simple regex pattern for email validation
                String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
                return email.matches(emailRegex);
            }
        });


        // Reset button
        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Helvetica", Font.PLAIN, 14));
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(new Color(220, 20, 60));
        resetButton.setBounds(500, 470, 100, 30);  // Positioned next to the signup button
        contentPane.add(resetButton);

        // Reset button action listener
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                usernameField.setText(null);
                passwordField.setText(null);
                emailField.setText(null);

            }
        });
    }
}

