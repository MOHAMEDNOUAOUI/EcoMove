import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database
{
    static Connection conn;
    private static final String URL = "jdbc:mysql://localhost/EcoMove";
    private static final String USER = "root";
    private static final String PASSWORD = "";


    private Database() {}

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.", e);
        }
    }

    public static synchronized Connection getConnection() {
        return conn;
    }




}
