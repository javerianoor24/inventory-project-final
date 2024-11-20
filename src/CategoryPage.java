import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
class FrameFactory {
    public static JFrame createFrame(String category) {
        switch (category) {
            case "Phones":
                return new PhoneDisplay();
            case "Tablets":
                return new  iPadDisplay();
            case "Laptop":
                return new LaptopDisplay();
            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }
    }
}
class CategoryPage extends JFrame {
    private JPanel contentPane;

    public CategoryPage() {
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
                g.fillRoundRect(150, 300, 170, 70, 30, 30); // Behind Phones Button
                g.fillRoundRect(350, 300, 170, 70, 30, 30); // Behind Tablets Button
                g.fillRoundRect(550, 300, 170, 70, 30, 30); // Behind Laptop Button
            }
        };

        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel("Select a Category");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 34));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 130, 900, 80);
        contentPane.add(titleLabel);

        // Subtitle label
        JLabel subtitleLabel = new JLabel("Explore Our Range of Products");
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

        // Button: Phones
        JButton phonesButton = new JButton("Phones");
        phonesButton.setBounds(150, 300, 170, 70);
        phonesButton.setFont(new Font("Helvetica", Font.BOLD, 18));
        phonesButton.setForeground(Color.WHITE);
        phonesButton.setBackground(new Color(0, 100, 200, 200));
        phonesButton.setFocusPainted(false);
        contentPane.add(phonesButton);
        phonesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFrame("Phones");
            }

        });

        // Button: Tablets
        JButton tabletsButton = new JButton("Tablets");
        tabletsButton.setBounds(350, 300, 170, 70);
        tabletsButton.setFont(new Font("Helvetica", Font.BOLD, 18));
        tabletsButton.setForeground(Color.WHITE);
        tabletsButton.setBackground(new Color(0, 100, 200, 200));
        tabletsButton.setFocusPainted(false);
        contentPane.add(tabletsButton);
        tabletsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFrame("Tablets");
            }
        });

        // Button: Laptop
        JButton laptopCoversButton = new JButton("Laptop");
        laptopCoversButton.setBounds(550, 300, 170, 70);
        laptopCoversButton.setFont(new Font("Helvetica", Font.BOLD, 18));
        laptopCoversButton.setForeground(Color.WHITE);
        laptopCoversButton.setBackground(new Color(0, 100, 200, 200));
        laptopCoversButton.setFocusPainted(false);
        contentPane.add(laptopCoversButton);
        laptopCoversButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFrame("Laptop");
            }

        });
    }

    private void openFrame(String category) {
        JFrame frame = FrameFactory.createFrame(category);
        frame.setVisible(true);
        setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CategoryPage categoryPage = new CategoryPage();
            categoryPage.setVisible(true);
        });
    }
}
