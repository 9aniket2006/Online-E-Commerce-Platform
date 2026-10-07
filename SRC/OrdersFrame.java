import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrdersFrame extends JFrame {

    private int buyerId;

    private final Color DARK = new Color(30, 41, 59);
    private final Color BLUE = new Color(37, 99, 235);
    private final Color LIGHT = new Color(248, 250, 252);

    public OrdersFrame(int buyerId) {

        this.buyerId = buyerId;

        setTitle("My Orders - Online E-Commerce Platform");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

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
                new JLabel("MY ORDERS");

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
                "Order ID",
                "Total Amount",
                "Status",
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

        loadOrders(model);
    }

    // =========================
    // LOAD ORDERS
    // =========================

    private void loadOrders(
            DefaultTableModel model) {

        String sql =
                "SELECT ORDER_ID, TOTAL_AMOUNT, STATUS, ORDER_DATE " +
                "FROM ORDERS " +
                "WHERE BUYER_ID = ? " +
                "ORDER BY ORDER_DATE DESC";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, buyerId);

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                Object[] row = {

                        result.getInt("ORDER_ID"),

                        result.getDouble("TOTAL_AMOUNT"),

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
        }
    }
}