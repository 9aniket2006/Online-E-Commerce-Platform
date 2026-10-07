import javax.swing.*;
import java.awt.*;

public class BuyerDashboard extends JFrame {

    private int buyerId;
    private String buyerName;

    private final Color DARK = new Color(30, 41, 59);
    private final Color BLUE = new Color(37, 99, 235);
    private final Color LIGHT = new Color(248, 250, 252);

    public BuyerDashboard(int buyerId, String buyerName) {

        this.buyerId = buyerId;
        this.buyerName = buyerName;

        setTitle("Buyer Dashboard - Online E-Commerce Platform");
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
                new JLabel("BUYER DASHBOARD");

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
                new JLabel("Welcome, " + buyerName);

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
        // VIEW PRODUCTS
        // =========================

        JButton productsButton =
                new JButton("VIEW PRODUCTS");

        productsButton.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        productsButton.setForeground(Color.WHITE);
        productsButton.setBackground(BLUE);
        productsButton.setFocusPainted(false);

        productsButton.setBounds(150, 125, 300, 50);

        mainPanel.add(productsButton);

        // =========================
        // MY CART
        // =========================

        JButton cartButton =
                new JButton("MY CART");

        cartButton.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        cartButton.setForeground(Color.WHITE);
        cartButton.setBackground(BLUE);
        cartButton.setFocusPainted(false);

        cartButton.setBounds(150, 195, 300, 50);

        mainPanel.add(cartButton);

        // =========================
        // MY ORDERS
        // =========================

        JButton ordersButton =
                new JButton("MY ORDERS");

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

            new ProductFrame(buyerId).setVisible(true);

        });

        cartButton.addActionListener(e -> {

            new CartFrame(buyerId).setVisible(true);

        });

        ordersButton.addActionListener(e -> {

            new OrdersFrame(buyerId).setVisible(true);

        });

        setContentPane(mainPanel);
    }
}