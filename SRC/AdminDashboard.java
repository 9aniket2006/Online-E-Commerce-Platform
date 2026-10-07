import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    private int adminId;
    private String adminName;

    private final Color DARK = new Color(30, 41, 59);
    private final Color BLUE = new Color(37, 99, 235);
    private final Color LIGHT = new Color(248, 250, 252);

    public AdminDashboard(int adminId, String adminName) {

        this.adminId = adminId;
        this.adminName = adminName;

        setTitle("Admin Dashboard - Online E-Commerce Platform");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(LIGHT);
        mainPanel.setLayout(null);

        // =========================
        // HEADER
        // =========================

        JLabel titleLabel =
                new JLabel("ADMIN DASHBOARD");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        titleLabel.setForeground(DARK);
        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        titleLabel.setBounds(100, 30, 400, 35);

        mainPanel.add(titleLabel);

        JLabel welcomeLabel =
                new JLabel("Welcome, " + adminName);

        welcomeLabel.setFont(
                new Font("Arial", Font.PLAIN, 17)
        );

        welcomeLabel.setForeground(BLUE);
        welcomeLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        welcomeLabel.setBounds(100, 70, 400, 30);

        mainPanel.add(welcomeLabel);

        // =========================
        // MANAGE USERS
        // =========================

        JButton usersButton =
                new JButton("MANAGE USERS");

        usersButton.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        usersButton.setForeground(Color.WHITE);
        usersButton.setBackground(BLUE);
        usersButton.setFocusPainted(false);

        usersButton.setBounds(150, 125, 300, 50);

        mainPanel.add(usersButton);

        // =========================
        // MANAGE PRODUCTS
        // =========================

        JButton productsButton =
                new JButton("MANAGE PRODUCTS");

        productsButton.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        productsButton.setForeground(Color.WHITE);
        productsButton.setBackground(BLUE);
        productsButton.setFocusPainted(false);

        productsButton.setBounds(150, 195, 300, 50);

        mainPanel.add(productsButton);

        // =========================
        // VIEW ORDERS
        // =========================

        JButton ordersButton =
                new JButton("VIEW ORDERS");

        ordersButton.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        ordersButton.setForeground(Color.WHITE);
        ordersButton.setBackground(BLUE);
        ordersButton.setFocusPainted(false);

        ordersButton.setBounds(150, 265, 300, 50);

        mainPanel.add(ordersButton);

        // =========================
        // BUTTON ACTIONS
        // =========================

        usersButton.addActionListener(e -> {

            new AdminUsersFrame().setVisible(true);

        });

        productsButton.addActionListener(e -> {

            new AdminProductsFrame().setVisible(true);

        });

        ordersButton.addActionListener(e -> {

            new AdminOrdersFrame().setVisible(true);

        });

        setContentPane(mainPanel);
    }
}