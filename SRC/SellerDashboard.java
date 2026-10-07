import javax.swing.*;
import java.awt.*;

public class SellerDashboard extends JFrame {

    private int sellerId;
    private String sellerName;

    private final Color DARK = new Color(30, 41, 59);
    private final Color BLUE = new Color(37, 99, 235);
    private final Color LIGHT = new Color(248, 250, 252);

    public SellerDashboard(int sellerId, String sellerName) {

        this.sellerId = sellerId;
        this.sellerName = sellerName;

        setTitle("Seller Dashboard - Online E-Commerce Platform");
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
                new JLabel("SELLER DASHBOARD");

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
                new JLabel("Welcome, " + sellerName);

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
        // MY PRODUCTS
        // =========================

        JButton productsButton =
                new JButton("MY PRODUCTS");

        productsButton.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        productsButton.setForeground(Color.WHITE);
        productsButton.setBackground(BLUE);
        productsButton.setFocusPainted(false);

        productsButton.setBounds(150, 125, 300, 50);

        mainPanel.add(productsButton);

        // =========================
        // ADD PRODUCT
        // =========================

        JButton addProductButton =
                new JButton("ADD PRODUCT");

        addProductButton.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        addProductButton.setForeground(Color.WHITE);
        addProductButton.setBackground(BLUE);
        addProductButton.setFocusPainted(false);

        addProductButton.setBounds(150, 195, 300, 50);

        mainPanel.add(addProductButton);

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

        productsButton.addActionListener(e -> {

            new SellerProductsFrame(sellerId).setVisible(true);

        });

        addProductButton.addActionListener(e -> {

            new AddProductFrame(sellerId).setVisible(true);

        });

        ordersButton.addActionListener(e -> {

            new SellerOrdersFrame(sellerId).setVisible(true);

        });

        setContentPane(mainPanel);
    }
}