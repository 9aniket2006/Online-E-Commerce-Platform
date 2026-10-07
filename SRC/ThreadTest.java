public class ThreadTest {

    public static void main(String[] args) {

        OrderProcessor order1 =
                new OrderProcessor();

        OrderProcessor order2 =
                new OrderProcessor();

        order1.setName("Order Thread 1");
        order2.setName("Order Thread 2");

        order1.start();
        order2.start();
    }
}