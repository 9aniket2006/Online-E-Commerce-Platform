public class CartDAOTest {

    public static void main(String[] args) {

        CartDAO cartDAO =
                new CartDAO();

        // Buyer ID 3
        cartDAO.displayCart(3);
    }
}