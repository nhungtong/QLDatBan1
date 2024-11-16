/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbconnection;
import java.sql.*;
/**
 *
 * @author hi tcc
 */
public class KetNoiCSDL {
    private static final String URL ="jdbc:mysql://localhost:3306/qldatban";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;
    public static Connection getConnection()
    {
        if(connection == null)
        {
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }catch(ClassNotFoundException | SQLException e)
            {
                 e.printStackTrace();
            }
        }
            return connection;
    }
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
