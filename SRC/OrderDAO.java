import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderDAO {

    public void displayOrders() {

        String sql =
                "SELECT ORDER_ID, BUYER_ID, " +
                "TOTAL_AMOUNT, STATUS, ORDER_DATE " +
                "FROM ORDERS " +
                "ORDER BY ORDER_DATE DESC";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet result =
                        statement.executeQuery()
        ) {

            System.out.println("ORDER LIST");
            System.out.println("-------------------------");

            while (result.next()) {

                System.out.println(
                        "Order ID: " +
                        result.getInt("ORDER_ID")
                );

                System.out.println(
                        "Buyer ID: " +
                        result.getInt("BUYER_ID")
                );

                System.out.println(
                        "Total Amount: " +
                        result.getDouble("TOTAL_AMOUNT")
                );

                System.out.println(
                        "Status: " +
                        result.getString("STATUS")
                );

                System.out.println(
                        "Order Date: " +
                        result.getDate("ORDER_DATE")
                );

                System.out.println("-------------------------");
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to load orders."
            );

            e.printStackTrace();
        }
    }
}