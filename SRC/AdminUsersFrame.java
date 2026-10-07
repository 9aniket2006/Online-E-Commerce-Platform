import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminUsersFrame extends JFrame {

    private JTable userTable;

    private final Color DARK = new Color(30, 41, 59);
    private final Color BLUE = new Color(37, 99, 235);
    private final Color LIGHT = new Color(248, 250, 252);

    public AdminUsersFrame() {

        setTitle("Manage Users - Online E-Commerce Platform");
        setSize(850, 500);
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
                new JLabel("MANAGE USERS");

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
        // USER TABLE
        // =========================

        String[] columns = {
                "User ID",
                "Name",
                "Email",
                "Role"
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

        userTable =
                new JTable(model);

        userTable.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        userTable.setRowHeight(30);

        userTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        userTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        userTable.getTableHeader()
                .setReorderingAllowed(false);

        JScrollPane scrollPane =
                new JScrollPane(userTable);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =========================
        // DELETE BUTTON
        // =========================

        JButton deleteButton =
                new JButton("DELETE USER");

        deleteButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        deleteButton.setForeground(Color.WHITE);

        deleteButton.setBackground(BLUE);

        deleteButton.setFocusPainted(false);

        deleteButton.setPreferredSize(
                new Dimension(160, 40)
        );

        deleteButton.addActionListener(
                e -> deleteUser()
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

        // Load users
        loadUsers();
    }

    // =========================
    // LOAD USERS
    // =========================

    private void loadUsers() {

        String sql =
                "SELECT USER_ID, NAME, EMAIL, ROLE " +
                "FROM USERS " +
                "ORDER BY USER_ID";

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
                            userTable.getModel();

            model.setRowCount(0);

            while (result.next()) {

                Object[] row = {

                        result.getInt("USER_ID"),

                        result.getString("NAME"),

                        result.getString("EMAIL"),

                        result.getString("ROLE")
                };

                model.addRow(row);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load users:\n" +
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // DELETE USER
    // =========================

    private void deleteUser() {

        int selectedRow =
                userTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user first.",
                    "No User Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int userId =
                (int) userTable.getValueAt(
                        selectedRow,
                        0
                );

        String name =
                userTable.getValueAt(
                        selectedRow,
                        1
                ).toString();

        String role =
                userTable.getValueAt(
                        selectedRow,
                        3
                ).toString();

        // Prevent deleting admin

        if (role.equalsIgnoreCase("ADMIN")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Admin account cannot be deleted.",
                    "Action Not Allowed",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete user: " + name + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        String sql =
                "DELETE FROM USERS " +
                "WHERE USER_ID = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            statement.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "User deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadUsers();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to delete user.\n" +
                    "This user may be connected to " +
                    "products or orders.",
                    "Delete Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }
}