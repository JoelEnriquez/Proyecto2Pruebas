/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author Joel Enriquez
 */
public class Conexion {
    private static final String URL="jdbc:mysql://localhost:3306/HOSPITAL";
    private String user="userhospital";
    private String password="hospital";
    private static Connection conexion=null;

    public static Connection getConexion(){
        if (conexion==null) {
            new Conexion();
        }
        return conexion;
    }
    
    private Conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(URL,user,password);
            
        } catch (SQLException | ClassNotFoundException ex) {
            ex.getMessage();
        }
    }
}
