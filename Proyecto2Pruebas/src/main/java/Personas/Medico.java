/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Personas;

import java.sql.Date;
import java.sql.Time;

/**
 * 
 * @author Joel Enriquez
 */
public class Medico extends Persona {

    private Time horaInicio;
    private Time horaFin;
    private String numeroColegiado;
    private Date fechaInicioHospital;

    public Medico(Time horaInicio, Time horaFin, String numeroColegiado,
            Date fechaInicioHospital, String codigo, String nombre,String DPI,
            String contraseña, String telefono, String correoElectronico) {
        super(codigo, nombre, DPI, contraseña, telefono, correoElectronico);
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.numeroColegiado = numeroColegiado;
        this.fechaInicioHospital = fechaInicioHospital;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    public String getNumeroColegiado() {
        return numeroColegiado;
    }

    public void setNumeroColegiado(String numeroColegiado) {
        this.numeroColegiado = numeroColegiado;
    }

    public Date getFechaInicioHospital() {
        return fechaInicioHospital;
    }

    public void setFechaInicioHospital(Date fechaInicioHospital) {
        this.fechaInicioHospital = fechaInicioHospital;
    }

}
