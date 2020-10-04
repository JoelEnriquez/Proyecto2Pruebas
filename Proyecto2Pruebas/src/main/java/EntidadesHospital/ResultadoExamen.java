/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package EntidadesHospital;

import java.io.InputStream;
import java.sql.Date;
import java.sql.Time;

/**
 * 
 * @author Joel Enriquez
 */
public class ResultadoExamen {
    private String codigo;
    private InputStream orden;
    private InputStream informe;
    private Time horaExamen;
    private Date fechaExamen;
    private String codigoPaciente;
    private String codigoMedico;
    private String codigoLaboratorista;
    private int codigoExamen;

    public ResultadoExamen(String codigo, InputStream orden, InputStream informe,
            Time horaExamen, Date fechaExamen, String codigoPaciente,
            String codigoMedico, String codigoLaboratorista, int codigoExamen) {
        this.codigo = codigo;
        this.orden = orden;
        this.informe = informe;
        this.horaExamen = horaExamen;
        this.fechaExamen = fechaExamen;
        this.codigoPaciente = codigoPaciente;
        this.codigoMedico = codigoMedico;
        this.codigoLaboratorista = codigoLaboratorista;
        this.codigoExamen = codigoExamen;
    }
    
    public ResultadoExamen(InputStream orden, InputStream informe,
            Time horaExamen, Date fechaExamen, String codigoPaciente,
            String codigoMedico, String codigoLaboratorista, int codigoExamen) {
        this.orden = orden;
        this.informe = informe;
        this.horaExamen = horaExamen;
        this.fechaExamen = fechaExamen;
        this.codigoPaciente = codigoPaciente;
        this.codigoMedico = codigoMedico;
        this.codigoLaboratorista = codigoLaboratorista;
        this.codigoExamen = codigoExamen;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public InputStream getOrden() {
        return orden;
    }

    public void setOrden(InputStream orden) {
        this.orden = orden;
    }

    public InputStream getInforme() {
        return informe;
    }

    public void setInforme(InputStream informe) {
        this.informe = informe;
    }

    public Time getHoraExamen() {
        return horaExamen;
    }

    public void setHoraExamen(Time horaExamen) {
        this.horaExamen = horaExamen;
    }

    public Date getFechaExamen() {
        return fechaExamen;
    }

    public void setFechaExamen(Date fechaExamen) {
        this.fechaExamen = fechaExamen;
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

    public int getCodigoExamen() {
        return codigoExamen;
    }

    public void setCodigoExamen(int codigoExamen) {
        this.codigoExamen = codigoExamen;
    }

    public String getCodigoLaboratorista() {
        return codigoLaboratorista;
    }

    public void setCodigoLaboratorista(String codigoLaboratorista) {
        this.codigoLaboratorista = codigoLaboratorista;
    }
    
    
    
    
}
