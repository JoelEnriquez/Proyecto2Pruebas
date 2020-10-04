/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LecturaXML;

import ConexionDB.ConsultasSubidaXML;
import EntidadesAsignacion.Especialidad;
import EntidadesHospital.CitaMedico;
import EntidadesHospital.InformeMedico;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.jdom2.Element;

/**
 *
 * @author joel
 */
public class CargaReporteYCitas {
    
    private Connection conexion;
    private ConsultasSubidaXML consultasXML;
    private List<Element> listaReportes;
    private List<Element> listaCitas;

    public CargaReporteYCitas(Connection conexion, List<Element> listaReportes,
            List<Element> listaCitas) {
        this.conexion = conexion;
        this.listaReportes = listaReportes;
        this.listaCitas = listaCitas;
        this.consultasXML = new ConsultasSubidaXML();
    }
    
    public void ejecutarCarga() throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("HH:mm"); // 12 hour format
        
        for (int i = 0; i < listaCitas.size(); i++) {
            Element cita = listaCitas.get(i);

            String codigoCita = cita.getChildTextTrim("CODIGO");
            String codigoPaciente =  cita.getChildTextTrim("PACIENTE");
            String codigoMedico =  cita.getChildTextTrim("MEDICO");        
            //Obtener los atributos de especialidad que van a estar en cita
            String especialidadCita = cita.getChildTextTrim("ESPECIALIDAD");
            Especialidad espCita = consultasXML.getEspecialidadPorNombre(especialidadCita,conexion);
            
            Date fechaCita = Date.valueOf(cita.getChildTextTrim("FECHA"));           
            //Convertir a formato aceptado            
            String horaC = cita.getChildTextTrim("HORA");
            //Pasarlo a fecha, para luego obtener la hora de dicho dia
            java.util.Date fCita = (java.util.Date)format.parse(horaC);
            Time horaCita = new Time(fCita.getTime());
            
            CitaMedico newCita = new CitaMedico(codigoCita, codigoPaciente, codigoMedico,
                especialidadCita,espCita.getId(), espCita.getCostoConsulta(), fechaCita, horaCita);
            agregarCita(newCita);      
        }
        
        for (int i = 0; i < listaReportes.size(); i++) {
            Element reporte = listaReportes.get(i);
            
            String codigoReporte = reporte.getChildTextTrim("CODIGO");
            String codigoPaciente = reporte.getChildTextTrim("PACIENTE");
            String codigoMedico =  reporte.getChildTextTrim("MEDICO");
            String descripcionReporte =  reporte.getChildTextTrim("INFORME");
            Date fechaInforme = Date.valueOf(reporte.getChildTextTrim("FECHA"));
            //Poner la hora en un formato correcto
            String horaReporte = reporte.getChildTextTrim("HORA");
            java.util.Date fReporte = (java.util.Date)format.parse(horaReporte);
            Time horaInforme = new Time(fReporte.getTime());
            
            String codigoCita = consultasXML.getCodigoCita(fechaInforme, horaInforme, codigoMedico, conexion);
            
            InformeMedico informeMedico = new InformeMedico(codigoReporte, fechaInforme, horaInforme,
                    descripcionReporte, codigoPaciente, codigoMedico, codigoCita);
            agregarInforme(informeMedico);
        }
    }
    
    private void agregarCita(CitaMedico cita) {
        String query = "INSERT INTO CITA_MEDICO VALUES (?,?,?,?,?,?,?,?)";

        String codigoCita = cita.getCodigo();
        Date fechaCita = cita.getFechaCita();
        Time horaCita = cita.getHoraCita();
        int codigoPaciente = Integer.parseInt(cita.getCodigoPaciente());
        String codigoMedico = cita.getCodigoMedico();
        int idEspecialidad = Integer.parseInt(cita.getIdEspecialidad());
        String nombreEspeciadlidad = cita.getNombreEspecialidad();
        double costoConsulta = cita.getCostoConsulta();

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, Integer.parseInt(codigoCita));
            ps.setDate(2, fechaCita);
            ps.setTime(3, horaCita);
            ps.setInt(4, codigoPaciente);
            ps.setString(5, codigoMedico);
            ps.setInt(6, idEspecialidad);
            ps.setString(7, nombreEspeciadlidad);
            ps.setDouble(8, costoConsulta);

            ps.execute();
        } catch (Exception e) {
            //e.getMessage();
            e.printStackTrace(System.out);
        }
    }
    
    private void agregarInforme(InformeMedico informe){
        String query = "INSERT INTO INFORME_MEDICO VALUES (?,?,?,?,?,?,?)";

        String codigoInforme = informe.getCodigo();
        Date fechaInforme = informe.getFechaInforme();
        Time horaInforme = informe.getHoraInforme();
        String descripcion = informe.getDescripcionInforme();
        int codigoPaciente = Integer.parseInt(informe.getCodigoPaciente());
        String codigoMedico = informe.getCodigoMedico();
        int codigoCitaMedico = Integer.parseInt(informe.getCodigoCitaMedico());

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, codigoInforme);
            ps.setDate(2, fechaInforme);
            ps.setTime(3, horaInforme);
            ps.setString(4, descripcion);
            ps.setInt(5, codigoPaciente);
            ps.setString(6, codigoMedico);
            ps.setInt(7, codigoCitaMedico);          

            ps.execute();
        } catch (Exception e) {
            e.getMessage();
        }
    }

}
