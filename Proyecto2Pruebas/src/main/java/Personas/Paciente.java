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
public class Paciente extends Persona {
    
    private String sexo;
    private Date fechaNacimiento;
    private String peso;
    private String tipoSangre;

    public Paciente(String sexo, Date fechaNacimiento, String peso, String tipoSangre,
            String codigo, String nombre, String DPI, String contraseña,
            String telefono, String correoElectronico) {
        super(codigo, nombre, DPI, contraseña, telefono, correoElectronico);
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
        this.tipoSangre = tipoSangre;
    }

    public Paciente(String sexo, Date fechaNacimiento, String peso, String tipoSangre,
            String nombre, String DPI, String contraseña,
            String telefono, String correoElectronico) {
        super(nombre, DPI, contraseña, telefono, correoElectronico);
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
        this.tipoSangre = tipoSangre;
    }
    
    

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    


    
    
    
}
