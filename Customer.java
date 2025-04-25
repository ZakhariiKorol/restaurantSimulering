package restaurantsimulering;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Customer implements Runnable {
    private final String name;
    private final String mealType;
    private final OrderQueue orderQueue;
    private final VBox customerBox;
    private final VBox orderQueueBox;

    public Customer(String name, String mealType, OrderQueue orderQueue, VBox customerBox, VBox orderQueueBox) {
        this.name = name;
        this.mealType = mealType;
        this.orderQueue = orderQueue;
        this.customerBox = customerBox;
        this.orderQueueBox = orderQueueBox;
    }

    @Override
    public void run() {
        // Create a label to represent this customer
        Label customerLabel = new Label(name + " wants " + mealType);
        Platform.runLater(() -> customerBox.getChildren().add(customerLabel));

        try {
            // Create and place the order
            Order order = new Order(mealType, 3000);
            Label orderLabel = new Label("📝 " + name + ": " + mealType);
            Platform.runLater(() -> orderQueueBox.getChildren().add(orderLabel));

            order.setVisual(orderLabel); // link visual to order object
            orderQueue.placeOrder(order);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
