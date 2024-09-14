import java.sql.*;
import java.util.ArrayList;

public class SQLiteJDBC {
    private static Connection connect() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public static void insertSignUp(String username, String password,String location, int status) {
        if(status==1) {
            String sql = "INSERT INTO ADMINS(username,password) VALUES(?,?)";

            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (status==2) {
            String sql = "INSERT INTO CLIENT(username,password,Credit,Location) VALUES(?,?,?,?)";
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, "0");
                pstmt.setString(4, location);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void insertRestuarant(String Rname, String Aname, String location, String Rnumber, String FoodType) {

        String sql = "INSERT INTO RESTUARANT(RName,AdminName,Location,RNumber,FoodType) VALUES(?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Rname);
            pstmt.setString(2, Aname);
            pstmt.setString(3,location);
            pstmt.setString(4, Rnumber);
            pstmt.setString(5, FoodType);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertOrder(String Rname, String Uname, String Food,String Price) {

        String sql = "INSERT INTO Orders(RName,Uname,Food,Price) VALUES(?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Rname);
            pstmt.setString(2, Uname);
            pstmt.setString(3, Food);
            pstmt.setString(4, Price);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertFood(String Rname, String Aname, String Fname,int Price, int Discount) {

        String sql = "INSERT INTO Food(Rname,Aname,Fname,Price,Discounte) VALUES(?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Rname);
            pstmt.setString(2, Aname);
            pstmt.setString(3, Fname);
            pstmt.setInt(4, Price);
            pstmt.setInt(5, Discount);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static void insertComment (String comment, String res, String User,String food) {

        String sql = "INSERT INTO COMMENT(Comment,Res,User,Food) VALUES(?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, comment);
            pstmt.setString(2, res);
            pstmt.setString(3, User);
            pstmt.setString(4, food);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertRating (String Rname, String Fname, String Rating) {

        String sql = "INSERT INTO Rating(Rname,Fname,Rating) VALUES(?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Rname);
            pstmt.setString(2, Fname);
            pstmt.setString(3, Rating);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<String> OrderUser(String usrename ,String field) {

        String sql = "SELECT * FROM Orders WHERE Uname=?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usrename);
            ResultSet resultSet = pstmt.executeQuery();
            ArrayList<String> list = new ArrayList<String>();
            while (resultSet.next()){
                list.add(resultSet.getString(field));
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static ArrayList<String> RatingFood(String usrename ) {

        String sql = "SELECT * FROM Rating WHERE Fname=?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usrename);
            ResultSet resultSet = pstmt.executeQuery();
            ArrayList<String> list = new ArrayList<String>();
            while (resultSet.next()){
                list.add(resultSet.getString("Rating"));
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static ArrayList<String> RatingRes(String usrename ) {

        String sql = "SELECT * FROM Rating WHERE Rname=?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usrename);
            ResultSet resultSet = pstmt.executeQuery();
            ArrayList<String> list = new ArrayList<String>();
            while (resultSet.next()){
                list.add(resultSet.getString("Rating"));
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static ArrayList<String> CommentListRes(String usrename ,String field) {

        String sql = "SELECT * FROM COMMENT WHERE Res=? AND Food=?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usrename);
            pstmt.setString(2, "0");
            ResultSet resultSet = pstmt.executeQuery();
            ArrayList<String> list = new ArrayList<String>();
            while (resultSet.next()){
                list.add(resultSet.getString(field));
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static ArrayList<String> CommentListFood(String usrename ,String field) {

        String sql = "SELECT * FROM COMMENT WHERE  Food=?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usrename);
            ResultSet resultSet = pstmt.executeQuery();
            ArrayList<String> list = new ArrayList<String>();
            while (resultSet.next()){
                list.add(resultSet.getString(field));
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static ArrayList<String> RestuarantListUser(String field) {

        String sql = "SELECT * FROM RESTUARANT;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            ArrayList<String> list = new ArrayList<String>();
            while (resultSet.next()){
                list.add(resultSet.getString(field));
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static ArrayList<String> RestuarantList(String Aname , String field) {

        String sql = "SELECT * FROM RESTUARANT WHERE AdminName=?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Aname);
            ResultSet resultSet = pstmt.executeQuery();
            ArrayList<String> list = new ArrayList<String>();
            while (resultSet.next()){
                list.add(resultSet.getString(field));
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static ArrayList<String> FoodList(String Rname , String Fname) {

        String sql = "SELECT * FROM Food WHERE Rname=?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Rname);
            ResultSet resultSet = pstmt.executeQuery();
            ArrayList<String> list = new ArrayList<String>();
            while (resultSet.next()){
                list.add(resultSet.getString(Fname));
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static String ResObject(String username ,String field){

        String sql = "SELECT * FROM RESTUARANT WHERE RName=?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            String vijegi =resultSet.getString(field);
            return vijegi;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static String searchOrder (String username ,String field){
        String sql = "SELECT * FROM Orders WHERE id=?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(username));
            ResultSet resultSet = pstmt.executeQuery();
            String Rname =resultSet.getString(field);
            return Rname;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static String searchFood(String username ,String field){
        String sql = "SELECT * FROM Food WHERE Fname=?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            String Rname =resultSet.getString(field);
            return Rname;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static String searchComment(String username ,String field,String food){
        String sql = "SELECT * FROM COMMENT WHERE Comment=? AND Food =?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, food);
            ResultSet resultSet = pstmt.executeQuery();
            String Rname =resultSet.getString(field);
            return Rname;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static String searchFoodid(int id ,String field){
        String sql = "SELECT * FROM Food WHERE id=?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            String Rname =resultSet.getString(field);
            return Rname;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static boolean searchRes (String username){
        String sql = "SELECT * FROM RESTUARANT WHERE Rname=?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            String password =resultSet.getString("Rnumber");
            if(password == null){
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public static String search(String username ,String field, int status){
        if (status==1) {
            String sql = "SELECT * FROM ADMINS WHERE USERNAME=?;";
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                ResultSet resultSet = pstmt.executeQuery();
                String password =resultSet.getString(field);
                return password;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (status==2) {
            String sql = "SELECT * FROM CLIENT WHERE USERNAME=?;";
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                ResultSet resultSet = pstmt.executeQuery();
                String password = resultSet.getString(field);
                return password;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public static void editUserCredit(String usrename, String newData){

        String sql = "UPDATE CLIENT SET Credit = ? WHERE USERNAME = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newData);
            pstmt.setString(2, usrename);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public static void editResName(String Rname , String newData){

        String sql = "UPDATE RESTUARANT SET RName = ? WHERE RName = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newData);
            pstmt.setString(2, Rname);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public static void editResLocation(String Rname , String newData){

        String sql = "UPDATE RESTUARANT SET Location = ? WHERE RName = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newData);
            pstmt.setString(2, Rname);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    } public static void editResFT(String Rname , String newData){

        String sql = "UPDATE RESTUARANT SET FoodType = ? WHERE RName = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newData);
            pstmt.setString(2, Rname);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public static void editFoodName(String Fname , String newData){

        String sql = "UPDATE Food SET FName = ? WHERE FName = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newData);
            pstmt.setString(2, Fname);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void editFoodPrice(String Fname , int newData){

        String sql = "UPDATE Food SET Price = ? WHERE FName = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newData);
            pstmt.setString(2, Fname);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void editFoodDis(String Fname , int newData){

        String sql = "UPDATE Food SET Discounte = ? WHERE FName = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newData);
            pstmt.setString(2, Fname);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void delFood(String Fname, String Rname){
        String sql = "DELETE FROM Food WHERE Fname=? AND Rname =?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Fname);
            pstmt.setString(2, Rname);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void delRes(String Rnam){

        String sql = "DELETE FROM RESTUARANT WHERE RName=?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Rnam);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createNewTable() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String url = "jdbc:sqlite:database.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Rating ("
                + "	id integer PRIMARY KEY,"
                + "	Rname TEXT NOT NULL,"
                + "	Fname TEXT NOT NULL,"
                + "	Rating TEXT NOT NULL"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<String> OrderRes(String username, String field) {
        String sql = "SELECT * FROM Orders WHERE Rname=?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            ArrayList<String> list = new ArrayList<String>();
            while (resultSet.next()){
                list.add(resultSet.getString(field));
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;

    }
}