import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminProductsFrame extends JFrame {

    private JTable productTable;

    private final Color DARK = new Color(30, 41, 59);
    private final Color BLUE = new Color(37, 99, 235);
    private final Color LIGHT = new Color(248, 250, 252);

    public AdminProductsFrame() {

        setTitle("Manage Products - Online E-Commerce Platform");
        setSize(950, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // =========================
        // MAIN PANEL
        // =========================

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

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
                new JLabel("MANAGE PRODUCTS");

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
        // PRODUCT TABLE
        // =========================

        String[] columns = {
                "Product ID",
                "Seller ID",
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

        productTable =
                new JTable(model);

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

        productTable.getTableHeader()
                .setReorderingAllowed(false);

        JScrollPane scrollPane =
                new JScrollPane(productTable);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =========================
        // DELETE BUTTON
        // =========================

        JButton deleteButton =
                new JButton("DELETE PRODUCT");

        deleteButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        deleteButton.setForeground(Color.WHITE);

        deleteButton.setBackground(BLUE);

        deleteButton.setFocusPainted(false);

        deleteButton.setPreferredSize(
                new Dimension(180, 40)
        );

        deleteButton.addActionListener(
                e -> deleteProduct()
        );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );

        buttonPanel.setBackground(LIGHT);

        buttonPanel.add(deleteButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        setContentPane(mainPanel);

        // Load products
        loadProducts();
    }

    // =========================
    // LOAD PRODUCTS
    // =========================

    private void loadProducts() {

        String sql =
                "SELECT PRODUCT_ID, SELLER_ID, " +
                "PRODUCT_NAME, PRICE, STOCK, CATEGORY " +
                "FROM PRODUCTS " +
                "ORDER BY PRODUCT_ID";

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

            model.setRowCount(0);

            while (result.next()) {

                Object[] row = {

                        result.getInt("PRODUCT_ID"),

                        result.getInt("SELLER_ID"),

                        result.getString(
                                "PRODUCT_NAME"
                        ),

                        result.getDouble("PRICE"),

                        result.getInt("STOCK"),

                        result.getString(
                                "CATEGORY"
                        )
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
    // DELETE PRODUCT
    // =========================

    private void deleteProduct() {

        int selectedRow =
                productTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product first.",
                    "No Product Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int productId =
                (int) productTable.getValueAt(
                        selectedRow,
                        0
                );

        String productName =
                productTable.getValueAt(
                        selectedRow,
                        2
                ).toString();

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete product: " +
                        productName + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        String sql =
                "DELETE FROM PRODUCTS " +
                "WHERE PRODUCT_ID = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    productId
            );

            statement.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Product deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadProducts();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to delete product.\n" +
                    "This product may already be used " +
                    "in an order or cart.",
                    "Delete Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }
}