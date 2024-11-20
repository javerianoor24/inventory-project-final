import javax.swing.*;
import java.awt.*;
import java.sql.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class iPadDisplay extends JFrame {
    private JPanel contentPane;
    private JPanel cardsPanel;
    private JScrollPane scrollPane;
    private JTextField searchField;

    public iPadDisplay () {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 0, 900, 800);

        // Load the background image
        Image backgroundImage = new ImageIcon("C:\\Users\\javer\\Downloads\\background.jpg.jpg").getImage();

        // Set up the content pane with custom painting for background and gradient
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
        // Set size and position

        JButton category = new JButton("Category") {
            @Override
            protected void paintComponent(Graphics g) {
                // No background painting, keeping it fully transparent
                super.paintComponent(g);
            }
        };
        category.setBounds(135, 20, 100, 30);
        category.setContentAreaFilled(false); // Disable default background
        category.setFocusPainted(false); // Disable focus highlight
        category.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // White border with thickness of 2
        category.setForeground(Color.WHITE); // Set text color to white
        category.setFont(new Font("Helvetica", Font.BOLD, 14));
        contentPane.add(category);
        // Font customization
        // Add an ActionListener to the button
        category.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action performed on button click
                CategoryPage categoryPage= new CategoryPage();
                categoryPage.setVisible(true);
                setVisible(false);
            }
        });


        // "Insert" button
        JButton insertButton = new JButton("Insert") {
            @Override
            protected void paintComponent(Graphics g) {
                // No background painting, keeping it fully transparent
                super.paintComponent(g);
            }
        };
        insertButton.setBounds(20, 20, 100, 30);
        insertButton.setContentAreaFilled(false); // Disable default background
        insertButton.setFocusPainted(false); // Disable focus highlight
        insertButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // White border with thickness of 2
        insertButton.setForeground(Color.WHITE); // Set text color to white
        insertButton.setFont(new Font("Helvetica", Font.BOLD, 14)); // Font customization
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                phoneEntryPage PHONE_ENTRY=new phoneEntryPage();
                PHONE_ENTRY.setVisible(true);
            }
        });
        contentPane.add(insertButton);


        // Search bar
        searchField = new JTextField();
        searchField.setBounds(340, 20, 200, 30);
        searchField.addActionListener(e -> filterCards());
        searchField.setOpaque(false);
        searchField.setForeground(Color.WHITE);
        contentPane.add(searchField);

        JButton searchButton = new JButton("Search") {
            @Override
            protected void paintComponent(Graphics g) {
                // No background painting, keeping it fully transparent
                super.paintComponent(g);
            }
        };

        searchButton.setBounds(550, 20, 100, 30);
        searchButton.setContentAreaFilled(false); // Disable default background
        searchButton.setFocusPainted(false); // Disable focus highlight
        searchButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // White border with thickness of 2
        searchButton.setForeground(Color.WHITE); // Set text color to white
        searchButton.setFont(new Font("Helvetica", Font.BOLD, 14)); // Font customization
        searchButton.addActionListener(e -> filterCards());
        contentPane.add(searchButton);


        // Panel to hold cards in a grid
        cardsPanel = new JPanel();
        cardsPanel.setLayout(new GridLayout(0, 3, 10, 10));
        cardsPanel.setOpaque(false);

        // ScrollPane to make the cards panel scrollable
        scrollPane = new JScrollPane(cardsPanel);
        scrollPane.setBounds(50, 80, 800, 650);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        contentPane.add(scrollPane);

        // Fetch data and create cards
        fetchData("");
    }

    private void fetchData(String filter) {
        cardsPanel.removeAll();

        // Database connection parameters
        String url = "jdbc:sqlserver://Javeria\\SQLEXPRESS;databaseName=SCD;encrypt=false;trustServerCertificate=true;user=Javeria;password=JAVERIANOOR123";
        String query = "SELECT name, description, price, colour FROM tablets";

        if (!filter.isEmpty()) {
            query += " WHERE name LIKE ? OR description LIKE ?";
        }

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(query)) {

            if (!filter.isEmpty()) {
                String searchTerm = "%" + filter + "%";
                statement.setString(1, searchTerm);
                statement.setString(2, searchTerm);
            }

            ResultSet resultSet = statement.executeQuery();

            // For each entry, create a card
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String price = resultSet.getString("price");
                String color = resultSet.getString("colour");

                // Create card
                JPanel card = createCard(name, description, price, color);
                cardsPanel.add(card);
            }
            cardsPanel.revalidate();
            cardsPanel.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createCard(String name, String description, String price, String color) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(250, 300));
        card.setBackground(new Color(255, 255, 255, 220));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 0));
        card.setOpaque(false);

        // Image placeholder
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\javer\\OneDrive\\Desktop\\pt_ipad_pro__6bgrkek0jnm2_xlarge.png");
        Image resizedImage = originalIcon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel imageLabel = new JLabel(resizedIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(imageLabel, BorderLayout.NORTH);

        // Content Panel for text
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(nameLabel);

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setForeground(Color.WHITE);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(descriptionLabel);

        JLabel colorLabel = new JLabel("Color: " + color);
        colorLabel.setForeground(Color.WHITE);
        colorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(colorLabel);

        JLabel priceLabel = new JLabel("Price: " + price);
        priceLabel.setForeground(Color.WHITE);
        priceLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(priceLabel);

        card.add(contentPanel, BorderLayout.CENTER);

        return card;
    }

    private void filterCards() {
        String filter = searchField.getText().trim();
        fetchData(filter);
    }

    private void showInsertDialog() {
        // Code to handle insert operation can go here
        JOptionPane.showMessageDialog(this, "Insert functionality is not implemented yet.", "Insert", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            iPadDisplay  frame = new iPadDisplay ();
            frame.setVisible(true);
        });
    }
}
