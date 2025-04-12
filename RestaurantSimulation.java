package restaurantsimulering;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class RestaurantSimulation implements Runnable {

    private final VBox customerBox;
    private final VBox orderQueueBox;
    private final VBox chefBox;
    private final HBox statusBar;

    public RestaurantSimulation(VBox customerBox, VBox orderQueueBox, VBox chefBox, HBox statusBar) {
        this.customerBox = customerBox;
        this.orderQueueBox = orderQueueBox;
        this.chefBox = chefBox;
        this.statusBar = statusBar;
    }

    @Override
    public void run() {
        OrderQueue orderQueue = new OrderQueue();

        // Start 2 chef threads
        new Thread(new Chef("Chef 1", "Pizza", orderQueue, chefBox, statusBar, orderQueueBox)).start();
        new Thread(new Chef("Chef 2", "Burger", orderQueue, chefBox, statusBar, orderQueueBox)).start();

        // Start 5 customer threads
        for (int i = 1; i <= 5; i++) {
            String meal = (i % 2 == 0) ? "Pizza" : "Burger";
            new Thread(new Customer("Customer " + i, meal, orderQueue, customerBox, orderQueueBox)).start();
            try {
                Thread.sleep(500); // staggered arrival
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}