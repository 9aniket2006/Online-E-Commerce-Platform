import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CartFrame extends JFrame {

    private int buyerId;
    private JTable cartTable;
    private JLabel totalLabel;

    private final Color DARK = new Color(30, 41, 59);
    private final Color BLUE = new Color(37, 99, 235);
    private final Color LIGHT = new Color(248, 250, 252);

    public CartFrame(int buyerId) {

        this.buyerId = buyerId;

        setTitle("My Cart - Online E-Commerce Platform");
        setSize(800, 500);
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
                new JLabel("MY SHOPPING CART");

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
                "Cart ID",
                "Product",
                "Price",
                "Quantity",
                "Total"
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

        cartTable = new JTable(model);

        cartTable.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        cartTable.setRowHeight(30);

        cartTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        cartTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        cartTable.getTableHeader()
                .setReorderingAllowed(false);

        JScrollPane scrollPane =
                new JScrollPane(cartTable);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =========================
        // BOTTOM PANEL
        // =========================

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.setBackground(LIGHT);

        totalLabel =
                new JLabel("Total: ₹0.00");

        totalLabel.setFont(
                new Font("Arial", Font.BOLD, 19)
        );

        totalLabel.setForeground(DARK);

        bottomPanel.add(
                totalLabel,
                BorderLayout.WEST
        );

        JButton orderButton =
                new JButton("PLACE ORDER");

        orderButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        orderButton.setForeground(Color.WHITE);
        orderButton.setBackground(BLUE);
        orderButton.setFocusPainted(false);

        orderButton.setPreferredSize(
                new Dimension(180, 45)
        );

        orderButton.addActionListener(
                e -> placeOrder()
        );

        bottomPanel.add(
                orderButton,
                BorderLayout.EAST
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        setContentPane(mainPanel);

        loadCart();
    }

    // =========================
    // LOAD CART
    // =========================

    private void loadCart() {

        String sql =
                "SELECT C.CART_ID, P.PRODUCT_NAME, " +
                "P.PRICE, C.QUANTITY, " +
                "(P.PRICE * C.QUANTITY) AS TOTAL " +
                "FROM CART C " +
                "JOIN PRODUCTS P " +
                "ON C.PRODUCT_ID = P.PRODUCT_ID " +
                "WHERE C.BUYER_ID = ?";

        double grandTotal = 0;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, buyerId);

            ResultSet result =
                    statement.executeQuery();

            DefaultTableModel model =
                    (DefaultTableModel)
                            cartTable.getModel();

            while (result.next()) {

                double itemTotal =
                        result.getDouble("TOTAL");

                Object[] row = {

                        result.getInt("CART_ID"),

                        result.getString("PRODUCT_NAME"),

                        result.getDouble("PRICE"),

                        result.getInt("QUANTITY"),

                        itemTotal
                };

                model.addRow(row);

                grandTotal += itemTotal;
            }

            totalLabel.setText(
                    String.format(
                            "Total: ₹%.2f",
                            grandTotal
                    )
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load cart:\n" +
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // PLACE ORDER
    // =========================

    private void placeOrder() {

        String checkCartSQL =
                "SELECT COUNT(*) FROM CART " +
                "WHERE BUYER_ID = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement checkStatement =
                        connection.prepareStatement(
                                checkCartSQL
                        )
        ) {

            checkStatement.setInt(1, buyerId);

            ResultSet checkResult =
                    checkStatement.executeQuery();

            checkResult.next();

            if (checkResult.getInt(1) == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Your cart is empty.",
                        "Empty Cart",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to check cart:\n" +
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to place this order?",
                        "Confirm Order",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        Connection connection = null;

        try {

            connection =
                    DBConnection.getConnection();

            connection.setAutoCommit(false);

            // =========================
            // STEP 1: CALCULATE TOTAL
            // =========================

            String totalSQL =
                    "SELECT SUM(P.PRICE * C.QUANTITY) AS TOTAL " +
                    "FROM CART C " +
                    "JOIN PRODUCTS P " +
                    "ON C.PRODUCT_ID = P.PRODUCT_ID " +
                    "WHERE C.BUYER_ID = ?";

            PreparedStatement totalStatement =
                    connection.prepareStatement(totalSQL);

            totalStatement.setInt(1, buyerId);

            ResultSet totalResult =
                    totalStatement.executeQuery();

            totalResult.next();

            double totalAmount =
                    totalResult.getDouble("TOTAL");

            // =========================
            // STEP 2: CREATE ORDER
            // =========================

            String orderSQL =
                    "INSERT INTO ORDERS " +
                    "(ORDER_ID, BUYER_ID, TOTAL_AMOUNT, STATUS) " +
                    "VALUES " +
                    "(ORDER_SEQ.NEXTVAL, ?, ?, 'Pending')";

            PreparedStatement orderStatement =
                    connection.prepareStatement(orderSQL);

            orderStatement.setInt(1, buyerId);
            orderStatement.setDouble(2, totalAmount);

            orderStatement.executeUpdate();

            // =========================
            // STEP 3: GET ORDER ID
            // =========================

            String idSQL =
                    "SELECT ORDER_SEQ.CURRVAL FROM DUAL";

            PreparedStatement idStatement =
                    connection.prepareStatement(idSQL);

            ResultSet idResult =
                    idStatement.executeQuery();

            idResult.next();

            int orderId =
                    idResult.getInt(1);

            // =========================
            // STEP 4: INSERT ORDER ITEMS
            // =========================

            String itemSQL =
                    "INSERT INTO ORDER_ITEMS " +
                    "(ORDER_ITEM_ID, ORDER_ID, PRODUCT_ID, QUANTITY) " +
                    "VALUES " +
                    "(ORDER_ITEM_SEQ.NEXTVAL, ?, ?, ?)";

            PreparedStatement itemStatement =
                    connection.prepareStatement(itemSQL);

            String cartSQL =
                    "SELECT PRODUCT_ID, QUANTITY " +
                    "FROM CART " +
                    "WHERE BUYER_ID = ?";

            PreparedStatement cartStatement =
                    connection.prepareStatement(cartSQL);

            cartStatement.setInt(1, buyerId);

            ResultSet cartResult =
                    cartStatement.executeQuery();

            while (cartResult.next()) {

                int productId =
                        cartResult.getInt("PRODUCT_ID");

                int quantity =
                        cartResult.getInt("QUANTITY");

                itemStatement.setInt(1, orderId);
                itemStatement.setInt(2, productId);
                itemStatement.setInt(3, quantity);

                itemStatement.executeUpdate();
            }

            // =========================
            // STEP 5: REDUCE STOCK
            // =========================

            String stockSQL =
                    "UPDATE PRODUCTS " +
                    "SET STOCK = STOCK - ? " +
                    "WHERE PRODUCT_ID = ?";

            PreparedStatement stockStatement =
                    connection.prepareStatement(stockSQL);

            String cartStockSQL =
                    "SELECT PRODUCT_ID, QUANTITY " +
                    "FROM CART " +
                    "WHERE BUYER_ID = ?";

            PreparedStatement cartStockStatement =
                    connection.prepareStatement(cartStockSQL);

            cartStockStatement.setInt(1, buyerId);

            ResultSet stockResult =
                    cartStockStatement.executeQuery();

            while (stockResult.next()) {

                int productId =
                        stockResult.getInt("PRODUCT_ID");

                int quantity =
                        stockResult.getInt("QUANTITY");

                stockStatement.setInt(1, quantity);
                stockStatement.setInt(2, productId);

                stockStatement.executeUpdate();
            }

            // =========================
            // STEP 6: CLEAR CART
            // =========================

            String deleteSQL =
                    "DELETE FROM CART " +
                    "WHERE BUYER_ID = ?";

            PreparedStatement deleteStatement =
                    connection.prepareStatement(deleteSQL);

            deleteStatement.setInt(1, buyerId);

            deleteStatement.executeUpdate();

            // =========================
            // SAVE EVERYTHING
            // =========================

            connection.commit();

            JOptionPane.showMessageDialog(
                    this,
                    "Order Placed Successfully!\n\n" +
                    "Order ID: " + orderId +
                    "\nTotal: ₹" + totalAmount,
                    "Order Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (Exception e) {

            try {

                if (connection != null) {
                    connection.rollback();
                }

            } catch (Exception rollbackError) {

                rollbackError.printStackTrace();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Order failed:\n" +
                    e.getMessage(),
                    "Order Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();

        } finally {

            try {

                if (connection != null) {
                    connection.close();
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
}