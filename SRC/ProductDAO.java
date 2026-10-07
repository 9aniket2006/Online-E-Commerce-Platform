import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductDAO {

    public void displayProducts() {

        String sql =
                "SELECT PRODUCT_ID, PRODUCT_NAME, " +
                "PRICE, STOCK, CATEGORY " +
                "FROM PRODUCTS";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet result =
                        statement.executeQuery()
        ) {

            System.out.println("PRODUCT LIST");
            System.out.println("-------------------------");

            while (result.next()) {

                System.out.println(
                        "ID: " +
                        result.getInt("PRODUCT_ID")
                );

                System.out.println(
                        "Name: " +
                        result.getString("PRODUCT_NAME")
                );

                System.out.println(
                        "Price: " +
                        result.getDouble("PRICE")
                );

                System.out.println(
                        "Stock: " +
                        result.getInt("STOCK")
                );

                System.out.println(
                        "Category: " +
                        result.getString("CATEGORY")
                );

                System.out.println("-------------------------");
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to load products."
            );

            e.printStackTrace();
        }
    }
}