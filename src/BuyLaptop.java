
import javax.swing.*;
import java.awt.*;
import java.sql.*;

class BuyLaptop extends JFrame {
    private JPanel contentPane;
    private JTextField phoneNameField;
    private JTextField quantityField;

    public BuyLaptop () {
        setTitle("Buy iPad");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 250);

        // Load the background image
        Image backgroundImage = new ImageIcon("C:\\Users\\javer\\Downloads\\background.jpg.jpg").getImage();

        // Set up the content pane with custom painting for background
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
            }
        };

        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Input for phone name
        JLabel phoneNameLabel = new JLabel("Laptop Name:");
        phoneNameLabel.setBounds(50, 50, 200, 30);
        phoneNameLabel.setForeground(Color.WHITE);
        phoneNameLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        contentPane.add(phoneNameLabel);

        phoneNameField = new JTextField();
        phoneNameField.setBounds(250, 50, 200, 30);
        phoneNameField.setOpaque(false);
        phoneNameField.setForeground(Color.WHITE);
        phoneNameField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        phoneNameField.setFont(new Font("Helvetica", Font.PLAIN, 14));
        contentPane.add(phoneNameField);

        // Input for quantity
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(50, 100, 200, 30);
        quantityLabel.setForeground(Color.WHITE);
        quantityLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        contentPane.add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setBounds(250, 100, 200, 30);
        quantityField.setOpaque(false);
        quantityField.setForeground(Color.WHITE);
        quantityField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        quantityField.setFont(new Font("Helvetica", Font.PLAIN, 14));
        contentPane.add(quantityField);

        // Submit button
        JButton submitButton = new JButton("Buy") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };
        submitButton.setBounds(200, 150, 100, 30);
        submitButton.setContentAreaFilled(false);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Helvetica", Font.BOLD, 14));
        submitButton.addActionListener(e -> processPurchase());
        contentPane.add(submitButton);
    }

    private void processPurchase() {
        String iPadName = phoneNameField.getText().trim();
        String quantityText = quantityField.getText().trim();

        // Validate inputs
        if (iPadName.isEmpty() || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                throw new NumberFormatException("Quantity must be greater than zero.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a positive number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Database connection parameters
        String url = "jdbc:sqlserver://Javeria\\SQLEXPRESS;databaseName=SCD;encrypt=false;trustServerCertificate=true;user=Javeria;password=JAVERIANOOR123";
        int currentQuantity;
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            // Check if the phone exists and has enough quantity
            String selectQuery = "SELECT quantity, price FROM laptop WHERE name = '" + iPadName + "'";
            ResultSet resultSet = statement.executeQuery(selectQuery);

            if (resultSet.next()) {
                currentQuantity = resultSet.getInt("quantity");
                String priceString = resultSet.getString("price");
                String cleanedPriceString = priceString.replaceAll("[^0-9]", ""); // Removes everything except digits
                int price = Integer.parseInt(cleanedPriceString);

                if (currentQuantity < quantity) {
                    JOptionPane.showMessageDialog(this, "Not enough quantity available.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update the quantity in the database
                String updateQuery = "UPDATE laptop SET quantity = quantity - " + quantity +
                        " WHERE name = '" + iPadName + "' AND quantity >= " + quantity;

                int rowsAffected = statement.executeUpdate(updateQuery);

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Purchase successful!"+"price "+currentQuantity*price, "Success", JOptionPane.INFORMATION_MESSAGE);
                    phoneNameField.setText("");  // Clear input fields after purchase
                    quantityField.setText("");

                } else {
                    JOptionPane.showMessageDialog(this, "Error processing purchase.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "laptop not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

