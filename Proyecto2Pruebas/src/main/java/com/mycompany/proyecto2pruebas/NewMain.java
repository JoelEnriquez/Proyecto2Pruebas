/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto2pruebas;

import java.io.IOException;

/**
 *
 * @author Joel Enriquez
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PruebaXML xml = new PruebaXML("data.xml");
        try {
            xml.leerXML();
        } catch (IOException ex) {
            ex.getMessage();
        }
        
    }
    
}
