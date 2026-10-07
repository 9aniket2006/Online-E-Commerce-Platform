public class User implements UserOperations {

    protected int userId;
    protected String name;

    public User(int userId, String name) {

        this.userId = userId;
        this.name = name;
    }

    @Override
    public void showDashboard() {

        System.out.println(
                "User Dashboard: " + name
        );
    }
}
