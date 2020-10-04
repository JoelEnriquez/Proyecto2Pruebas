/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConexionDB;

import EntidadesAsignacion.Especialidad;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;

/**
 *
 * @author joel
 */
public class ConsultasSubidaXML {
    
    public Especialidad getEspecialidadPorNombre(String nombreEspecialidad, Connection conexion){
        String query = "SELECT id, costo_consulta FROM ESPECIALIDAD WHERE nombre = ?";
        Especialidad especialidad = null;
        
        try (PreparedStatement ps = conexion.prepareStatement(query)) {         
            ps.setString(1, nombreEspecialidad);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String idEspecialiad = rs.getString(1);
                    double costoConsulta = rs.getDouble(2);
                    
                    especialidad = new Especialidad(idEspecialiad, nombreEspecialidad, costoConsulta);
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        
        return especialidad;
    }
    
    public String getCodigoCita(Date fechaC, Time horaC, String medicoC, Connection conexion){
        String query = "SELECT codigo FROM CITA_MEDICO WHERE "
                + "fecha = ? AND hora = ? AND codigo_medico = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(query)) {         
            ps.setDate(1, fechaC);
            ps.setTime(2, horaC);
            ps.setString(3, medicoC);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return "";
    }
    
    public String idEspecialidad(String nombreEspecialidad, Connection conexion){
        String query = "SELECT id FROM ESPECIALIDAD WHERE nombre=?";
        String id = "";
        
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, nombreEspecialidad);
            
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    id = String.valueOf(rs.getInt("id"));
                }
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return id;
    }
    
    public int obtenerCodigoTipoExamenPorNombre(String nombreExamen, Connection conexion){
        String query = "SELECT codigo FROM TIPO_EXAMEN WHERE nombre_examen=?";
        
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, nombreExamen);
            
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return 0;
    }
}
