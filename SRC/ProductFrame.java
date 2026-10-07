import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductFrame extends JFrame {

    private JTable productTable;

    private int buyerId;

    private final Color DARK = new Color(30, 41, 59);
    private final Color BLUE = new Color(37, 99, 235);
    private final Color LIGHT = new Color(248, 250, 252);

    public ProductFrame(int buyerId) {

        this.buyerId = buyerId;

        setTitle("Available Products - Online E-Commerce Platform");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(LIGHT);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel("AVAILABLE PRODUCTS");

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
        // TABLE
        // =========================

        String[] columns = {
                "Product ID",
                "Product Name",
                "Price",
                "Stock",
                "Category"
        };

        DefaultTableModel model =
                new DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {
                        return false;
                    }
                };

        productTable = new JTable(model);

        productTable.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        productTable.setRowHeight(30);

        productTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        productTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        productTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane =
                new JScrollPane(productTable);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTON
        // =========================

        JButton addToCartButton =
                new JButton("ADD TO CART");

        addToCartButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        addToCartButton.setForeground(Color.WHITE);
        addToCartButton.setBackground(BLUE);
        addToCartButton.setFocusPainted(false);

        addToCartButton.setPreferredSize(
                new Dimension(200, 45)
        );

        addToCartButton.addActionListener(
                e -> addToCart()
        );

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.setBackground(LIGHT);

        buttonPanel.add(addToCartButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        setContentPane(mainPanel);

        loadProducts();
    }

    // =========================
    // LOAD PRODUCTS
    // =========================

    private void loadProducts() {

        String sql =
                "SELECT PRODUCT_ID, PRODUCT_NAME, PRICE, STOCK, CATEGORY " +
                "FROM PRODUCTS";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet result =
                        statement.executeQuery()
        ) {

            DefaultTableModel model =
                    (DefaultTableModel)
                            productTable.getModel();

            while (result.next()) {

                Object[] row = {

                        result.getInt("PRODUCT_ID"),

                        result.getString("PRODUCT_NAME"),

                        result.getDouble("PRICE"),

                        result.getInt("STOCK"),

                        result.getString("CATEGORY")
                };

                model.addRow(row);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load products:\n" +
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // ADD TO CART
    // =========================

    private void addToCart() {

        int selectedRow =
                productTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product first.",
                    "Add to Cart",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int productId =
                (int) productTable.getValueAt(
                        selectedRow,
                        0
                );

        int stock =
                (int) productTable.getValueAt(
                        selectedRow,
                        3
                );

        String quantityInput =
                JOptionPane.showInputDialog(
                        this,
                        "Enter quantity:"
                );

        if (quantityInput == null) {
            return;
        }

        try {

            int quantity =
                    Integer.parseInt(
                            quantityInput.trim()
                    );

            if (quantity <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Quantity must be greater than 0.",
                        "Invalid Quantity",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (quantity > stock) {

                JOptionPane.showMessageDialog(
                        this,
                        "Not enough stock available.",
                        "Stock Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            String sql =
                    "INSERT INTO CART " +
                    "(CART_ID, BUYER_ID, PRODUCT_ID, QUANTITY) " +
                    "VALUES " +
                    "(CART_SEQ.NEXTVAL, ?, ?, ?)";

            try (
                    Connection connection =
                            DBConnection.getConnection();

                    PreparedStatement statement =
                            connection.prepareStatement(sql)
            ) {

                statement.setInt(1, buyerId);
                statement.setInt(2, productId);
                statement.setInt(3, quantity);

                statement.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Product added to cart!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid quantity.",
                    "Invalid Quantity",
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
        }
    }
}