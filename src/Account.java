import java.util.ArrayList;

public abstract class Account {
    String userName;
    String password;
    int ID;
    abstract void setUserName(String userName);
    abstract void setPassword(String password);
    abstract void setID(int ID);

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public int getID() {
        return ID;
    }
}
class Admin extends Account{

    public int RestaurantNumbers=0;
    Admin(String userName,String password,int ID){setUserName(userName);setPassword(password);setID(ID);}
    @Override
    void setPassword(String password) {this.password = password;}

    @Override
    void setUserName(String userName) {this.userName = userName;}

    @Override
    void setID(int ID) {
        this.ID=ID;
    }
}

class User extends Account{

    User(String userName,String password,int ID,int Credit1,String Location1){setUserName(userName);setPassword(password);setID(ID);BuyingList=new ArrayList<>();Credit=Credit1;Location=Location1;}
    public ArrayList<Orders> BuyingList;
    public Orders[] BuyHistory;
    public int Credit;
    public String Location;

    @Override
    void setPassword(String password) {this.password = password;}

    @Override
    void setUserName(String userName) {this.userName = userName;}

    @Override
    void setID(int ID) {this.ID=ID;}
}
