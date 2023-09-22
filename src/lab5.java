import java.sql.*;
import java.util.*;

public class lab5 {

	public static void main(String[] args) throws SQLException {
		String url = "jdbc:sqlite:C:/Users/kahil/eclipse-workspace/COE848_lab5/EBook.db";
        Connection c=null;   
        Scanner s = new Scanner(System.in);
        String selected =null;
        Boolean quit=false;     
		try {       
			Class.forName("org.sqlite.JDBC");       
			c = DriverManager.getConnection(url);
            System.out.println("Opened database successfully");
	    } 
		catch ( Exception e )  {      
			System.err.println(e.getMessage());     
	    }
		
		System.out.println("1.  List books by ratings");
        System.out.println("2.  List books by the number of ratings");
        System.out.println("3.  List books of a specific author");
        System.out.println("4.  List books by price");
        System.out.println("5.  List books of a particular genre");
        System.out.println("6.  List books of one particular publisher");
        System.out.println("7.  List new books");
        System.out.println("8.  List books that are out of stock");
        System.out.println("9.  Which is the best-selling book for a specific author");
        System.out.println("10. What is the highest-rated book for friend X");
        System.out.println("11. Select the publisher that has the best rating on average");
        System.out.println("12. Select author that has lowest ratings on average");
        System.out.println("Q.  Quit");
        
        while (quit==false) {
            System.out.println("Enter Selection: ");
            selected=s.nextLine();
            switch (selected) {
            case "Q":
                quit=true;
                break;
            case "1":
                Query1(c,url);
                break;
            case "2":
                Query2(c,url);
                break;
            case "3":
                Query3(c,url,selected, s);
                break;
            case "4":
                Query4(c,url);
                break;
            case "5":
                Query5(c,url,selected, s);
                break;
            case "6":
                Query6(c,url,selected, s);
                break;
            case "7":
                Query7(c,url);
                break;
            case "8":
                Query8(c,url);
                break;
            case "9":
                Query9(c,url,selected, s);
                break;
            case "10":
                Query10(c,url,selected, s);
                break;
            case "11":
                Query11(c,url);
                break;
            case "12":
                Query12(c,url);
                break;
            default:
                System.out.println("Invaild Selection!\n Please choose between 1-12 or Q");
                break;
            }
        }
        System.out.println("Connection Closed");
        c.close();
	}
	
