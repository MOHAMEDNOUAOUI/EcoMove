import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


class TrainData {

    private static Connection conn;

    private static final String URL = "jdbc:postgresql://localhost:5432/EcoMove";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    private TrainData() {}

    static{
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URL , USER , PASSWORD);
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("something wrong");
        }
    }


    public static synchronized Connection GetConnection() {
        return conn;
    }





}
