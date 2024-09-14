import java.util.*;
public class Main {

    public static int AdminIdCounter=0;
    public static int UserIdCounter=0;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SQLiteJDBC.createNewTable();
        interiorMenu(scanner);
    }

    

    // VERIFICATION

    public static void interiorMenu(Scanner scanner){
        System.out.println("///// Hello buddy  ////");
        System.out.println("  Good to see you again  ");
        System.out.println("  To continue Enter your command with the given number");
        System.out.println("Tip: You can end the program whenever you want with entering the number -99");
        System.out.println("  So what can I do for you? ");
        System.out.println("1- Sign in ");
        System.out.println("2- Sign up ");
        int command;
        do{
            command=Command(scanner,2);
            switch (command){
                case 1:
                    ChooseRole(scanner,true);
                    break;
                case 2:
                    ChooseRole(scanner,false);
                    break;
                case 0:
                    break;
            }
        }while (command!=0);

    }
    public static void ChooseRole(Scanner scanner,boolean identifier){
        System.out.println("Choose your role");
        System.out.println("1-Admin");
        System.out.println("2-User");
        System.out.println("0-Back");
        int command;
        do {
            command=Command(scanner,2);
            switch (command){
                case 1:
                    if (identifier)
                        AdminSignInMenu(scanner);
                    else
                        AdminRegister(scanner);
                    break;
                case 2:
                    if (identifier)
                        UserSignInMenu(scanner);
                    else
                        UserRegister(scanner);
                    break;
                case 0:
                    interiorMenu(scanner);
                    break;
            }
        }while (true);
    }
    public static void AdminSignInMenu(Scanner scanner){
        System.out.println("0-Back");

        do {
            boolean Search =true;
            System.out.println("Enter your username");
            String UserName = scanner.nextLine();
            try{
                int search = Integer.parseInt(UserName);
                if(search==-99)
                    System.exit(0);
                else if(search==0)
                    ChooseRole(scanner,true);
            }catch (Exception e){}
            if(SQLiteJDBC.search(UserName,"PASSWORD",1)==null){
                System.out.println("There is no Account with the given username");
                Search=false;
            }
            if(Search){
                do {
                    System.out.println("Enter your password");
                    String Password = scanner.nextLine();
                    if(SQLiteJDBC.search(UserName,"PASSWORD",1).equals(Password)){
                        Admin A = new Admin(UserName,Password,Integer.parseInt(SQLiteJDBC.search(UserName,"id",1)));
                        AdminRestaurantMenu(scanner, A);
                    }
                    else
                        System.out.println("Wrong password");
                }while (true);
            }
        }while (true);

    }
    public static void UserSignInMenu(Scanner scanner){

        System.out.println("0-Back");
        do {
            boolean Search=true;
            System.out.println("Enter your username");
            String UserName = scanner.nextLine();
            try{
                int search = Integer.parseInt(UserName);
                if(search==0)
                    ChooseRole(scanner,true);
                if(search==-99)
                    System.exit(0);
            }catch (Exception e){}
            if(SQLiteJDBC.search(UserName,"PASSWORD",2)==null){
                System.out.println("There is no Account with the given username");
                Search=false;
            }
            if(Search) {
                do {
                    System.out.println("Enter your password");
                    String Password = scanner.nextLine();
                    if(SQLiteJDBC.search(UserName,"PASSWORD",2).equals(Password)){
                        int id = Integer.parseInt(SQLiteJDBC.search(UserName,"id",2));
                        int credit =Integer.parseInt(SQLiteJDBC.search(UserName,"credit",2));
                        String location = SQLiteJDBC.search(UserName,"Location",2);
                        User A = new User(UserName,Password,id,credit,location);//SQL LOCATION
                        UserRestaurantMenu(scanner, A);
                    }
                    else
                        System.out.println("Wrong password");
                }while (true);
            }
        }while (true);

    }
    public static void AdminRegister(Scanner scanner){
        do {
            System.out.println("0-Back");
            System.out.println("Enter your username");
            String UserName = scanner.nextLine();
            try {
                int notImportant = Integer.parseInt(UserName);
                if(notImportant==0)
                    ChooseRole(scanner,false);
                else if(notImportant==-99)
                    System.exit(0);
            }catch (Exception e){}
            System.out.println("Enter your password");
            String Password = scanner.nextLine();
            if(!(SQLiteJDBC.search(UserName,"PASSWORD",1)==null))
                System.out.println("An account with the given name already exists");
            else{
                AdminIdCounter++;
                Admin A = new Admin(UserName, Password, AdminIdCounter);
                System.out.println("Account registered successfully");
                SQLiteJDBC.insertSignUp(UserName, Password, "",1);
                AdminRestaurantMenu(scanner, A);
            }

        }while (true);
    }
    public static void UserRegister(Scanner scanner){
        do {
            System.out.println("0-Back");
            System.out.println("Enter your username");
            String UserName = scanner.nextLine();
            try {
                int notImportant = Integer.parseInt(UserName);
                if(notImportant==0)
                    ChooseRole(scanner,false);
                else if(notImportant==-99)
                    System.exit(0);
            }catch (Exception e){}
            System.out.println("Enter your password");
            String Password = scanner.nextLine();
            System.out.println("Enter your location");
            boolean condition = true;
            String Location;
            do {
                Location = scanner.nextLine();
                try {
                    if(Integer.parseInt(Location)>0&&Integer.parseInt(Location)<1000)
                        condition=false;
                    else
                        System.out.println("Entered number must be between 0 and 1000");
                }catch (Exception e){
                    System.out.println("Please enter a number");
                }
            }while (condition);
            if(!(SQLiteJDBC.search(UserName,"PASSWORD",2)==null))
                System.out.println("An account with the given name already exists");
            else{
                UserIdCounter++;
                User A = new User(UserName, Password, UserIdCounter,0,Location);
                SQLiteJDBC.insertSignUp(UserName, Password, Location,2);
                System.out.println("Account registered successfully");
                UserRestaurantMenu(scanner, A);
            }
        }while (true);
    }

    //USER MENU

    public static void UserRestaurantMenu(Scanner scanner,User user) {
        System.out.println("Welcome to your account user "+user.userName);
        System.out.println("Credit: " + user.Credit);
        System.out.println("What do you want to do?");
        System.out.println("0- Logout");
        System.out.println("1- Restaurant list");
        System.out.println("2- Search restaurant");
        System.out.println("3- Charge credit");
        System.out.println("4- Buying History");

        int command;
        do {
            command=Command(scanner,4);
            switch (command){
                case 0:
                    user=null;
                    System.gc();
                    interiorMenu(scanner);
                    break;
                case 1:
                    UserRestaurantList(scanner,user);
                    break;
                case 2:
                    SearchRestaurant(scanner,user);
                    break;
                case 3:
                    System.out.println("How much you want to charge your credit?");
                    boolean condition = true;
                    do {
                        String command1 = scanner.nextLine();
                        try {
                            int credit = Integer.parseInt(command1);
                            if(credit==-99)
                                System.exit(0);
                            else if(credit<0){
                                System.out.println("Please enter a positive number");
                            }
                            else {
                                user.Credit+=credit;
                                SQLiteJDBC.editUserCredit(user.userName,String.valueOf(user.Credit));
                            }
                            condition=false;
                        }catch (Exception e){
                            System.out.println("Please enter numbers.");
                        }
                    }while (condition);
                    System.out.println("Your credit charged to "+user.Credit);
                    UserRestaurantMenu(scanner,user);
                    break;
                case 4:
                    UserHistory(scanner,user);
                    break;
            }

        }while (true);
    } //Check
    public static void UserHistory(Scanner scanner,User user){
        ArrayList<String> OrderList = new ArrayList<String>();
        OrderList = SQLiteJDBC.OrderUser(user.userName, "id");
        if(OrderList.size()==0)
            System.out.println("You had no order yet.");
        else {
            for (int i = 0 ; i < OrderList.size() ; i++){
                String ofood = SQLiteJDBC.searchOrder(OrderList.get(i),"Food");
                String Ores = SQLiteJDBC.searchOrder(OrderList.get(i),"Rname");
                String oprice = SQLiteJDBC.searchOrder(OrderList.get(i),"Price");
                System.out.printf("%d- %s with price %d from restaurant %s\n",i+1,ofood,Integer.parseInt(oprice),Ores);
            }

        }
        System.out.println("Press 0 to continue");
        do {
            int com =Command(scanner,0);
            UserRestaurantMenu(scanner,user);
        }while (true);
    } //مشکل داره
    public static void UserRestaurantList(Scanner scanner,User user){
        ArrayList<String> restuarantsArrayList = new ArrayList<String>();
        restuarantsArrayList=SQLiteJDBC.RestuarantListUser("RName");
        Collections.sort(restuarantsArrayList, String.CASE_INSENSITIVE_ORDER);
        System.out.println(Arrays.deepToString(new ArrayList[]{restuarantsArrayList}));
        if(restuarantsArrayList.size()==0){
            System.out.println("No restaurant has been registered yet.");
            System.out.println("Insert 0 to continue.");
        }
        else{
            System.out.println("Choose restaurant");
            for(int i = 0 ; i < restuarantsArrayList.size() ; i++){
                System.out.printf("%d- Restaurant %s\n",i+1,restuarantsArrayList.get(i));
            }
            System.out.println("Insert 0 to go last page");
        }
        int command;
        do {
            command=Command(scanner,restuarantsArrayList.size());
            if(command==0)
                UserRestaurantMenu(scanner,user);
            else if(command > 0) {
                String Rname = restuarantsArrayList.get(command - 1);
                String id = SQLiteJDBC.ResObject(Rname, "Rnumber");
                String FT = SQLiteJDBC.ResObject(Rname, "FoodType");
                TreeSet<String> ft = convertFoodType(FT);
                String Location = SQLiteJDBC.ResObject(Rname,"Location");
                Restuarants res = new Restuarants(Rname, Location ,Integer.parseInt(id), ft);
                UserRestaurantPage(scanner, user, res);
            }
        }while (true);

    } //Check
    public static void SearchRestaurant(Scanner scanner,User user){
        System.out.println("Please enter the name of the restaurant");
        System.out.println("0- Back");
        do {
            String command = scanner.nextLine();
            try{
                int com = Integer.parseInt(command);
                if(com == 0)
                    UserRestaurantMenu(scanner,user);
                if(com == -99){
                    System.exit(0);
                }
            }catch (Exception e){}
            Restuarants restuarants = null;
            if (SQLiteJDBC.searchRes(command)) {
                System.out.println("There is no restaurant with the given name.Try again");
            } else {
                String Rname = command;
                String id= SQLiteJDBC.ResObject(Rname,"id");
                String FT= SQLiteJDBC.ResObject(Rname,"FoodType");
                TreeSet<String> ft = convertFoodType(FT);
                String Location = SQLiteJDBC.ResObject(Rname,"Location");
                Restuarants res = new Restuarants(Rname,Location,Integer.parseInt(id),ft);
                restuarants=res;
                UserRestaurantPage(scanner, user, restuarants);
            }
        }while (true);
    } //Check
    public static void UserRestaurantPage(Scanner scanner,User user,Restuarants restuarants){
        System.out.println("Restaurant: " + restuarants.name);
        System.out.println("ID: "+restuarants.ID);
        System.out.println("Food type: "+restuarants.FoodType.toString());
        System.out.println("Location: "+restuarants.Location);
        System.out.println("1- Show Food list");
        System.out.println("2- Search food");
        System.out.println("3- Add comment");
        System.out.println("4- Show comment");
        System.out.println("5- Rating");
        System.out.println("0- Back");
        int command;
        do {
            command=Command(scanner,5);
            switch (command){
                case 1:
                    FoodList(scanner,user,restuarants);
                    break;
                case 2:
                    SearchFood(scanner,user,restuarants);
                    break;
                case 3:
                    AddComment(scanner,user,restuarants,null);
                    break;
                case 4:
                    ShowComment(scanner,user,null,null,restuarants);
                    break;
                case 5:
                    Rating(scanner,null,user,restuarants,null);
                    break;
                case 0:
                    restuarants=null;
                    System.gc();
                    UserRestaurantMenu(scanner,user);
            }
        }while (true);
    } //Check
    public static void FoodList(Scanner scanner , User user , Restuarants restuarants){
        ArrayList<String> FoodList = new ArrayList<String>();
        FoodList = SQLiteJDBC.FoodList(restuarants.name, "Fname");
        Collections.sort(FoodList, String.CASE_INSENSITIVE_ORDER);

        if(FoodList.size()==0){
            System.out.println("No food had been registered yet");
            System.out.println("Enter 0 to continue");
        }
        else {
            for (int i = 0; i < FoodList.size(); i++) {
                System.out.printf("%d- %s\n", i + 1,FoodList.get(i) );
            }
            System.out.println("Select food");
        }
        int command;
        do {
            command = Command(scanner ,FoodList.size());
            if(command==0){
                UserRestaurantPage(scanner, user, restuarants);
            }
            else if(command!=-10){
                String fname = FoodList.get(command-1);
                String price = SQLiteJDBC.searchFood(fname,"Price");
                String diccount = SQLiteJDBC.searchFood(fname,"Discounte");
                String idf = SQLiteJDBC.searchFood(fname,"id");
                Food food = new Food(fname,Integer.parseInt(price),Integer.parseInt(idf),Integer.parseInt(diccount));
                FoodPage(scanner,user,restuarants,food);
            }
        }while (true);
    } //Check
    public static void SearchFood(Scanner scanner,User user,Restuarants restuarants){
        System.out.println("Enter name of the food");
        System.out.println("0- Back");
        do {
            String command = scanner.nextLine();
            try{
                int com = Integer.parseInt(command);
                if(com == -99)
                    System.exit(0);
                else if(com == 0){
                    UserRestaurantPage(scanner,user,restuarants);
                }
            }catch (Exception e){}
            Food food = null ;
            if(SQLiteJDBC.searchFood(command,"Rname")==null)
                System.out.println("There is no food with the given name.Try again");
            else {
                String fname = command;
                String price = SQLiteJDBC.searchFood(fname,"Price");
                String diccount = SQLiteJDBC.searchFood(fname,"Discounte");
                String idf = SQLiteJDBC.searchFood(fname,"id");
                food = new Food(fname,Integer.parseInt(price),Integer.parseInt(idf),Integer.parseInt(diccount));
                FoodPage(scanner,user , restuarants , food);
            }
        }while (true);
    } //Check
    public static void FoodPage(Scanner scanner , User user,Restuarants restuarants , Food food){
        System.out.println("Food " + food.name);
        System.out.println("Price: "+food.price);
        System.out.println("Discount: "+ food.discount + "%");
        System.out.println("1- Buy");
        System.out.println("2- Add comment");
        System.out.println("3- Show comment");
        System.out.println("4- Rating");
        System.out.println("0- Back");
        int command;
        do {
            command = Command(scanner,2);
            switch (command){
                case 1:
                    if(food.price-food.price*food.discount/100 > user.Credit){
                        System.out.println("Not enough credit. Please charge your account.");
                    }
                    else {
                        user.Credit -= (food.price-(food.price*food.discount/100));
                        SQLiteJDBC.editUserCredit(user.userName,String.valueOf(user.Credit));
                        Orders order = new Orders(restuarants,food,user);
                        user.BuyingList.add(order);
                        SQLiteJDBC.insertOrder(restuarants.name,user.userName,food.name,String.valueOf(food.price));
                        System.out.println("Food ordered successfully");
                    }
                    break;
                case 2:
                    AddComment(scanner,user,restuarants,food);
                    break;
                case 3:
                    ShowComment(scanner,user,null,food,restuarants);
                    break;
                case 4:
                    Rating(scanner,null,user,restuarants,food);
                    break;
                case 0:
                    food=null;
                    System.gc();
                    UserRestaurantPage(scanner,user,restuarants);
                    break;
            }
        }while (true);
    } //Check
    public static void AddComment(Scanner scanner , User user , Restuarants restuarants , Food food){
        System.out.println("Write your comment");
        String comment = scanner.nextLine();
        if(food==null){
            try {
                if(Integer.parseInt(comment)==0){
                    UserRestaurantPage(scanner,user,restuarants);
                }
                else if(Integer.parseInt(comment)==-99)
                    System.exit(0);
            }catch (Exception e){}
            Comment comment1 = new Comment(user,comment,restuarants);
            SQLiteJDBC.insertComment(comment,restuarants.name,user.userName,"0");
            System.out.println("Comment added");
            UserRestaurantPage(scanner,user,restuarants);
        }
        else{
            try {
                if(Integer.parseInt(comment)==0){
                    FoodPage(scanner, user, restuarants, food);
                }
                else if(Integer.parseInt(comment)==-99)
                    System.exit(0);
            }catch (Exception e){}
            Comment comment1=new Comment(user,comment,restuarants,food);
            SQLiteJDBC.insertComment(comment,restuarants.name,user.userName,food.name);
            System.out.println("Comment added.");
            FoodPage(scanner, user, restuarants, food);
        }
    } //Check



    //ADMIN MENU


    public static void AdminRestaurantMenu(Scanner scanner,Admin admin){
        System.out.println("Welcome to your account admin "+admin.userName);
        System.out.println("What do you want to do?");
        System.out.println("1- Add restaurant");
        System.out.println("2- Show my restaurant list");
        System.out.println("0- Logout");
        int command;
        do {
            command=Command(scanner,2);
            switch (command){
                case 1:
                    restaurantRegister(scanner,admin);
                    break;
                case 2:
                    restaurantList(scanner,admin);
                    break;
                case 0:
                    admin=null;
                    System.gc();
                    interiorMenu(scanner);
                    break;
            }
        }while (true);
    } //Check
    public static void restaurantRegister(Scanner scanner,Admin admin){
        TreeSet<String> FoodType = new TreeSet<>();
        do{
            System.out.println("0-Back");
            System.out.println("Enter your restaurant name");
            String RestaurantName = scanner.nextLine();
            try {
                int nothing = Integer.parseInt(RestaurantName);
                if(nothing==0)
                    AdminRestaurantMenu(scanner,admin);
                else if(nothing==-99)
                    System.exit(0);
            }catch (Exception e){}
            boolean condition3 =true,codition2=true;
            System.out.println("Please enter the location");
            String Location ;
            do {
                Location = scanner.nextLine();
                try {
                    if(Integer.parseInt(Location)>0&&Integer.parseInt(Location)<=1000){
                        codition2=false;
                    }
                    else
                        System.out.println("Entered number must be between 0 and 1000");
                }catch (Exception e){
                    System.out.println("Please enter a number");
                }
            }while (codition2);
            int commmand1;
            String foodtype = " ";
            do{
                System.out.println("Choose food type");
                System.out.println("1- FastFood");
                System.out.println("2- Iranian");
                System.out.println("3- Kebab");
                System.out.println("4- Salad");
                System.out.println("5- International");
                System.out.println("0- end");
                commmand1=Command(scanner,5);
                switch (commmand1){
                    case 1:
                        FoodType.add("FastFood");
                        foodtype +="1";
                        break;
                    case 2:
                        FoodType.add("Iranian");
                        foodtype +="2";
                        break;
                    case 3:
                        FoodType.add("Kebab");
                        foodtype+="3";
                        break;
                    case 4:
                        FoodType.add("Salad");
                        foodtype+="4";
                    case 5:
                        FoodType.add("International");
                        foodtype+="5";
                    case 0:
                        condition3=false;
                }
            }while (condition3);
            admin.RestaurantNumbers++;
            Restuarants restuarants = new Restuarants(RestaurantName,Location, admin.RestaurantNumbers,FoodType);
            SQLiteJDBC.insertRestuarant(RestaurantName,admin.userName,Location,String.valueOf(admin.RestaurantNumbers),foodtype);//SQL LOCATION
            System.out.println("Restaurant has been added.");
            AdminRestaurantMenu(scanner,admin);
        }while(true);

    } //Check
    public static void restaurantList(Scanner scanner,Admin admin){
        ArrayList<String> restuarantsArrayList = new ArrayList<String>();
        restuarantsArrayList=SQLiteJDBC.RestuarantList(admin.userName,"RName");
        Collections.sort(restuarantsArrayList, String.CASE_INSENSITIVE_ORDER);
        for (int i=0;i< restuarantsArrayList.size() ; i++){
            System.out.println(i+1+"-"+restuarantsArrayList.get(i));
        }
        if(restuarantsArrayList.size()==0) {
            System.out.println("You have no restaurant yet.");
            System.out.println("insert 0 to continue.");
        }
        else
            System.out.println("Choose your restaurant");
        int command;
        do {
            command =Command(scanner,restuarantsArrayList.size());
            if(command==0)
                AdminRestaurantMenu(scanner,admin);
            if (command==-99)
                System.exit(0);
            else {
                System.gc();
                String Rname = restuarantsArrayList.get(command-1);
                String id= SQLiteJDBC.ResObject(Rname,"Rnumber");
                String FT= SQLiteJDBC.ResObject(Rname,"FoodType");
                TreeSet<String> ft = convertFoodType(FT);
                String Location = SQLiteJDBC.ResObject(Rname,"Location");
                Restuarants res = new Restuarants(Rname,Location,Integer.parseInt(id),ft);
                restaurantPage(scanner, admin,res );
                restuarantsArrayList = null;

            }
        }while (true);
    } //Check

    private static TreeSet<String> convertFoodType(String ft) {
        TreeSet<String> temp =new TreeSet<String>();
        if(ft.contains("1")){
            temp.add("FastFood");
        } else if (ft.contains("2")) {
            temp.add("Iranian");
        }else if (ft.contains("3")){
            temp.add("Kebab");
        } else if (ft.contains("4")) {
            temp.add("Salad");
        } else if (ft.contains("5")) {
            temp.add("International");
        }
        return temp;
    }

    public static void restaurantPage(Scanner scanner, Admin admin , Restuarants restaurants){
        System.out.println("Restaurant "+ restaurants.name);
        System.out.println("Location: " + restaurants.Location);
        System.out.println(restaurants.FoodType.toString());
        System.out.println();
        System.out.println("1- Edit restaurant");
        System.out.println("2- Add food");
        System.out.println("3- Food list");
        System.out.println("4- Remove restaurant");
        System.out.println("5- Orders");
        System.out.println("6- Show comments");
        System.out.println("7- Ratings");
        System.out.println("0-Back");
        int command;
        do {
            command = Command(scanner,7);
            switch (command){
                case 0:
                    restaurants=null;
                    System.gc();
                    AdminRestaurantMenu(scanner,admin);
                    break;
                case 1:
                    EditRestaurant(admin,restaurants,scanner);
                    break;
                case 2:
                    AddFood(admin,restaurants,scanner);
                    break;
                case 3:
                    AdminFoodList(admin,restaurants,scanner);
                    break;
                case 4:
                    SQLiteJDBC.delRes(restaurants.name);
                    System.out.println(restaurants.name +" Have been deleted successfully");
                    restaurants=null;
                    System.gc();
                    AdminRestaurantMenu(scanner,admin);
                    break;
                case 5:
                    RestaurantOrderPage(scanner,admin,restaurants);
                    break;
                case 6:
                    ShowComment(scanner,null,admin,null,restaurants);
                    break;
                case 7:
                    Rating(scanner,admin,null,restaurants,null);
                    break;
            }
        }while (true);
    } //Check
    public static void EditRestaurant(Admin admin , Restuarants restuarants , Scanner scanner){
        System.out.println("Which one you want to change?");
        System.out.println("1- Name");
        System.out.println("2- location");
        System.out.println("3- Food type");
        int command;
        do {
            command = Command(scanner , 3);
            switch (command){
                case 1:
                    boolean condition = true;
                    do {
                        System.out.println("Enter the new name");
                        String newName = scanner.nextLine();
                        SQLiteJDBC.editResName(restuarants.name,newName);
                        restuarants.name = newName;
                        condition=false;
                    }while(condition);
                    System.out.println("Name changed successfully");
                    restaurantPage(scanner,admin,restuarants);
                    break;
                case 2:
                    boolean condition1=true;
                    System.out.println("Please enter the location");
                    String Location1;
                    do {
                        Location1= scanner.nextLine();
                        try {
                            if (Integer.parseInt(Location1)>0&&Integer.parseInt(Location1)<=1000)
                                condition1=false;
                            else
                                System.out.println("Entered number must be between 0 and 1000");
                        }catch (Exception e){
                            System.out.println("Please enter a number");
                        }
                    }while (condition1);
                    restuarants.Location=Location1;
                    SQLiteJDBC.editResLocation(restuarants.name,Location1);
                    System.out.println("Location changed successfully.");
                    restaurantPage(scanner,admin,restuarants);
                    break;
                case 3:
                    System.out.println("Existing Food types:");
                    System.out.println(restuarants.FoodType.toString());
                    ArrayList<String> FoodtypeArray=new ArrayList<>(restuarants.FoodType);
                    System.out.println("Choose a food type. if it is a food type of the restaurant it will be removed and if it doesnt exist int will be added");
                    System.out.println("1- FastFood");
                    System.out.println("2- Iranian");
                    System.out.println("3- Kebab");
                    System.out.println("4- Salad");
                    System.out.println("5- International");
                    System.out.println("0- end");
                    int command1;
                    boolean condition4 = true;
                    do {
                        command1=Command(scanner,5);
                        switch (command1){
                            case 1:
                                boolean condition3=false;
                                for (int i = 0 ; i < FoodtypeArray.size() ; i++){
                                    if (FoodtypeArray.get(i).equals("FastFood")) {
                                        FoodtypeArray.remove(i);
                                        condition3 = true;
                                    }
                                }
                                if (!condition3)
                                    FoodtypeArray.add("FastFood");
                                break;
                            case 2:
                                boolean condition5=false;
                                for (int i = 0 ; i < FoodtypeArray.size() ; i++){
                                    if (FoodtypeArray.get(i).equals("Iranian")) {
                                        FoodtypeArray.remove(i);
                                        condition5 = true;
                                    }
                                }
                                if (!condition5)
                                    FoodtypeArray.add("Iranian");
                                break;
                            case 3:
                                boolean condition6=false;
                                for (int i = 0 ; i < FoodtypeArray.size() ; i++){
                                    if (FoodtypeArray.get(i).equals("Kebab")) {
                                        FoodtypeArray.remove(i);
                                        condition6 = true;
                                    }
                                }
                                if (!condition6)
                                    FoodtypeArray.add("Kebab");
                                break;
                            case 4:
                                boolean condition7=false;
                                for (int i = 0 ; i < FoodtypeArray.size() ; i++){
                                    if (FoodtypeArray.get(i).equals("Salad")) {
                                        FoodtypeArray.remove(i);
                                        condition7 = true;
                                    }
                                }
                                if (!condition7)
                                    FoodtypeArray.add("Salad");
                                break;
                            case 5:
                                boolean condition8=false;
                                for (int i = 0 ; i < FoodtypeArray.size() ; i++){
                                    if (FoodtypeArray.get(i).equals("International")) {
                                        FoodtypeArray.remove(i);
                                        condition8 = true;
                                    }
                                }
                                if (!condition8)
                                    FoodtypeArray.add("International");
                                break;
                            case 0:
                                condition4=false;
                        }
                    }while (condition4);
                    restuarants.FoodType.clear();
                    restuarants.FoodType.addAll(FoodtypeArray);
                    String tempFT= restuarants.FoodType.toString();
                    String ft="";
                    if(tempFT.contains("FastFood")){
                        ft+="1";
                    } else if (tempFT.contains("Iranian")) {
                        ft+="2";
                    } else if (tempFT.contains("kebab")) {
                        ft+="3";
                    } else if (tempFT.contains("Salad")) {
                        ft+="4";
                    } else if (tempFT.contains("International")) {
                        ft+="5";
                    }
                    SQLiteJDBC.editResFT(restuarants.name,ft);
                    System.out.println("Food type has been changed successfully");
                    restaurantPage(scanner,admin,restuarants);
                    break;
                case 0:
                    restaurantPage(scanner,admin,restuarants);
                    break;
            }
        }while (true);
    } //Check
    public static void AddFood(Admin admin , Restuarants restuarants , Scanner scanner){
        boolean condition = true;
        String name;
        int Price=0;
        int Discount = -1;
        boolean search=false;
        do {
            System.out.println("Enter the name of the food");
            name = scanner.nextLine();
            if(restuarants.name.equals(SQLiteJDBC.searchFood(name,"Rname")))
                search=true;
            if(search)
                System.out.println("The entered food exists");
            try{
                switch (Integer.parseInt(name)){
                    case 0:
                        restaurantPage(scanner,admin,restuarants);
                        break;
                    case -99:
                        System.exit(0);
                        break;
                }
            }catch (Exception e){condition=false;}
        }while (condition);
        condition=true;
        do {
            System.out.println("Enter the price of the food");
            try {
                Price = Integer.parseInt(scanner.nextLine());
                condition=false;
            }catch (Exception e){
                System.out.println("Please enter number");
            }
        }while (condition);
        condition=true;
        do {
            boolean con = false;
            System.out.println("Enter the discount of the food");
            try {
                Discount = Integer.parseInt(scanner.nextLine());
                condition=false;
                if (Discount>100||Discount<0){
                    con = true;
                    condition=true;
                }
            }catch (Exception e){
                System.out.println("Please enter number");
            }
            if(con)
                System.out.println("Please enter a number between 0 and 100");
        }while (condition);
        restuarants.Foods++;
        Food food = new Food(name,Price,restuarants.Foods,Discount);
        SQLiteJDBC.insertFood(restuarants.name,admin.userName,name, food.price, food.discount);
        System.out.println("Food added successfully");
        restaurantPage(scanner,admin,restuarants);
    } //Check
    public static void AdminFoodList(Admin admin,Restuarants restuarants,Scanner scanner){
        System.out.println("0- Back");
        ArrayList<String> FoodList = new ArrayList<String>();
        FoodList = SQLiteJDBC.FoodList(restuarants.name, "Fname");
        if(FoodList.size()==0){
            System.out.println("No food registered yet");
        }
        else {
            for (int i = 0; i < FoodList.size(); i++) {
                System.out.println(i + 1 + "- " + FoodList.get(i));
            }
        }
        int command;
        do {
            command = Command(scanner, FoodList.size());
            if (command==0){
                FoodList=null;
                restaurantPage(scanner,admin,restuarants);
            }
            String fname = FoodList.get(command-1);
            String price = SQLiteJDBC.searchFood(fname,"Price");
            String diccount = SQLiteJDBC.searchFood(fname,"Discounte");
            String idf = SQLiteJDBC.searchFood(fname,"id");
            Food food = new Food(fname,Integer.parseInt(price),Integer.parseInt(idf),Integer.parseInt(diccount));
            FoodPage(scanner,admin,restuarants,food);
        }while (true);
    } //Check
    public static void RestaurantOrderPage(Scanner scanner,Admin admin,Restuarants restuarants){
        ArrayList<String> OrderList = new ArrayList<String>();
        OrderList = SQLiteJDBC.OrderRes(restuarants.name, "id");
        if(OrderList.size()==0){
            System.out.println("There is no order for this restaurant yet.");
        }
        else {
            for (int i = 0 ; i < OrderList.size() ; i++){
                String ofood = SQLiteJDBC.searchOrder(OrderList.get(i),"Food");
                String ouser = SQLiteJDBC.searchOrder(OrderList.get(i),"Uname");
                String oprice = SQLiteJDBC.searchOrder(OrderList.get(i),"Price");
                System.out.printf("%d- %s \n",i+1,ofood);
                System.out.printf("from user %s \n with price %d \n",ouser,Integer.parseInt(oprice));
                if (true)
                    System.out.println("condition: ACTIVE");
                else
                    System.out.println("condition:INACTIVE");
            }
        }
        System.out.println("Insert 0 to continue.");
        int command;
        do {
            command = Command(scanner,0);
            if (command==0){
                restaurantPage(scanner,admin,restuarants);
            }
        }while (true);
    }
    public static void FoodPage(Scanner scanner , Admin admin , Restuarants restuarants , Food food){
        System.out.println(food.name);
        System.out.println("Price: "+food.price);
        System.out.println("Discount: "+food.discount);
        System.out.println("1- Edit food");
        System.out.println("2- remove food");
        System.out.println("3- Show comments");
        System.out.println("4- Rating");
        System.out.println("0- Back");
        int command;
        do {
            command=Command(scanner,4);
            switch (command){
                case 1:
                    FoodEdit(scanner,admin,restuarants,food);
                    break;
                case 2:
                    SQLiteJDBC.delFood(food.name,restuarants.name);
                    food =null;
                    System.gc();
                    System.out.println("Food has been removed successfully");
                    AdminFoodList(admin,restuarants,scanner);
                    break;
                case 0:
                    food=null;
                    System.gc();
                    AdminFoodList(admin,restuarants,scanner);
                    break;
                case 3:
                    ShowComment(scanner,null,admin,food,restuarants);
                    break;
                case 4:
                    Rating(scanner,admin,null,restuarants,food);
            }
        }while (true);
    } //Check
    public static void FoodEdit(Scanner scanner , Admin admin , Restuarants restuarants , Food food){
        System.out.println("Which one do you want to change?");
        System.out.println("1- name");
        System.out.println("2- Price");
        System.out.println("3- Discount");
        System.out.println("0- Back");
        int command;
        do {
            command = Command(scanner,3);
            switch (command){
                case 1:
                    String NewName;
                    System.out.println("Please enter the new name");
                    NewName = scanner.nextLine();
                    SQLiteJDBC.editFoodName(food.name,NewName);
                    food.name=NewName;
                    System.out.println("Name has been changed successfully");
                    FoodPage(scanner,admin,restuarants,food);
                    break;
                case 2:
                    boolean condition=true;
                    int Price=0;
                    do {
                        System.out.println("Enter the price");
                        try {
                            Price = Integer.parseInt(scanner.nextLine());
                            condition=false;
                        }catch (Exception e){
                            System.out.println("Please enter number");
                        }
                    }while (condition);
                    SQLiteJDBC.editFoodPrice(food.name,Price);
                    food.price=Price;
                    System.out.println("Price has been changed successfully");
                    FoodPage(scanner,admin,restuarants,food);
                    break;
                case 3:
                    condition=true;
                    int Discount=-1;
                    do {
                        boolean con = false;
                        System.out.println("Enter the discount");
                        try {
                            Discount=Integer.parseInt(scanner.nextLine());
                            condition=false;
                            if(Discount>100||Discount<0){
                                con = true;
                                condition = true;
                            }
                        }catch (Exception e){
                            System.out.println("Please enter number");
                        }
                        if (con)
                            System.out.println("Discount must be between 0 and 100");
                    }while (condition);
                    SQLiteJDBC.editFoodDis(food.name,Discount);
                    food.discount=Discount;
                    System.out.println("Discount has been changed successfully");
                    FoodPage(scanner,admin,restuarants,food);
                    break;
                case 0:
                    FoodPage(scanner,admin,restuarants,food);
                    break;
            }
        }while (true);
    } //Check

    //Rating
    public static void Rating(Scanner scanner,Admin admin,User user,Restuarants restuarants,Food food){
        if(food==null){
            ArrayList<String> ratingList = new ArrayList<String>();
            ratingList = SQLiteJDBC.RatingRes(restuarants.name);
            restuarants.ratings=ratingList;
            System.out.println("Ratings of the restaurant "+restuarants.name);
            if(restuarants.ratings.size()==0)
                System.out.println("No rating registered yet");
            else {
                for (int i = 0; i < restuarants.ratings.size(); i++) {
                    System.out.print(restuarants.ratings.get(i) + "//");
                }
            }
            System.out.println();
            if(admin==null){
                System.out.println("Rate this restaurant");
                System.out.println("1- AWESOME");
                System.out.println("2- GOOD");
                System.out.println("3- NOTBAD");
                System.out.println("4- BAD");
                System.out.println("5- TERRIBLE");
                System.out.println("0- Back");
                do {
                    int command = Command(scanner,5);
                    switch (command){
                        case 1:
                         //   Rating rating = Rating.AWESOME;
                            restuarants.ratings.add("AWESOME");
                            SQLiteJDBC.insertRating(restuarants.name,"","AWESOME");
                            UserRestaurantPage(scanner,user,restuarants);
                            break;
                        case 2:
                          //  Rating rating1 = Rating.GOOD;
                            restuarants.ratings.add("GOOD");
                            SQLiteJDBC.insertRating(restuarants.name,"","GOOD");

                            UserRestaurantPage(scanner,user,restuarants);
                            break;
                        case 3:
                          //  Rating rating2 = Rating.NOTBAD;
                            restuarants.ratings.add("NOTBAD");
                            SQLiteJDBC.insertRating(restuarants.name,"","NOTBAD");
                            UserRestaurantPage(scanner,user,restuarants);
                            break;
                        case 4:
                          //  Rating rating3 = Rating.BAD;
                            restuarants.ratings.add("BAD");
                            SQLiteJDBC.insertRating(restuarants.name,"","BAD");
                            UserRestaurantPage(scanner,user,restuarants);
                            break;
                        case 5:
                            // Rating rating4 = Rating.TERRIBLE;
                            restuarants.ratings.add("TERRIBLE");
                            SQLiteJDBC.insertRating(restuarants.name,"","TERRIBLE");
                            UserRestaurantPage(scanner,user,restuarants);
                            break;
                        case 0:
                            UserRestaurantPage(scanner,user,restuarants);
                            break;
                    }
                }while (true);
            }
            System.out.println("Press 0 to continue");
            do {
                int com = Command(scanner,0);
                restaurantPage(scanner,admin,restuarants);
            }while (true);
        }
        else{
            ArrayList<String> ratingList = new ArrayList<String>();
            ratingList = SQLiteJDBC.RatingFood(food.name);
            food.ratings=ratingList;
            System.out.println("Ratings of the food "+food.name);
            if(food.ratings.size()==0)
                System.out.println("No rating registered yet");
            else {
                for (int i = 0; i < food.ratings.size(); i++) {
                    System.out.print(food.ratings.get(i) + "//");
                }
            }
            System.out.println();
            if(admin==null){
                System.out.println("Rate this restaurant");
                System.out.println("1- AWESOME");
                System.out.println("2- GOOD");
                System.out.println("3- NOTBAD");
                System.out.println("4- BAD");
                System.out.println("5- TERRIBLE");
                System.out.println("0- Back");
                do {
                    int command = Command(scanner,5);
                    switch (command){
                        case 1:
                            //Rating rating = Rating.AWESOME;
                            food.ratings.add("AWESOME");
                            SQLiteJDBC.insertRating(restuarants.name,food.name,"AWESOME");
                            FoodPage(scanner,user,restuarants,food);
                            break;
                        case 2:
                          //  Rating rating1 = Rating.GOOD;
                            food.ratings.add("GOOD");
                            SQLiteJDBC.insertRating(restuarants.name,food.name,"GOOD");
                            FoodPage(scanner,user,restuarants,food);
                            break;
                        case 3:
                           // Rating rating2 = Rating.NOTBAD;
                            food.ratings.add("NOTBAD");
                            SQLiteJDBC.insertRating(restuarants.name,food.name,"NOTBAD");
                            FoodPage(scanner,user,restuarants,food);
                            break;
                        case 4:
                           // Rating rating3 = Rating.BAD;
                            food.ratings.add("BAD");
                            SQLiteJDBC.insertRating(restuarants.name,food.name,"BAD");
                            FoodPage(scanner,user,restuarants,food);
                            break;
                        case 5:
                          //  Rating rating4 = Rating.TERRIBLE;
                            food.ratings.add("TERRIBLE");
                            SQLiteJDBC.insertRating(restuarants.name,food.name,"TERRIBLE");
                            FoodPage(scanner,user,restuarants,food);
                            break;
                        case 0:
                            FoodPage(scanner,user,restuarants,food);
                            break;
                    }
                }while (true);
            }
            System.out.println("Press 0 to continue");
            do {
                int com = Command(scanner,0);
                FoodPage(scanner,admin,restuarants,food);
            }while (true);
        }
    }
    public static void ShowComment(Scanner scanner,User user , Admin admin , Food food , Restuarants restuarants){
        if(food==null){
            System.out.println("Comments for restaurant "+restuarants.name);
            ArrayList<String> CommentList = new ArrayList<String>();
            CommentList = SQLiteJDBC.CommentListRes(restuarants.name, "Comment");
            if(CommentList.size()==0)
                System.out.println("There is no comment yet.");
            else {
                for (int i = 0 ; i < CommentList.size() ; i++){
                    System.out.println("From user "+SQLiteJDBC.searchComment(CommentList.get(i),"User","0"));
                    System.out.println(CommentList.get(i));
                }
            }
            System.out.println("Press 0 to get back");

            do {
                int command=Command(scanner,0);
                if(admin==null){
                    UserRestaurantPage(scanner,user,restuarants);
                }
                else
                    restaurantPage(scanner,admin,restuarants);
            }while (true);
        }
        else {
            System.out.println("Comments for food "+food.name);
            ArrayList<String> CommentList = new ArrayList<String>();
            CommentList = SQLiteJDBC.CommentListFood(food.name, "Comment");
            if(CommentList.size()==0)
                System.out.println("There is no comment yet.");
            else {
                for (int i = 0 ; i < CommentList.size() ; i++){
                    System.out.println("From user "+SQLiteJDBC.searchComment(CommentList.get(i),"User",food.name));
                    System.out.println(CommentList.get(i));
                }
            }
            System.out.println("Press 0 to get back");

            do {
                int command=Command(scanner,0);
                if(admin==null){
                    FoodPage(scanner,user,restuarants,food);
                }
                else
                    FoodPage(scanner,admin,restuarants,food);
            }while (true);
        }
    } //Check
    public static int Command(Scanner scanner,int limit){
        boolean condition=true;
        String command= scanner.nextLine();
        int commandInt=-10;
        try {
            commandInt = Integer.parseInt(command);
        }catch (Exception InputMismatchException){
            System.out.println("Entered command is not correct. Please enter a number");
            condition=false;

        }
        if(condition) {
            if(commandInt==-99)
                System.exit(0);
            if (commandInt > limit || commandInt < 0)
                System.out.println("invalid command.Please choose a number from command list");
        }
        return commandInt;
    }
}