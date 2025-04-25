package restaurantsimulering;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Layout containers
        VBox customerBox = new VBox(10);
        VBox orderQueueBox = new VBox(10);
        VBox cookBox = new VBox(10);
        HBox statusBar = new HBox(10);

        // Styling and layout
        customerBox.setPadding(new Insets(10));
        orderQueueBox.setPadding(new Insets(10));
        cookBox.setPadding(new Insets(10));
        statusBar.setPadding(new Insets(10));

        customerBox.setStyle("-fx-background-color: #F8F8DC; -fx-pref-width: 150;");
        orderQueueBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-pref-width: 150;");
        cookBox.setStyle("-fx-background-color: #ADD8E6; -fx-pref-width: 150;");
        statusBar.setStyle("-fx-background-color: #90EE90;");

        // Section titles
        // Symbols are made by AI
        customerBox.getChildren().add(new Label("👤 Customers"));
        orderQueueBox.getChildren().add(new Label("📦 Order Queue"));
        cookBox.getChildren().add(new Label("👨‍🍳 Chefs"));
        statusBar.getChildren().add(new Label("✅ Served Food:"));
        
        // Buttons for Object Serialization
        Button saveButton = new Button("Save");
        Button loadButton = new Button("Load");
        
        // Add Buttons to statusBar
        statusBar.getChildren().addAll(saveButton, loadButton);
        
        // Buttons Logic
        saveButton.setOnAction(e -> {
            // Extract orders from orderQueueBox (skip header)
            var orders = orderQueueBox.getChildren().stream()
                    .skip(1)
                    .map(node -> {
                        Label label = (Label) node;
                        String text = label.getText();
                        if (text.contains(":")) {
                            String meal = text.split(":")[1].trim();
                            return new Order(meal, 3000); // use default prepTime
                        }
                        return null;
                    })
                    .filter(o -> o != null)
                    .toList();
        
        // Extract customer names
        var customers = customerBox.getChildren().stream()
                .skip(1)
                .map(node -> ((Label) node).getText().replace("👤 ", ""))
                .toList();
        // Extract chef names
        var chefs = cookBox.getChildren().stream()
                .skip(1)
                .map(node -> ((Label) node).getText().split(" ")[1]) // Chef 1 cooking..." -> "Chef 1"
                .toList();
        
        // Create and save the state
        SimulationState state = new SimulationState(orders, customers, chefs);
        StateSaver.saveState(state, "restaurant_save.dat");
        });
        
        loadButton.setOnAction(e -> { 
            SimulationState loaded = StateLoader.loadState("restaurant_save.dat");
            if (loaded != null) {
                System.out.println("Loaded orders: " + loaded.orderQueue.size());
                System.out.println("Loaded customers: " + loaded.customers);
            }
        });
        
        Label happyLabel = new Label("😊 Happy: 0");
        Label angryLabel = new Label("😠 Angry: 0");
        statusBar.getChildren().addAll(happyLabel, angryLabel);
        
        // Main horizontal layout
        HBox mainLayout = new HBox(10, customerBox, orderQueueBox, cookBox);
        VBox root = new VBox(10, mainLayout, statusBar);
        root.setPadding(new Insets(10));

        // Set up the scene
        Scene scene = new Scene(root, 850, 400);
        primaryStage.setTitle("Restaurant Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Run the simulation with dynamic UI updates
        new Thread(new RestaurantSimulation(customerBox, orderQueueBox, cookBox, statusBar)).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
