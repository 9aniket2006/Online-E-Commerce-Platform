import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SellerOrdersFrame extends JFrame {

    private int sellerId;

    private final Color DARK = new Color(30, 41, 59);
    private final Color LIGHT = new Color(248, 250, 252);

    public SellerOrdersFrame(int sellerId) {

        this.sellerId = sellerId;

        setTitle("Seller Orders - Online E-Commerce Platform");
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
                new JLabel("CUSTOMER ORDERS");

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
        // ORDER TABLE
        // =========================

        String[] columns = {
                "Order ID",
                "Buyer ID",
                "Product",
                "Quantity",
                "Price",
                "Order Status",
                "Order Date"
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

        JTable orderTable =
                new JTable(model);

        orderTable.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        orderTable.setRowHeight(30);

        orderTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        orderTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        orderTable.getTableHeader()
                .setReorderingAllowed(false);

        JScrollPane scrollPane =
                new JScrollPane(orderTable);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        setContentPane(mainPanel);

        // Load seller orders
        loadOrders(model);
    }

    // =========================
    // LOAD ORDERS
    // =========================

    private void loadOrders(
            DefaultTableModel model) {

        String sql =
                "SELECT o.ORDER_ID, o.BUYER_ID, " +
                "p.PRODUCT_NAME, oi.QUANTITY, " +
                "p.PRICE, o.STATUS, o.ORDER_DATE " +
                "FROM ORDERS o " +
                "JOIN ORDER_ITEMS oi " +
                "ON o.ORDER_ID = oi.ORDER_ID " +
                "JOIN PRODUCTS p " +
                "ON oi.PRODUCT_ID = p.PRODUCT_ID " +
                "WHERE p.SELLER_ID = ? " +
                "ORDER BY o.ORDER_DATE DESC";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, sellerId);

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                Object[] row = {

                        result.getInt("ORDER_ID"),

                        result.getInt("BUYER_ID"),

                        result.getString(
                                "PRODUCT_NAME"
                        ),

                        result.getInt("QUANTITY"),

                        result.getDouble("PRICE"),

                        result.getString("STATUS"),

                        result.getDate("ORDER_DATE")
                };

                model.addRow(row);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load orders:\n" +
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }
}