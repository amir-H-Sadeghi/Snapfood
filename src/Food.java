import java.util.ArrayList;

public class Food {
    Food(String name,int price,int foodID,int discount){setName(name);setPrice(price);setFoodID(foodID);setDiscount(discount);}
    Food(int FoodID){}
    public String name;
    public int price;
    public int foodID;
    public int discount;
    public ArrayList<String>ratings=new ArrayList<>();
    private void setName(String name){this.name = name;}

    private void setPrice(int price) {this.price = price;}
    private void setFoodID(int foodID){this.foodID=foodID;}
    private void setDiscount(int discount){
        this.discount=discount;
    }

    public String getName() {return name;}

    public int getPrice() {return price;}

    public int getFoodID() {return foodID;}

    public int getDiscount() {return discount;}
}
