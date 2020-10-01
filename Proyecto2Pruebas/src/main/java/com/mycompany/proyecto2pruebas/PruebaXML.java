/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto2pruebas;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jdom2.input.SAXBuilder;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

/**
 *
 * @author Joel Enriquez
 */
public class PruebaXML {

    private String pathXML;

    public PruebaXML(String pathXML) {
        this.pathXML = pathXML;
    }

    public void leerXML() throws IOException {
        SAXBuilder builder = new SAXBuilder();
        
        try {
            File archivoEntradaXML = new File(pathXML);
            Document documento = (Document) builder.build(archivoEntradaXML);
            Element root = documento.getRootElement();

            //Datos que no dependen de la estructura de la DB
            List<Element> listaAdmins = root.getChildren("admin");
            List<Element> listaDoctores = root.getChildren("doctor");
            List<Element> listaLaboratoristas = root.getChildren("laboratorista");
            List<Element> listaPacientes = root.getChildren("paciente");
            
            //Datos que dependen de la estructura de la DB
            List<Element> listaExamenes = root.getChildren("examen");
            List<Element> listaReportes = root.getChildren("reporte");
            List<Element> listaResultados = root.getChildren("resultado");
            List<Element> listaCitas = root.getChildren("cita");
            List<Element> listaConsultas = root.getChildren("consulta");

            for (int i = 0; i < listaAdmins.size(); i++) {
                Element admin = listaAdmins.get(i);
                String codigo = admin.getChildTextTrim("CODIGO");
                String DPI = admin.getChildTextTrim("DPI");
                String NOMBRE = admin.getChildTextTrim("NOMBRE");
                String PASSWORD = admin.getChildTextTrim("PASSWORD");
                System.out.println(codigo + "\t" + DPI + "\t" + NOMBRE + "\t" + PASSWORD);
            }
        } catch (JDOMException e) {
            throw new IOException("No se ha encontrado el archivo xml");
        }
    }
}
