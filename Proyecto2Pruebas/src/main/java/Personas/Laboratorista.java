/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Personas;

import java.sql.Date;

/**
 * 
 * @author Joel Enriquez
 */
public class Laboratorista extends Persona {

    private Date fechaInicioHospital;
    private int codigoTipoExamen;
    private String registroSalud;

    public Laboratorista(Date fechaInicioHospital, int codigoTipoExamen,
            String registroSalud,String codigo, String nombre, String DPI,
            String contraseña, String telefono, String correoElectronico) {
        super(codigo, nombre, DPI, contraseña, telefono, correoElectronico);
        this.fechaInicioHospital = fechaInicioHospital;
        this.codigoTipoExamen = codigoTipoExamen;
        this.registroSalud = registroSalud;
    }

    public Date getFechaInicioHospital() {
        return fechaInicioHospital;
    }

    public void setFechaInicioHospital(Date fechaInicioHospital) {
        this.fechaInicioHospital = fechaInicioHospital;
    }

    public int getCodigoTipoExamen() {
        return codigoTipoExamen;
    }

    public void setCodigoTipoExamen(int codigoTipoExamen) {
        this.codigoTipoExamen = codigoTipoExamen;
    }

    public String getRegistroSalud() {
        return registroSalud;
    }

    public void setRegistroSalud(String registroSalud) {
        this.registroSalud = registroSalud;
    }

}
