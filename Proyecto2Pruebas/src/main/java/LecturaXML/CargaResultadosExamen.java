/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LecturaXML;

import EntidadesHospital.Examen;
import EntidadesHospital.ResultadoExamen;
import EntidadesHospital.TipoExamen;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Element;

/**
 *
 * @author joel
 */
public class CargaResultadosExamen {
    
    private Connection conexion;
    private ArrayList<Integer> codigosExamenConOrden;
    private List<Element>listaResultados;

    public CargaResultadosExamen(Connection conexion,List<Element>listaResultados) {
        this.conexion = conexion;
        this.listaResultados = listaResultados;
    }

    public void setCodigosExamenConOrden(ArrayList<Integer> codigosExamenConOrden) {
        this.codigosExamenConOrden = codigosExamenConOrden;
    }
    
    public void agregarExamenesYResultados() throws FileNotFoundException, ParseException{
        int auxCodigoExamen = 1;
                
        for (int i = 0; i < listaResultados.size(); i++) {
            Element result = listaResultados.get(i);
            
            String codigo = result.getChildTextTrim("CODIGO");
            String codigoPaciente = result.getChildTextTrim("PACIENTE");
            String codigoTipoExamen = result.getChildTextTrim("EXAMEN");
            String codigoMedico = result.getChildTextTrim("MEDICO");
            //Comprobar que no venga nulo
            if (codigoMedico.isEmpty()) {
                codigoMedico = null;
            }
            String codigoLaboratorista = result.getChildTextTrim("LABORATORISTA");
            
            //PDF de orden Medica
            String archivoOrden = result.getChildTextTrim("ORDEN");
            InputStream inputOrden = null;
            if (!archivoOrden.equals("")) {
                File orden = new File(archivoOrden);
                inputOrden = new FileInputStream(orden);
            }
            //Archivo de informe de resultado
            String archivoInforme = result.getChildTextTrim("INFORME");
            File informe = new File(archivoInforme);
            InputStream inputInforme = new FileInputStream(informe);
            
            Date fechaExamen = Date.valueOf(result.getChildTextTrim("FECHA"));
            
            //Convertir a un formato correcto la hora
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            String hourExamen =  result.getChildTextTrim("HORA");
            java.util.Date fExam = (java.util.Date)format.parse(hourExamen);
            Time horaExamen = new Time(fExam.getTime());
            
            boolean requiereOrden = false;
            if (codigosExamenConOrden.contains(Integer.parseInt(codigoTipoExamen))) {
                requiereOrden = true;
            }
            Examen examen = new Examen(auxCodigoExamen, fechaExamen, horaExamen,
                    requiereOrden, codigoPaciente, codigoMedico, codigoTipoExamen);
            agregarExamen(examen);
            
            ResultadoExamen resultadoE = new ResultadoExamen(codigo, inputOrden,
                    inputInforme, horaExamen, fechaExamen, codigoPaciente,
                    codigoMedico, codigoLaboratorista, auxCodigoExamen);
            agregarResultado(resultadoE);
            
            auxCodigoExamen++;
        }
    }
    
    public void agregarTipoExamen(TipoExamen tipoExamen){
        String query = "INSERT INTO TIPO_EXAMEN VALUES (?,?,?,?,?)";
        
        int codigoResultado = Integer.parseInt(tipoExamen.getCodigo());
        String nombreExamen = tipoExamen.getNombreExamen();
        String descripcionExamen = tipoExamen.getDescripcion();
        double costoExamen = tipoExamen.getCostoExamen();
        String formatoInforme = tipoExamen.getFormatoInforme();
        
        try(PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, codigoResultado);
            ps.setString(2, nombreExamen);
            ps.setString(3, descripcionExamen);
            ps.setDouble(4, costoExamen);
            ps.setString(5, formatoInforme);
            
            ps.execute();
            
        } catch (Exception e) {
            e.getMessage();
        }
    }
    
    private void agregarResultado(ResultadoExamen resultado){
        String query = "INSERT INTO RESULTADO_EXAMEN VALUES (?,?,?,?,?,?,?,?,?)";
        
        String codigoResultado = resultado.getCodigo();
        InputStream ordenMedica = resultado.getOrden();
        InputStream informeExamen = resultado.getInforme();
        Time horaExamen = resultado.getHoraExamen();
        Date fechaExamen = resultado.getFechaExamen();
        int codigoPaciente = Integer.parseInt(resultado.getCodigoPaciente());
        String codigoMedico = resultado.getCodigoMedico();
        String codigoLaboratorista = resultado.getCodigoLaboratorista();
        int codigoExamen = resultado.getCodigoExamen();
        
        try(PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, codigoResultado);
            ps.setBlob(2, ordenMedica);
            ps.setBlob(3, informeExamen);
            ps.setTime(4, horaExamen);
            ps.setDate(5, fechaExamen);
            ps.setInt(6, codigoPaciente);
            ps.setString(7, codigoMedico);
            ps.setString(8, codigoLaboratorista);
            ps.setInt(9, codigoExamen);
            
            //Error
            ps.execute();
            
        } catch (Exception e) {
            e.getMessage();
        }
    }
    
    private void agregarExamen(Examen examen){
        String query = "INSERT INTO EXAMEN VALUES (?,?,?,?,?,?,?)";
        
        int codigoExamen = examen.getCodigo();
        Date fechaExamen = examen.getFechaCita();
        Time horaExamen = examen.getHoraCita();
        boolean requiereOrden = examen.getRequiereOrden();
        int codigoPaciente = Integer.parseInt(examen.getCodigoPaciente());
        String codigoMedico = examen.getCodigoMedico();
        int codigoTipoExamen = Integer.parseInt(examen.getCodigoTipoExamen());
        
        try(PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, codigoExamen);
            ps.setDate(2, fechaExamen);
            ps.setTime(3, horaExamen);
            ps.setBoolean(4, requiereOrden);
            ps.setInt(5, codigoPaciente);
            ps.setString(6, codigoMedico);
            ps.setInt(7, codigoTipoExamen);

            //Cannot add or update a child row
            ps.execute();
            
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
