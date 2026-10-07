public class OOPTest {

    public static void main(String[] args) {

        User user1 =
                new Buyer(3, "Buyer User");

        User user2 =
                new Seller(2, "Seller User");

        user1.showDashboard();

        user2.showDashboard();
    }
}