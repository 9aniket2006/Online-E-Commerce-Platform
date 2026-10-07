public class OrderProcessor extends Thread {

    private static int processedOrders = 0;

    @Override
    public void run() {

        processOrder();
    }

    private static synchronized void processOrder() {

        processedOrders++;

        System.out.println(
                Thread.currentThread().getName() +
                " is processing an order."
        );

        try {

            Thread.sleep(1000);

        } catch (InterruptedException e) {

            System.out.println(
                    "Order processing interrupted."
            );
        }

        System.out.println(
                "Order processed successfully."
        );

        System.out.println(
                "Total processed orders: " +
                processedOrders
        );
    }
}