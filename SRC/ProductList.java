import java.util.ArrayList;
import java.util.List;

public class ProductList<T> {

    private List<T> products;

    public ProductList() {

        products = new ArrayList<>();
    }

    public void addProduct(T product) {

        products.add(product);
    }

    public T getProduct(int index) {

        return products.get(index);
    }

    public int size() {

        return products.size();
    }

    public void displayProducts() {

        for (T product : products) {

            System.out.println(product);
        }
    }
}