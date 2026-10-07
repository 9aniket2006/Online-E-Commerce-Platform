import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SellerProductsFrame extends JFrame {

    private int sellerId;
    private JTable productTable;

    private final Color DARK = new Color(30, 41, 59);
    private final Color LIGHT = new Color(248, 250, 252);

    public SellerProductsFrame(int sellerId) {

        this.sellerId = sellerId;

        setTitle("My Products - Online E-Commerce Platform");
        setSize(800, 450);
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
                new JLabel("MY PRODUCTS");

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

        setContentPane(mainPanel);

        // Load seller products
        loadProducts();
    }

    // =========================
    // LOAD PRODUCTS
    // =========================

    private void loadProducts() {

        String sql =
                "SELECT PRODUCT_ID, PRODUCT_NAME, " +
                "PRICE, STOCK, CATEGORY " +
                "FROM PRODUCTS " +
                "WHERE SELLER_ID = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, sellerId);

            ResultSet result =
                    statement.executeQuery();

            DefaultTableModel model =
                    (DefaultTableModel)
                            productTable.getModel();

            while (result.next()) {

                Object[] row = {

                        result.getInt("PRODUCT_ID"),

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

            e.printStackTrace();
        }
    }
}