/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package EntidadesHospital;

import java.sql.Date;
import java.sql.Time;

/**
 * 
 * @author Joel Enriquez
 */
public class CitaMedico {
    private String codigo;
    private String codigoPaciente;
    private String codigoMedico;
    private String nombreEspecialidad;
    private String idEspecialidad;
    private double costoConsulta;
    private Date fechaCita;
    private Time horaCita;

    public CitaMedico(String codigoPaciente, String codigoMedico, String nombreEspecialidad,
            String idEspecialidad, double costoConsulta, Date fechaCita, Time horaCita) {
        this.codigoPaciente = codigoPaciente;
        this.codigoMedico = codigoMedico;
        this.nombreEspecialidad = nombreEspecialidad;
        this.idEspecialidad = idEspecialidad;
        this.costoConsulta = costoConsulta;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
    }

    public CitaMedico(String codigo, String codigoPaciente, String codigoMedico,
            String nombreEspecialidad, String idEspecialidad, double costoConsulta,
            Date fechaCita, Time horaCita) {
        this.codigo = codigo;
        this.codigoPaciente = codigoPaciente;
        this.codigoMedico = codigoMedico;
        this.nombreEspecialidad = nombreEspecialidad;
        this.idEspecialidad = idEspecialidad;
        this.costoConsulta = costoConsulta;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
    }

    
    public double getCostoConsulta() {
        return costoConsulta;
    }

    public void setCostoConsulta(double costoConsulta) {
        this.costoConsulta = costoConsulta;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoPaciente() {
        return codigoPaciente;
    }

    public void setCodigoPaciente(String codigoPaciente) {
        this.codigoPaciente = codigoPaciente;
    }

    public String getCodigoMedico() {
        return codigoMedico;
    }

    public void setCodigoMedico(String codigoMedico) {
        this.codigoMedico = codigoMedico;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public Time getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(Time horaCita) {
        this.horaCita = horaCita;
    }

    public String getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(String idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }
    
    
}
