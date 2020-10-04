/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package EntidadesAsignacion;

/**
 * 
 * @author Joel Enriquez
 */
public class Especialidad {
    private String id;
    private String nombre;
    private double costoConsulta;

    public Especialidad(String nombre, double costoConsulta){
        this.nombre = nombre;
        this.costoConsulta = costoConsulta;
    }

    public Especialidad(String id, String nombre, double costoConsulta) {
        this.id = id;
        this.nombre = nombre;
        this.costoConsulta = costoConsulta;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCostoConsulta() {
        return costoConsulta;
    }

    public void setCostoConsulta(double costoConsulta) {
        this.costoConsulta = costoConsulta;
    }
    
    
}
