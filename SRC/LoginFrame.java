import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    // Colors
    private final Color DARK = new Color(30, 41, 59);
    private final Color BLUE = new Color(37, 99, 235);
    private final Color LIGHT = new Color(248, 250, 252);

    public LoginFrame() {

        setTitle("Online E-Commerce Platform");
        setSize(500, 430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(LIGHT);
        mainPanel.setLayout(null);

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel =
                new JLabel("ONLINE E-COMMERCE");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        titleLabel.setForeground(DARK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(70, 30, 360, 35);

        mainPanel.add(titleLabel);

        JLabel subtitleLabel =
                new JLabel("Platform");

        subtitleLabel.setFont(
                new Font("Arial", Font.PLAIN, 17)
        );

        subtitleLabel.setForeground(BLUE);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setBounds(150, 65, 200, 25);

        mainPanel.add(subtitleLabel);

        // =========================
        // EMAIL
        // =========================

        JLabel emailLabel =
                new JLabel("Email");

        emailLabel.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        emailLabel.setBounds(80, 115, 100, 25);

        mainPanel.add(emailLabel);

        emailField = new JTextField();

        emailField.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        emailField.setBounds(80, 140, 340, 35);

        mainPanel.add(emailField);

        // =========================
        // PASSWORD
        // =========================

        JLabel passwordLabel =
                new JLabel("Password");

        passwordLabel.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        passwordLabel.setBounds(80, 185, 100, 25);

        mainPanel.add(passwordLabel);

        passwordField =
                new JPasswordField();

        passwordField.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        passwordField.setBounds(80, 210, 340, 35);

        mainPanel.add(passwordField);

        // =========================
        // LOGIN BUTTON
        // =========================

        loginButton =
                new JButton("LOGIN");

        loginButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(BLUE);
        loginButton.setFocusPainted(false);
        loginButton.setBounds(80, 270, 340, 40);

        mainPanel.add(loginButton);

        // =========================
        // REGISTER BUTTON
        // =========================

        registerButton =
                new JButton("CREATE NEW ACCOUNT");

        registerButton.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        registerButton.setForeground(DARK);
        registerButton.setBackground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBounds(80, 325, 340, 35);

        mainPanel.add(registerButton);

        // =========================
        // BUTTON ACTIONS
        // =========================

        loginButton.addActionListener(
                e -> loginUser()
        );

        registerButton.addActionListener(e -> {

            new RegisterFrame().setVisible(true);

        });

        setContentPane(mainPanel);
    }

    // =========================
    // LOGIN METHOD
    // =========================

    private void loginUser() {

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        if (email.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter email and password.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String sql =
                "SELECT USER_ID, NAME, ROLE " +
                "FROM USERS " +
                "WHERE EMAIL = ? " +
                "AND USER_PASSWORD = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                int userId =
                        result.getInt("USER_ID");

                String name =
                        result.getString("NAME");

                String role =
                        result.getString("ROLE");

                JOptionPane.showMessageDialog(
                        this,
                        "Login Successful!\n\n" +
                        "Welcome " + name +
                        "\nRole: " + role,
                        "Login Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );

                if (role.equalsIgnoreCase("BUYER")) {

                    BuyerDashboard buyerDashboard =
                            new BuyerDashboard(
                                    userId,
                                    name
                            );

                    buyerDashboard.setVisible(true);

                    this.dispose();
                }

                else if (role.equalsIgnoreCase("SELLER")) {

                    SellerDashboard sellerDashboard =
                            new SellerDashboard(
                                    userId,
                                    name
                            );

                    sellerDashboard.setVisible(true);

                    this.dispose();
                }

                else if (role.equalsIgnoreCase("ADMIN")) {

                    AdminDashboard adminDashboard =
                            new AdminDashboard(
                                    userId,
                                    name
                            );

                    adminDashboard.setVisible(true);

                    this.dispose();
                }

                else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Unknown user role: " + role
                    );
                }

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid email or password!",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error:\n" +
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            LoginFrame frame =
                    new LoginFrame();

            frame.setVisible(true);
        });
    }
}