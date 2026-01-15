import java.sql.Connection;
import java.sql.DriverManager;
//CONNECTION LOGIC
public class DBConnection {
    public static Connection getConnection() {  //{{making it static to call from other class without object}}
        try{
            // jdbc=java database connectivity ( an API that acts as a translator between Java and relational DB like MySQL
                            // PROTOCOL||COMPUTER|PORT||DB name
            String url = "jdbc:mysql://localhost:3306/library_db";//tells location,,,connection string
            return DriverManager.getConnection(url, "root", "mysql19t02r06");//uses .jar driver to call library_db(database) and send credentials to server
        } catch (Exception e) {
            //handles runtime errors( like wrong pass or server being off)
            e.printStackTrace(); //prints exact error location to debug it
            return null;
        }
    }
}
