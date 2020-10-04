/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LecturaXML;

import ConexionDB.Conexion;
import java.io.IOException;
import java.sql.Connection;

/**
 *
 * @author Joel Enriquez
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection conexion = Conexion.getConexion();
        LecturaXML xml = new LecturaXML("data2.xml",conexion);
        try {
            xml.leerXML();
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        } 

    }

}
