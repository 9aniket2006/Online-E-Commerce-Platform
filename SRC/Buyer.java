public class Buyer extends User {

    public Buyer(int userId, String name) {

        super(userId, name);
    }

    @Override
    public void showDashboard() {

        System.out.println(
                "Buyer Dashboard: " + name
        );
    }
}