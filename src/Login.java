import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class Login extends JFrame {
    private JPanel contentPane;
    private JTextField textField;
    private JPasswordField passwordField;
    private Image backgroundImage;


    public Login() {
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
        JLabel lblNewLabel = new JLabel("Login Page");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Helvetica", Font.BOLD, 44));
        lblNewLabel.setForeground(new Color(220, 220, 220)); // Adjusted text color
        lblNewLabel.setBounds(-10, 50, 900, 80);  // Centered at the top
        contentPane.add(lblNewLabel);

        // Username label
        JLabel lblNewLabel_1 = new JLabel("Username*");
        lblNewLabel_1.setFont(new Font("Helvetica", Font.BOLD, 14));
        lblNewLabel_1.setForeground(new Color(220, 220, 220)); // Adjusted text color
        lblNewLabel_1.setBounds(300, 250, 100, 19);  // Centered in the left section
        contentPane.add(lblNewLabel_1);

        // Username text field
        textField = new JTextField();
        textField.setBounds(300, 280, 300, 30);  // Centered in the left section
        contentPane.add(textField);
        textField.setColumns(10);

        // Password label
        JLabel lblNewLabel_2 = new JLabel("Password*");
        lblNewLabel_2.setFont(new Font("Helvetica", Font.BOLD, 14));
        lblNewLabel_2.setForeground(new Color(220, 220, 220)); // Adjusted text color
        lblNewLabel_2.setBounds(300, 320, 100, 19);  // Centered in the left section
        contentPane.add(lblNewLabel_2);

        // Password field
        passwordField = new JPasswordField();
        passwordField.setBounds(300, 350, 300, 30);  // Centered in the left section
        contentPane.add(passwordField);

        // Login button
        JButton btnNewButton = new JButton("Login");
        btnNewButton.setFont(new Font("Helvetica", Font.PLAIN, 14));
        btnNewButton.setForeground(Color.WHITE);
        btnNewButton.setBackground(new Color(0, 100, 200));
        btnNewButton.setBounds(300, 400, 100, 30);  // Positioned below the password field
        contentPane.add(btnNewButton);

        // Login button action listener
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e2) {
                String username = textField.getText();
                String password = new String(passwordField.getPassword());
                String url = "jdbc:sqlserver://Javeria\\SQLEXPRESS;databaseName=SCD;encrypt=false;trustServerCertificate=true;user=Javeria;password=JAVERIANOOR123";

                boolean loginSuccessful = false; // Flag to track login success

                try {
                    Connection connection = DriverManager.getConnection(url);
                    System.out.println("Connected to the database successfully.");
                    Statement statement = connection.createStatement();
                    ResultSet resultset = statement.executeQuery("SELECT * FROM CREDENTIALS");

                    // Check if user credentials match any record in the database
                    while (resultset.next()) {
                        String Result = resultset.getString("username");
                        String Result1 = resultset.getString("password");

                        if (Result.equals(username) && Result1.equals(password)) {
                            JOptionPane.showMessageDialog(null, "Login successful");
                            loginSuccessful = true; // Mark login as successful
                            CategoryPage categorypage=new CategoryPage();
                            categorypage.setVisible(true);
                            setVisible(false);
                            break; // Stop checking after a successful login
                        }
                    }
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Connection failed.");
                    e.printStackTrace();
                }

                // If no successful login from the database, check for admin credentials
                if (!loginSuccessful) {
                    if (username.equals("admin") && password.equals("password")) {
                        JOptionPane.showMessageDialog(null, "Login Successful!");
                        loginSuccessful = true; // Mark as successful for admin

                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect username or password");
                    }
                }
            }
        });


        // Reset button
        JButton btnNewButton_1 = new JButton("Reset");
        btnNewButton_1.setFont(new Font("Helvetica", Font.PLAIN, 14));
        btnNewButton_1.setForeground(Color.WHITE);
        btnNewButton_1.setBackground(new Color(220, 20, 60));
        btnNewButton_1.setBounds(500, 400, 100, 30);  // Positioned next to the login button
        contentPane.add(btnNewButton_1);

        // Reset button action listener
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textField.setText(null);
                passwordField.setText(null);
            }
        });
    }
}

