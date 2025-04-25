package restaurantsimulering;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class Chef implements Runnable {
    private final String name;
    private final String specialty;
    private final OrderQueue orderQueue;
    private final VBox chefBox;
    private final HBox statusBar;
    private final VBox orderQueueBox;

    public Chef(String name, String specialty, OrderQueue orderQueue, VBox chefBox, HBox statusBar, VBox orderQueueBox) {
        this.name = name;
        this.specialty = specialty;
        this.orderQueue = orderQueue;
        this.chefBox = chefBox;
        this.statusBar = statusBar;
        this.orderQueueBox = orderQueueBox;
    }

    @Override
    public void run() {
        Label chefStatus = new Label("👨‍🍳 " + name + " is idle");
        Platform.runLater(() -> chefBox.getChildren().add(chefStatus));

        while (true) {
            try {
                Order order = orderQueue.takeOrder();

                // Update chef status
                Platform.runLater(() -> chefStatus.setText("👨‍🍳 " + name + " cooking " + order.getMealType()));

                // Simulate cooking time
                Thread.sleep(order.getPrepTime());

                // Remove order from order queue 
                Platform.runLater(() -> {
                    if (order.getVisual() != null) {
                        orderQueueBox.getChildren().remove(order.getVisual());
                    }
                });

                // Update chef status again
                Platform.runLater(() -> chefStatus.setText("👨‍🍳 " + name + " is idle"));

                // Add to served area
                Label served = new Label("✅ " + order.getMealType() + " ready!");
                Platform.runLater(() -> statusBar.getChildren().add(served));
                
                
                Thread.sleep(1000); // 1 second break before taking order

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
