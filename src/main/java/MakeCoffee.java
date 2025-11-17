
public class MakeCoffee {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Coffee order =new Sugar(new Cream( new ExtraShot(new BasicCoffee())));


        double costOne = order.makeCoffee();
        System.out.println("Total: $"+ String.format("%.2f", costOne));

    }
}

interface Coffee{
    public double makeCoffee();
}

class BasicCoffee implements Coffee{

    private double cost = 4.50;

    @Override
    public double makeCoffee() {
        // TODO Auto-generated method stub
        System.out.println("Black Coffee: $4.50");

        return cost;
    }
}

abstract class CoffeeDecorator implements Coffee{
    protected Coffee specialCoffee;

    public CoffeeDecorator(Coffee c) {

        this.specialCoffee = c;
    }

    public double makeCoffee() {

        return specialCoffee.makeCoffee();
    }
}

class ExtraShot extends CoffeeDecorator{

    private double cost = 1.20;

    ExtraShot(Coffee specialCoffee){
        super(specialCoffee);
    }

    public double makeCoffee() {
        return specialCoffee.makeCoffee() + addShot();
    }

    private double addShot() {
        System.out.println(" + extra shot: $1.20");

        return cost;
    }
}

class Cream extends CoffeeDecorator{

    private double cost = .50;
    Cream(Coffee specialCoffee){
        super(specialCoffee);
    }

    public double makeCoffee() {
        return specialCoffee.makeCoffee() + addCream();
    }

    private double addCream() {

        System.out.println(" + cream: $0.50");

        return cost;
    }
}

class Sugar extends CoffeeDecorator{

    private double cost = .50;

    Sugar(Coffee specialCoffee){
        super(specialCoffee);
    }

    public double makeCoffee() {
        return specialCoffee.makeCoffee()+ addSugar();
    }

    private double addSugar() {

        System.out.println(" + sugar: $0.50");

        return cost;
    }
}

class Caramel extends CoffeeDecorator{
    private double cost = 1.50;
    Caramel(Coffee specialCoffee){super(specialCoffee);}
    public double makeCoffee() {return specialCoffee.makeCoffee() + addCaramel();}

    private double addCaramel() {
        System.out.println(" + caramel: $1.50");
        return cost;
    }
}

class Cinnamon extends CoffeeDecorator{
    private double cost = 1.50;
    Cinnamon(Coffee specialCoffee){super(specialCoffee);}
    public double makeCoffee() {return specialCoffee.makeCoffee() + addCinnamon();}

    private double addCinnamon() {
        System.out.println(" + cinnamon: $0.50");
        return cost;
    }
}
