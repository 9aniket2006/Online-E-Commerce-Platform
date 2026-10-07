public class CollectionsTest {

    public static void main(String[] args) {

        ProductList<String> products =
                new ProductList<>();

        products.addProduct("Laptop");
        products.addProduct("Keyboard");
        products.addProduct("Mouse");

        System.out.println("Products:");

        products.displayProducts();

        System.out.println(
                "Total Products: " +
                products.size()
        );
    }
}