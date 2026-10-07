import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public void displayUsers() {

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

            System.out.println("USER LIST");
            System.out.println("-------------------------");

            while (result.next()) {

                System.out.println(
                        "ID: " +
                        result.getInt("USER_ID")
                );

                System.out.println(
                        "Name: " +
                        result.getString("NAME")
                );

                System.out.println(
                        "Email: " +
                        result.getString("EMAIL")
                );

                System.out.println(
                        "Role: " +
                        result.getString("ROLE")
                );

                System.out.println("-------------------------");
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to load users."
            );

            e.printStackTrace();
        }
    }
}