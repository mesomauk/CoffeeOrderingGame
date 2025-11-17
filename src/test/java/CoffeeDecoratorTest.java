import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class CoffeeDecoratorTest {

    private OrderBuilder builder;

    @BeforeEach
    void setup() {
        builder = new OrderBuilder();
    }


    @Test
    void BasicCoffeePriceTest() {
        Coffee c = builder.getOrder();
        assertEquals(4.50, c.makeCoffee(), 0.001);
    }

    @Test
    void AddSugarTest() {
        builder.addSugar();
        Coffee c = builder.getOrder();

        assertEquals(4.50 + .50, c.makeCoffee(), 0.001);
        assertTrue(builder.getIngredients().contains("Sugar"));
    }

    @Test
    void AddCreamTest() {
        builder.addCream();

        assertEquals(4.50 + .50, builder.getOrder().makeCoffee(), 0.001);
        assertTrue(builder.getIngredients().contains("Cream"));
    }

    @Test
    void MultipleIngredientsTest() {
        builder.addSugar();
        builder.addCream();
        builder.addExtraShot();

        double expected = 4.50 + .50 + .50 + 1.20;
        assertEquals(expected, builder.getOrder().makeCoffee(), 0.001);
        assertEquals(3, builder.getIngredients().size());
    }

    @Test
    void RemoveIngredientTest() {
        builder.addSugar();
        builder.addCream();

        builder.removeIngredient("Sugar");

        assertFalse(builder.getIngredients().contains("Sugar"));
        assertEquals(4.50 + .50, builder.getOrder().makeCoffee(), 0.001);
    }

    @Test
    void RemoveNonexistentIngredientTest() {
        builder.addSugar();
        builder.removeIngredient("Extra Shot"); // should not crash

        assertEquals(1, builder.getIngredients().size());
        assertEquals(4.50 + .50, builder.getOrder().makeCoffee(), 0.001);
    }

    @Test
    void ResetTest() {
        builder.addSugar();
        builder.addCream();
        builder.reset();

        assertEquals(0, builder.getIngredients().size());
        assertEquals(4.50, builder.getOrder().makeCoffee(), 0.001);
    }

    @Test
    void CaramelTest() {
        builder.addCaramel();

        assertEquals(4.50 + 1.50, builder.getOrder().makeCoffee(), 0.001);
        assertTrue(builder.getIngredients().contains("Caramel"));
    }

    @Test
    void CinnamonTest() {
        builder.addCinnamon();

        assertEquals(4.50 + 1.50, builder.getOrder().makeCoffee(), 0.001);
        assertTrue(builder.getIngredients().contains("Cinnamon"));
    }

    @Test
    void RebuildConsistencyTest() {
        builder.addSugar();
        builder.addCream();

        double before = builder.getOrder().makeCoffee();

        // Force a rebuild by using remove and add
        builder.removeIngredient("Cream");
        builder.addCream();

        double after = builder.getOrder().makeCoffee();

        assertEquals(before, after, 0.001);
    }
    @Test
    void RemoveIngredientReducesPrice() {
        OrderBuilder builder = new OrderBuilder();
        builder.addSugar();
        builder.addExtraShot();
        double with = builder.getOrder().makeCoffee();

        builder.removeIngredient("Extra Shot");
        double without = builder.getOrder().makeCoffee();

        assertTrue(with > without);
        assertEquals(4.50 + 0.50, without, 0.001);
    }

    @Test
    void RemoveNonExistingIngredientDoesNothing() {
        OrderBuilder builder = new OrderBuilder();
        builder.addSugar();

        builder.removeIngredient("Caramel");  // Not in list
        double total = builder.getOrder().makeCoffee();

        assertEquals(5.0, total, 0.001);
    }

    @Test
    void IngredientOrderDoesNotMatter() {
        OrderBuilder b1 = new OrderBuilder();
        b1.addCream();
        b1.addSugar();

        OrderBuilder b2 = new OrderBuilder();
        b2.addSugar();
        b2.addCream();

        assertEquals(b1.getOrder().makeCoffee(), b2.getOrder().makeCoffee(), 0.001);
    }
    @Test
    void MultipleSameIngredient() {
        OrderBuilder builder = new OrderBuilder();
        builder.addSugar();
        builder.addSugar(); // 2 sugars

        double total = builder.getOrder().makeCoffee();
        assertEquals(4.50 + 0.50 + 0.50, total, 0.001);
    }

    @Test
    void ResetClearsOrder() {
        OrderBuilder builder = new OrderBuilder();
        builder.addCaramel();
        builder.addCinnamon();
        builder.reset();

        assertEquals(4.50, builder.getOrder().makeCoffee(), 0.001);
        assertTrue(builder.getIngredients().isEmpty());
    }

    @Test
    void ExtraShotPriceCorrect() {
        OrderBuilder builder = new OrderBuilder();
        builder.addExtraShot();

        assertEquals(4.50 + 1.20, builder.getOrder().makeCoffee(), 0.001);
    }

    @Test
    void ThreeIngredientComboTest() {
        OrderBuilder builder = new OrderBuilder();
        builder.addCaramel();
        builder.addCinnamon();
        builder.addSugar();

        double total = builder.getOrder().makeCoffee();

        assertEquals(4.50 + 1.50 + 1.50 + 0.50, total, 0.001);
    }

    @Test
    void RemoveOneOfMultiple() {
        OrderBuilder builder = new OrderBuilder();
        builder.addCream();
        builder.addCream(); // twice
        builder.removeIngredient("Cream");

        double total = builder.getOrder().makeCoffee();

        assertEquals(4.50 + 0.50, total, 0.001);  // one cream remains
    }

    @Test
    void DecoratorPrintsCorrectOutput() {
        Coffee drink = new Sugar(new ExtraShot(new BasicCoffee()));

        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        drink.makeCoffee();

        String output = out.toString();

        assertTrue(output.contains("Black Coffee: $4.50"));
        assertTrue(output.contains("extra shot"));
        assertTrue(output.contains("sugar"));
    }

    @Test
    void BuildCoffeeMatchesOrderBuilder() {
        OrderBuilder builder = new OrderBuilder();
        builder.addSugar();
        builder.addExtraShot();

        Coffee order1 = builder.getOrder();
        double builderTotal = order1.makeCoffee();

        Coffee manual = new Sugar(new ExtraShot(new BasicCoffee()));
        double manualTotal = manual.makeCoffee();

        assertEquals(manualTotal, builderTotal, 0.001);
    }


}
