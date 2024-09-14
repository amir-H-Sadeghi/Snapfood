public class Comment {
    Comment(User user1,String comment1,Restuarants restuarants1 ){
        user=user1;comment=comment1;restuarants=restuarants1;food=null;
    }
    Comment(User user1,String comment1,Restuarants restuarants1,Food food1){
        user=user1;comment=comment1;restuarants=restuarants1;food=food1;
    }
    User user;
    String comment;
    Restuarants restuarants;
    Food food;
}
