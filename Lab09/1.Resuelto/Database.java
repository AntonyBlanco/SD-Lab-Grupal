package Lab09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @web https://www.jc-mouse.net/
 * @author Mouse
 */
public class Database {

    // Actualiza la información de la base de datos
    private final static String bd = "mydb3";
    private final static String login = "root";
    private final static String password = ""; // Asumiendo que no hay contraseña
    private final static String url = "jdbc:mysql://localhost:3306/" + bd;

    public static Connection getConnection() {
        try {
            // Cargar el controlador JDBC de MySQL
            Class.forName("com.mysql.jdbc.Driver");
            // Intentar establecer la conexión
            Connection conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                System.out.println("Conectado a la base de datos [" + bd + "]");
            }
            return conn;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}

