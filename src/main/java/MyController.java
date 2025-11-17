

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MyController implements Initializable {
    private OrderBuilder build =  new OrderBuilder();
    private final List<Coffee> drinksInOrder = new ArrayList<>();
    private final List<String> drinkSummaries = new ArrayList<>();

    @FXML
    private ListView<String> orderList;

    @FXML
    private TextArea outputArea;
    private final List<String> order = new ArrayList<>(); // current drink items


    @FXML
    private void handleSugar() {
        build.addSugar();
        refreshDisplay();
    }


    @FXML
    private void handleCream() {
        build.addCream();
        addItem("Cream");

    }

    @FXML
    private void handleExtraShot() {
        build.addExtraShot();
        addItem("Extra Shot");

    }

    @FXML
    private void handleCaramel() {
        build.addCaramel();
        addItem("Caramel");

    }


    @FXML
    private void handleCinnamon() {
        build.addCinnamon();
        addItem("Cinnamon");

    }

    @FXML
    private void handleRemoveSelected() {
        String selected = orderList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            build.removeIngredient(selected);
            refreshDisplay();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }

   @FXML
   private void handleNewOrder() {
       build.reset();
       order.clear();
       refreshDisplay();
       orderList.getItems().clear();
   }
   @FXML
   private void handleAddDrink() {
        drinksInOrder.add(build.getOrder());
       StringBuilder summary = new StringBuilder();
       for(String s: order){
           summary.append("- ").append(s).append("\n");
       }
       drinkSummaries.add(summary.toString());
       order.clear();
       orderList.getItems().clear();
       build.reset();

       outputArea.appendText(" Drink added to order!\n");


   }
   @FXML
   private void handleComplete() {
       outputArea.clear();

        Coffee finalOrder = buildCoffee();

       PrintStream ogOut = System.out;
       ByteArrayOutputStream baOut = new ByteArrayOutputStream();
       PrintStream ps = new PrintStream(baOut);
       System.setOut(ps);

       int drinkCount = 1;
       double total = 0;

       for (int i = 0; i < drinksInOrder.size(); i++) {
           Coffee drink = drinksInOrder.get(i);
           outputArea.appendText("Drink #" + drinkCount + ":\n");
           outputArea.appendText(drinkSummaries.get(i));
           double amt = drink.makeCoffee();
           total += amt;
           outputArea.appendText(baOut.toString());
           baOut.reset();
           outputArea.appendText("Drink total: $" + String.format("%.2f", amt) + "\n\n");
           drinkCount++;
       }

       System.setOut(ogOut);

       outputArea.appendText("Grand Total $" + String.format("%.2f", total));

       drinksInOrder.clear();
       order.clear();
       orderList.getItems().clear();
       build.reset();

   }

   private void addItem(String item) {
       System.out.println("Adding: " + item);
       order.add(item);
       orderList.getItems().add(item);
        refreshDisplay();
   }
    private void removeItem(String item) {
        order.remove(item);
        orderList.getItems().remove(item);
        refreshDisplay();
    }

    private void refreshDisplay() {
        orderList.getItems().setAll(build.getIngredients());

    }

    private Coffee buildCoffee() {
       Coffee c = new BasicCoffee();
       for (String item : order) {
           switch (item) {
               case "Sugar":
                   c = new Sugar(c);
                   break;
               case "Cream":
                   c = new Cream(c);
                   break;
               case "Extra Shot":
                   c = new ExtraShot(c);
                   break;
               case "Caramel":
                   c = new Caramel(c);
                   break;
               case "Cinnamon":
                   c = new Cinnamon(c);
                   break;
           }
       }
       return c;
    }


}
