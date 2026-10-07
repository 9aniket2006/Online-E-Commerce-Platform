import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CartDAO {

    public void displayCart(int buyerId) {

        String sql =
                "SELECT c.CART_ID, " +
                "p.PRODUCT_NAME, " +
                "p.PRICE, " +
                "c.QUANTITY, " +
                "(p.PRICE * c.QUANTITY) AS TOTAL " +
                "FROM CART c " +
                "JOIN PRODUCTS p " +
                "ON c.PRODUCT_ID = p.PRODUCT_ID " +
                "WHERE c.BUYER_ID = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, buyerId);

            ResultSet result =
                    statement.executeQuery();

            System.out.println("SHOPPING CART");
            System.out.println("-------------------------");

            while (result.next()) {

                System.out.println(
                        "Cart ID: " +
                        result.getInt("CART_ID")
                );

                System.out.println(
                        "Product: " +
                        result.getString("PRODUCT_NAME")
                );

                System.out.println(
                        "Price: " +
                        result.getDouble("PRICE")
                );

                System.out.println(
                        "Quantity: " +
                        result.getInt("QUANTITY")
                );

                System.out.println(
                        "Total: " +
                        result.getDouble("TOTAL")
                );

                System.out.println("-------------------------");
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to load cart."
            );

            e.printStackTrace();
        }
    }
}