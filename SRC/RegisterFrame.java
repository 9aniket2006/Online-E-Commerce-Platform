import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;

    public RegisterFrame() {

        setTitle("Create Account");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title =
                new JLabel("CREATE ACCOUNT");

        title.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        title.setBounds(130, 30, 250, 30);
        panel.add(title);

        // Name
        JLabel nameLabel =
                new JLabel("Name:");

        nameLabel.setBounds(50, 90, 100, 25);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(160, 90, 210, 25);
        panel.add(nameField);

        // Email
        JLabel emailLabel =
                new JLabel("Email:");

        emailLabel.setBounds(50, 130, 100, 25);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(160, 130, 210, 25);
        panel.add(emailField);

        // Password
        JLabel passwordLabel =
                new JLabel("Password:");

        passwordLabel.setBounds(50, 170, 100, 25);
        panel.add(passwordLabel);

        passwordField =
                new JPasswordField();

        passwordField.setBounds(160, 170, 210, 25);
        panel.add(passwordField);

        // Role
        JLabel roleLabel =
                new JLabel("Role:");

        roleLabel.setBounds(50, 210, 100, 25);
        panel.add(roleLabel);

        String[] roles = {
                "BUYER",
                "SELLER"
        };

        roleBox =
                new JComboBox<>(roles);

        roleBox.setBounds(160, 210, 210, 25);
        panel.add(roleBox);

        // Register Button
        JButton registerButton =
                new JButton("REGISTER");

        registerButton.setBounds(150, 270, 150, 35);
        panel.add(registerButton);

        registerButton.addActionListener(
                e -> registerUser()
        );

        setContentPane(panel);
    }

    private void registerUser() {

        String name =
                nameField.getText().trim();

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String role =
                roleBox.getSelectedItem().toString();

        if (name.isEmpty() ||
            email.isEmpty() ||
            password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return;
        }

        try {

            String idSQL =
                    "SELECT NVL(MAX(USER_ID), 0) + 1 " +
                    "FROM USERS";

            String insertSQL =
                    "INSERT INTO USERS " +
                    "(USER_ID, NAME, EMAIL, USER_PASSWORD, ROLE) " +
                    "VALUES (?, ?, ?, ?, ?)";

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

                int userId =
                        result.getInt(1);

                insertStatement.setInt(
                        1,
                        userId
                );

                insertStatement.setString(
                        2,
                        name
                );

                insertStatement.setString(
                        3,
                        email
                );

                insertStatement.setString(
                        4,
                        password
                );

                insertStatement.setString(
                        5,
                        role
                );

                insertStatement.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Account created successfully!"
                );

                dispose();
            }

        } catch (Exception e) {

            if (e.getMessage() != null &&
                e.getMessage().contains("ORA-00001")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Email already exists!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to create account:\n" +
                        e.getMessage(),
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

            e.printStackTrace();
        }
    }
}