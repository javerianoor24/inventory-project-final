import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class phoneEntryPage extends JFrame {
    private JPanel contentPane;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JTextField colorField;
    private JTextField quantityField;

    public phoneEntryPage() {
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
            }
        };

        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel("Enter Phone Details");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 34));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 50, 900, 50);
        contentPane.add(titleLabel);

        // Name label and text field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        nameLabel.setBounds(150, 150, 100, 30);
        contentPane.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(300, 150, 400, 30);
        contentPane.add(nameField);

        // Description label and text field
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setForeground(Color.WHITE);
        descriptionLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        descriptionLabel.setBounds(150, 200, 120, 30);
        contentPane.add(descriptionLabel);

        descriptionField = new JTextField();
        descriptionField.setBounds(300, 200, 400, 30);
        contentPane.add(descriptionField);

        // Price label and text field
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setForeground(Color.WHITE);
        priceLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        priceLabel.setBounds(150, 250, 100, 30);
        contentPane.add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(300, 250, 400, 30);
        contentPane.add(priceField);

        // Color label and text field
        JLabel colorLabel = new JLabel("Color:");
        colorLabel.setForeground(Color.WHITE);
        colorLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        colorLabel.setBounds(150, 300, 100, 30);
        contentPane.add(colorLabel);

        colorField = new JTextField();
        colorField.setBounds(300, 300, 400, 30);
        contentPane.add(colorField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setForeground(Color.WHITE);
        quantityLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        quantityLabel.setBounds(150, 350, 100, 30);
        contentPane.add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setBounds(300, 350, 400, 30);
        contentPane.add(quantityField);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(350, 400, 200, 50);
        submitButton.setFont(new Font("Helvetica", Font.BOLD, 20));
        contentPane.add(submitButton);

        // Action listener for the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertPhoneData();
            }
        });
    }

    private void insertPhoneData() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        String price = priceField.getText();
        String color = colorField.getText();
        String quantityStr = quantityField.getText();
        int quantity;

        // Validation: Ensure no fields are empty
        if (name.isEmpty() || description.isEmpty() || price.isEmpty() || color.isEmpty() || quantityStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validation: Ensure the name contains only letters or alphanumeric characters
        if (!name.matches("^[a-zA-Z]+[a-zA-Z0-9]*$")) {
            JOptionPane.showMessageDialog(this, "Name must start with a letter and can only contain letters and numbers.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validation: Ensure quantity is a positive integer
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than 0!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validation: Ensure price is numeric with optional decimal
        if (!price.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
            JOptionPane.showMessageDialog(this, "Price must be a numeric value with up to two decimal places.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validation: Ensure color contains only alphabetic characters
        if (!color.matches("^[a-zA-Z]+$")) {
            JOptionPane.showMessageDialog(this, "Color must only contain alphabetic characters.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Database connection details
        String url = "jdbc:sqlserver://Javeria\\SQLEXPRESS;databaseName=SCD;encrypt=false;trustServerCertificate=true;user=Javeria;password=JAVERIANOOR123";

        try (Connection connection = DriverManager.getConnection(url)) {
            Statement statement = connection.createStatement();

            // Validation: Check for duplicate phone name
            String checkDuplicateSQL = "SELECT * FROM phones WHERE name = '" + name + "'";
            ResultSet resultSet = statement.executeQuery(checkDuplicateSQL);
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(this, "Phone name already exists! Please use a unique name.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Insert phone details into the database
            String sql = "INSERT INTO phones (name, description, price, colour, quantity) VALUES (" +
                    "'" + name + "', " +
                    "'" + description + "', " +
                    "'" + price + "', " +
                    "'" + color + "', " +
                    quantity + ")";

            statement.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Phone inserted into inventory successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            phoneEntryPage frame = new phoneEntryPage();
            frame.setVisible(true);
        });
    }
}
