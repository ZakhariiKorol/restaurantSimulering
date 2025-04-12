package restaurantsimulering;

import javafx.scene.control.Label;

public class Order {
    private final String mealType;
    private final int prepTime;
    private Label visual;

    public Order(String mealType, int prepTime) {
        this.mealType = mealType;
        this.prepTime = prepTime;
    }

    public String getMealType() {
        return mealType;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setVisual(Label label) {
        this.visual = label;
    }

    public Label getVisual() {
        return visual;
    }
}