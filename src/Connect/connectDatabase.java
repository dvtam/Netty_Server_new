/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connect;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author TAM95
 */
public class connectDatabase {
    private static Connection con;
    public static String notifi="";
    public static Connection getConnection()
    {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;user=sa;password=sa;database=data_Babicare");
            System.out.println("Server connected to Database");
            notifi="Server connected to Database";
        } catch (Exception e) {
            System.out.println("Can't connect to Database!!");
            notifi="Can't connect to Database!!";
        }
        return con;
    }
}
