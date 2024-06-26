package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private final static String bd = "mydb4";
    private final static String login = "root";
    private final static String password = "";
    private final static String url = "jdbc:mysql://localhost:3306/" + bd;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                System.out.println("Conectado a la base de datos [" + bd + "]");
            }
            return conn;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}

