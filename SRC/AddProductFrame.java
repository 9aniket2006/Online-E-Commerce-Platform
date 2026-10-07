    import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddProductFrame extends JFrame {

    private int sellerId;

    private JTextField nameField;
    private JTextField priceField;
    private JTextField stockField;
    private JTextField categoryField;

    private final Color DARK = new Color(30, 41, 59);
    private final Color BLUE = new Color(37, 99, 235);
    private final Color LIGHT = new Color(248, 250, 252);

    public AddProductFrame(int sellerId) {

        this.sellerId = sellerId;

        setTitle("Add Product - Online E-Commerce Platform");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // =========================
        // MAIN PANEL
        // =========================

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(LIGHT);

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 45, 25, 45
                )
        );

        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel("ADD NEW PRODUCT");

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        title.setForeground(DARK);

        title.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        // =========================
        // FORM PANEL
        // =========================

        JPanel formPanel =
                new JPanel(
                        new GridLayout(4, 2, 15, 18)
                );

        formPanel.setBackground(LIGHT);

        JLabel nameLabel =
                new JLabel("Product Name:");

        JLabel priceLabel =
                new JLabel("Price:");

        JLabel stockLabel =
                new JLabel("Stock:");

        JLabel categoryLabel =
                new JLabel("Category:");

        nameField = new JTextField();
        priceField = new JTextField();
        stockField = new JTextField();
        categoryField = new JTextField();

        Font labelFont =
                new Font("Arial", Font.BOLD, 14);

        nameLabel.setFont(labelFont);
        priceLabel.setFont(labelFont);
        stockLabel.setFont(labelFont);
        categoryLabel.setFont(labelFont);

        formPanel.add(nameLabel);
        formPanel.add(nameField);

        formPanel.add(priceLabel);
        formPanel.add(priceField);

        formPanel.add(stockLabel);
        formPanel.add(stockField);

        formPanel.add(categoryLabel);
        formPanel.add(categoryField);

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTON
        // =========================

        JButton addButton =
                new JButton("ADD PRODUCT");

        addButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        addButton.setForeground(Color.WHITE);
        addButton.setBackground(BLUE);

        addButton.setFocusPainted(false);

        addButton.setPreferredSize(
                new Dimension(160, 40)
        );

        addButton.addActionListener(
                e -> addProduct()
        );

        JPanel buttonPanel =
                new JPanel(new FlowLayout(
                        FlowLayout.CENTER
                ));

        buttonPanel.setBackground(LIGHT);

        buttonPanel.add(addButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        setContentPane(mainPanel);
    }

    // =========================
    // ADD PRODUCT
    // =========================

    private void addProduct() {

        String name =
                nameField.getText().trim();

        String priceText =
                priceField.getText().trim();

        String stockText =
                stockField.getText().trim();

        String category =
                categoryField.getText().trim();

        // Check empty fields

        if (name.isEmpty() ||
                priceText.isEmpty() ||
                stockText.isEmpty() ||
                category.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            double price =
                    Double.parseDouble(priceText);

            int stock =
                    Integer.parseInt(stockText);

            if (price <= 0 || stock < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enter valid price and stock.",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // Generate a new Product ID

            String idSQL =
                    "SELECT NVL(MAX(PRODUCT_ID), 0) + 1 " +
                    "FROM PRODUCTS";

            String insertSQL =
                    "INSERT INTO PRODUCTS " +
                    "(PRODUCT_ID, SELLER_ID, PRODUCT_NAME, " +
                    "PRICE, STOCK, CATEGORY) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            try (
                    Connection connection =
                            DBConnection.getConnection();

                    PreparedStatement idStatement =
                            connection.prepareStatement(idSQL);

                    PreparedStatement insertStatement =
                            connection.prepareStatement(insertSQL)
            ) {

                var result =
                        idStatement.executeQuery();

                result.next();

                int productId =
                        result.getInt(1);

                insertStatement.setInt(
                        1,
                        productId
                );

                insertStatement.setInt(
                        2,
                        sellerId
                );

                insertStatement.setString(
                        3,
                        name
                );

                insertStatement.setDouble(
                        4,
                        price
                );

                insertStatement.setInt(
                        5,
                        stock
                );

                insertStatement.setString(
                        6,
                        category
                );

                insertStatement.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Product added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Clear fields

                nameField.setText("");
                priceField.setText("");
                stockField.setText("");
                categoryField.setText("");
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Price must be a number and stock " +
                    "must be a whole number.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to add product:\n" +
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }
}