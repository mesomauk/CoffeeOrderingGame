import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
    private Coffee order;
    private final List<String> ingredients;
    public OrderBuilder() {
        ingredients = new ArrayList<>();
        reset();
    }

    public void reset() {
        order = new BasicCoffee();
        ingredients.clear();
    }

    public void addSugar() {

      ingredients.add("Sugar");
      rebuildOrder();
    }
    public void addCream() {

       ingredients.add("Cream");
       rebuildOrder();
    }
    public void addExtraShot() {
       ingredients.add("Extra Shot");
       rebuildOrder();
    }
    public void addCaramel() {
        ingredients.add("Caramel");
        rebuildOrder();
    }
    public void addCinnamon() {
        ingredients.add("Cinnamon");
        rebuildOrder();
    }
    public void removeIngredient(String ing) {
        ingredients.remove(ing);
        rebuildOrder();
    }

    private void rebuildOrder() {
        order = new BasicCoffee();

        for (String ing : ingredients) {
            switch (ing) {
                case "Sugar": order = new Sugar(order); break;
                case "Cream": order = new Cream(order); break;
                case "Extra Shot": order = new ExtraShot(order); break;
                case "Caramel": order = new Caramel(order); break;
                case "Cinnamon": order = new Cinnamon(order); break;
            }
        }
    }


    public Coffee getOrder() {
        return order;
    }
    public List<String> getIngredients() {
        return ingredients;
    }
}