	public static void Query1(Connection c, String url){
        String query = "SELECT Title, AVG(Rating)\n" +
                "FROM Feedback AS F, Book AS B\n" +
                "WHERE F.ISBN =B.ISBN\n" +
                //"GROUP BY Title;";
                "GROUP BY Title\n"+
                "ORDER BY AVG(Rating) DESC;";
        int i = 0;

        try{
            Connection myCon = DriverManager.getConnection(url);
            Statement myStmt = myCon.createStatement();
            ResultSet myRes = myStmt.executeQuery(query);

            while(myRes.next()){
                i = 1;
                String Title = myRes.getString(i++);
                String AVGR = myRes.getString(i++);
                System.out.println("Title: " + Title + " Average Rating: " + AVGR );
            }
            myStmt.close();
        }
        catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
    public static void Query2(Connection c, String url){
        String query = "SELECT  Title, Count(Rating) AS NumRating\n" +
                "FROM Feedback AS F, Book AS B\n" +
                "WHERE F.ISBN =B.ISBN\n" +
                "GROUP BY Title\n"+
                "ORDER BY NumRating DESC;";
        int i = 0;

        try{
            Connection myCon = DriverManager.getConnection(url);
            Statement myStmt = myCon.createStatement();
            ResultSet myRes = myStmt.executeQuery(query);

            while(myRes.next()){
                i = 1;
                String Title = myRes.getString(i++);
                String NUMR = myRes.getString(i++);
                System.out.println("Title: " + Title + " Number of Ratings: " + NUMR );
            }
            myStmt.close();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public static void Query3(Connection c, String url, String selected, Scanner s){
    	System.out.println("Enter Author Name: ");
    	selected=s.nextLine();
        String query = "SELECT  Title\n" +
                "FROM Book AS B\n" +
                //"WHERE B.Author='Catherine Hernandez';" ;
                "WHERE B.Author="+"'"+selected+"'"+ ";" ;
        int i = 0;

        try{
            Connection myCon = DriverManager.getConnection(url);
            Statement myStmt = myCon.createStatement();
            ResultSet myRes = myStmt.executeQuery(query);

            while(myRes.next()){
                i = 1;
                String Title = myRes.getString(i++);
                System.out.println("Title: " + Title);
            }
            myStmt.close();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public static void Query4(Connection c, String url){
        String query = "SELECT  Title, Price\n" +
                "FROM Book AS B\n"+
                "ORDER BY Price DESC;";
        int i = 0;

        try{
            Connection myCon = DriverManager.getConnection(url);
            Statement myStmt = myCon.createStatement();
            ResultSet myRes = myStmt.executeQuery(query);

            while(myRes.next()){
                i = 1;
                String Title = myRes.getString(i++);
                String Price = myRes.getString(i++);
                System.out.println("Title: " + Title +" Price: " +Price);
            }
            myStmt.close();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public static void Query5(Connection c, String url, String selected, Scanner s){
    	System.out.println("Enter Genre: ");
    	selected=s.nextLine();
    	String query = "SELECT  Title\n" +
                "FROM Book AS B, BookGenre As BG\n"+
                //"WHERE B.ISBN =BG.ISBN AND BG.GName= 'Science Fiction';";
                "WHERE B.ISBN =BG.ISBN AND BG.GName="+"'"+selected+"'"+ ";" ;
        int i = 0;

        try{
            Connection myCon = DriverManager.getConnection(url);
            Statement myStmt = myCon.createStatement();
            ResultSet myRes = myStmt.executeQuery(query);

            while(myRes.next()){
                i = 1;
                String Title = myRes.getString(i++);
                System.out.println("Title: " + Title);
            }
            myStmt.close();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public static void Query6(Connection c, String url, String selected, Scanner s){
    	System.out.println("Enter PubID: ");
    	selected=s.nextLine();
    	String query = "SELECT  CompanyName, Title\n" +
                "FROM Book AS B, Publisher As P\n"+
                //"WHERE B.PubID=P.PubID AND P.PubID= 5;";
                "WHERE B.PubID=P.PubID AND P.PubID="+"'"+selected+"'"+ ";" ;
        int i = 0;

        try{
            Connection myCon = DriverManager.getConnection(url);
            Statement myStmt = myCon.createStatement();
            ResultSet myRes = myStmt.executeQuery(query);

            while(myRes.next()){
                i = 1;
                String CompanyName = myRes.getString(i++);
                String Title = myRes.getString(i++);
                System.out.println("CompanyName: "+CompanyName +" Title: " + Title);
            }
            myStmt.close();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public static void Query7(Connection c, String url){
        String query = "SELECT  Title\n" +
                "FROM Book AS B\n"+
                "WHERE B.Realease>='2022-1-1';";
        int i = 0;

        try{
            Connection myCon = DriverManager.getConnection(url);
            Statement myStmt = myCon.createStatement();
            ResultSet myRes = myStmt.executeQuery(query);

            while(myRes.next()){
                i = 1;
                String Title = myRes.getString(i++);
                System.out.println("Title: " + Title);
            }
            myStmt.close();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public static void Query8(Connection c, String url){
        String query = "SELECT  Title\n" +
                "FROM Book AS B\n"+
                "WHERE B.Stock=FALSE;";
        int i = 0;

        try{
            Connection myCon = DriverManager.getConnection(url);
            Statement myStmt = myCon.createStatement();
            ResultSet myRes = myStmt.executeQuery(query);

            while(myRes.next()){
                i = 1;
                String Title = myRes.getString(i++);
                System.out.println("Title: " + Title);
            }
            myStmt.close();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public static void Query9(Connection c, String url, String selected, Scanner s){
    	System.out.println("Enter Author Name: ");
    	selected=s.nextLine();
    	String query = "SELECT  Author, Title, MAX(Sales)\n" +
                "FROM Book AS B\n"+
                //"WHERE Author='Andre Alexis';";
                "WHERE Author="+"'"+selected+"'"+ ";" ;
        int i = 0;

        try{
            Connection myCon = DriverManager.getConnection(url);
            Statement myStmt = myCon.createStatement();
            ResultSet myRes = myStmt.executeQuery(query);

            while(myRes.next()){
                i = 1;
                String Author = myRes.getString(i++);
                String Title = myRes.getString(i++);
                String MSales = myRes.getString(i++);
                System.out.println("Author: " +Author+" Title: " + Title+" Max Sales: " +MSales);
            }
            myStmt.close();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public static void Query10(Connection c, String url, String selected, Scanner s){
    	System.out.println("Enter Friend UserID: ");
    	selected=s.nextLine();
        String query = "SELECT   FirstName, LastName, Title, MAX(Rating)\n" +
                "FROM Feedback AS F, Book AS B, ORDR AS O, User As U\n"+
                //"WHERE F.ISBN=B.ISBN AND B.OrderID=O.OrderID AND O.UserID=U.UserID AND U.UserID=1;";
                "WHERE F.ISBN=B.ISBN AND B.OrderID=O.OrderID AND O.UserID=U.UserID AND U.UserID="+"'"+selected+"'"+ ";" ;
        int i = 0;

        try{
            Connection myCon = DriverManager.getConnection(url);
            Statement myStmt = myCon.createStatement();
            ResultSet myRes = myStmt.executeQuery(query);

            while(myRes.next()){
                i = 1;
                String FirstName = myRes.getString(i++);
                String LastName = myRes.getString(i++);
                String Title = myRes.getString(i++);
                String MRate = myRes.getString(i++);
                System.out.println("FirstName: " +FirstName+" LastName: " +LastName+" Title: " + Title+" Max Rate: " +MRate);
            }
            myStmt.close();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public static void Query11(Connection c, String url){
        String query = "SELECT  CompanyName, Title, MAX(AVGR)\n" +
                "FROM (SELECT  CompanyName, Title, AVG(Rating) AS AVGR\n" +
"FROM Book AS B, Publisher As P, Feedback As F\n" +
"WHERE B.PubID=P.PubID AND F.ISBN =B.ISBN \n" +
"GROUP BY CompanyName);";
        int i = 0;

        try{
            Connection myCon = DriverManager.getConnection(url);
            Statement myStmt = myCon.createStatement();
            ResultSet myRes = myStmt.executeQuery(query);

            while(myRes.next()){
                i = 1;
                String CompanyName = myRes.getString(i++);
                String Title = myRes.getString(i++);
                String MARate = myRes.getString(i++);
                System.out.println("Company Name: " +CompanyName+" Title: " + Title+" Max Average Rate: " +MARate);
            }
            myStmt.close();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public static void Query12(Connection c, String url){
        String query = "SELECT  Author, Title, MIN(AVGR)\n" +
                "FROM (SELECT  Author, Title, AVG(Rating) AS AVGR\n" +
"FROM Book AS B, Feedback As F\n" +
"WHERE F.ISBN =B.ISBN \n" +
"GROUP BY Author);";
        int i = 0;

        try{
            Connection myCon = DriverManager.getConnection(url);
            Statement myStmt = myCon.createStatement();
            ResultSet myRes = myStmt.executeQuery(query);

            while(myRes.next()){
                i = 1;
                String Author = myRes.getString(i++);
                String Title = myRes.getString(i++);
                String MARate = myRes.getString(i++);
                System.out.println("Author: " +Author+" Title: " + Title+" Min Average Rate: " +MARate);
            }
            myStmt.close();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }


}
