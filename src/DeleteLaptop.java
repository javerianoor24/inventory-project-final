import javax.swing.*;
import java.awt.*;
import java.sql.*;

class DeleteLaptop extends JFrame {
    private JPanel contentPane;
    private JTextField phoneNameField;

    public DeleteLaptop() {
        setTitle("Delete Phone");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 200);

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

        // Delete button
        JButton deleteButton = new JButton("Delete") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };
        deleteButton.setBounds(200, 100, 100, 30);
        deleteButton.setContentAreaFilled(false);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Helvetica", Font.BOLD, 14));
        deleteButton.addActionListener(e -> processDelete());
        contentPane.add(deleteButton);
    }

    private void processDelete() {
        String phoneName = phoneNameField.getText().trim();

        // Validate input
        if (phoneName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in the Laptop name.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String url = "jdbc:sqlserver://Javeria\\SQLEXPRESS;databaseName=SCD;encrypt=false;trustServerCertificate=true;user=Javeria;password=JAVERIANOOR123";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            // Delete the phone from the database
            String deleteQuery = "DELETE FROM laptop WHERE name = '" + phoneName + "'";
            int rowsAffected = statement.executeUpdate(deleteQuery);

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Laptop deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                phoneNameField.setText("");  // Clear the input field after deletion
            } else {
                JOptionPane.showMessageDialog(this, "Laptop not found or could not be deleted.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DeleteLaptop frame = new DeleteLaptop();
            frame.setVisible(true);
        });
    }
}
