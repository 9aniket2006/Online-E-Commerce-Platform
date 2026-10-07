public class Seller extends User {

    public Seller(int userId, String name) {

        super(userId, name);
    }

    @Override
    public void showDashboard() {

        System.out.println(
                "Seller Dashboard: " + name
        );
    }
}