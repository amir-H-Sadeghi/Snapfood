import java.util.ArrayList;
import java.util.TreeSet;

public class Restuarants {
    public String name;
    public String Location;
    public int ID;
    TreeSet<String> FoodType;
    int Foods =0;
    public Orders[] orders;
    public ArrayList<String> ratings = new ArrayList<>();
    public String getName(){return name;}
    public int getID(){return ID;}

    Restuarants(String name ,String location, int ID , TreeSet<String> FoodType){setName(name);Location = location;setID(ID);setFoodType(FoodType);}
    private void setName(String name){this.name=name;}
    private void setID(int ID){this.ID=ID;}
    private void setFoodType(TreeSet<String> FoodType){this.FoodType=FoodType;}

    public void setOrders(Orders[] orders) {
        this.orders = orders;
    }
}
