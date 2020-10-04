/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Personas;

/**
 * 
 * @author Joel Enriquez
 */
public class Persona {
    protected String codigo;
    protected String nombre;
    protected String DPI;
    protected String contraseña;
    protected String telefono;
    protected String correoElectronico;

    //Constructor Administrador
    public Persona(String codigo, String nombre, String DPI, String contraseña) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.DPI = DPI;
        this.contraseña = contraseña;
    }

    //Constructor Paciente, Medico, Laboratorista
    public Persona(String codigo, String nombre, String DPI, String contraseña, String telefono, String correoElectronico) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.DPI = DPI;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
    }

    //Constructor paciente sin codigo
    public Persona(String nombre, String DPI, String contraseña, String telefono, String correoElectronico) {
        this.nombre = nombre;
        this.DPI = DPI;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
    }
    
    

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDPI() {
        return DPI;
    }

    public void setDPI(String DPI) {
        this.DPI = DPI;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    
}
