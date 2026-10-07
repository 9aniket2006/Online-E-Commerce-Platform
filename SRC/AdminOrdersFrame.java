import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminOrdersFrame extends JFrame {

    public AdminOrdersFrame() {

        setTitle("Manage Orders - Online E-Commerce Platform");
        setSize(1000, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main background
        getContentPane().setBackground(new Color(248, 250, 252));

        // Title
        JLabel title = new JLabel("ALL CUSTOMER ORDERS");

        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(30, 41, 59));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        title.setBorder(
                BorderFactory.createEmptyBorder(20, 10, 20, 10)
        );

        // Table columns
        String[] columns = {
                "Order ID",
                "Buyer ID",
                "Product",
                "Quantity",
                "Price",
                "Status",
                "Order Date"
        };

        // Non-editable table model
        DefaultTableModel model =
                new DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

        // Table
        JTable orderTable = new JTable(model);

        orderTable.setRowHeight(30);
        orderTable.setFont(new Font("Arial", Font.PLAIN, 14));
        orderTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        orderTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        orderTable.getTableHeader().setReorderingAllowed(false);

        // Scroll pane
        JScrollPane scrollPane =
                new JScrollPane(orderTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(203, 213, 225)
                )
        );

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));

        mainPanel.setBackground(new Color(248, 250, 252));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 20, 20, 20
                )
        );

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        // Load orders
        loadOrders(model);
    }

    private void loadOrders(DefaultTableModel model) {

        String sql =
                "SELECT o.ORDER_ID, o.BUYER_ID, " +
                "p.PRODUCT_NAME, oi.QUANTITY, " +
                "p.PRICE, o.STATUS, o.ORDER_DATE " +
                "FROM ORDERS o " +
                "JOIN ORDER_ITEMS oi " +
                "ON o.ORDER_ID = oi.ORDER_ID " +
                "JOIN PRODUCTS p " +
                "ON oi.PRODUCT_ID = p.PRODUCT_ID " +
                "ORDER BY o.ORDER_DATE DESC";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet result =
                        statement.executeQuery()
        ) {

            while (result.next()) {

                Object[] row = {

                        result.getInt("ORDER_ID"),

                        result.getInt("BUYER_ID"),

                        result.getString("PRODUCT_NAME"),

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
                    "Unable to load orders:\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }
}