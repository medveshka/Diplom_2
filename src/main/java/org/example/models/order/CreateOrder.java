package org.example.models.order;
import java.util.List;

public class CreateOrder {
    public final List<String> ingredients;

    public CreateOrder(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}

