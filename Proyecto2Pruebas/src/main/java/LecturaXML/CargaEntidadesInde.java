/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LecturaXML;

import Encriptar.Encriptacion;
import EntidadesAsignacion.Especialidad;
import Personas.Administrador;
import Personas.Paciente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import org.jdom2.Element;

/**
 *
 * @author joel
 */
public class CargaEntidadesInde {

    private Connection conexion;
    private Encriptacion encriptacion;
    private List<Element> listaAdmins;
    private List<Element> listaPacientes;
    private List<Element> listaConsultas;

    public CargaEntidadesInde(Connection conexion,Encriptacion encriptacion,
            List<Element> listaAdmins, List<Element> listaPacientes, List<Element> listaConsultas) {
        this.conexion = conexion;
        this.encriptacion = encriptacion;
        this.listaAdmins = listaAdmins;
        this.listaPacientes = listaPacientes;
        this.listaConsultas = listaConsultas;
    }

    public void ejecutarCarga() throws Exception {
        capturaDatosAdmin();
        capturaDatosPaciente();
        capturaDatosEspecialidad();
    }

    private void capturaDatosAdmin() throws Exception {
        for (int i = 0; i < listaAdmins.size(); i++) {
            Element admin = listaAdmins.get(i);
            
            String codigo = admin.getChildTextTrim("CODIGO");
            String DPI = admin.getChildTextTrim("DPI");
            String nombre = admin.getChildTextTrim("NOMBRE");
            String password = admin.getChildTextTrim("PASSWORD");
            String encryptPass = encriptacion.encriptar(password);

            Administrador newAdmin = new Administrador(codigo, nombre, DPI, encryptPass);
            insertarAdmins(newAdmin);
        }
    }

    private void capturaDatosPaciente() throws Exception {
        for (int i = 0; i < listaPacientes.size(); i++) {
            Element paciente = listaPacientes.get(i);
            
            String codigoPaciente = paciente.getChildTextTrim("CODIGO");
            String nombre = paciente.getChildTextTrim("NOMBRE");
            String sexo = paciente.getChildTextTrim("SEXO");
            Date fechaNacimiento = Date.valueOf(paciente.getChildTextTrim("BIRTH"));
            String DPI = paciente.getChildTextTrim("DPI");
            String telefono = paciente.getChildTextTrim("TELEFONO");
            String peso = paciente.getChildTextTrim("PESO");
            String tipoSangre = paciente.getChildTextTrim("SANGRE");
            String correoElectronico = paciente.getChildTextTrim("CORREO");
            String password = paciente.getChildTextTrim("PASSWORD");
            String encryptPass = encriptacion.encriptar(password);
            
            Paciente newPaciente = new Paciente(sexo, fechaNacimiento, peso, tipoSangre,
                    codigoPaciente, nombre, DPI, encryptPass, telefono, correoElectronico);
            insertarPacientes(newPaciente);
        }
    }

    private void capturaDatosEspecialidad() {
        for (int i = 0; i < listaConsultas.size(); i++) {
            Element consulta = listaConsultas.get(i);
            
            String nombreEspecialiad = consulta.getChildTextTrim("TIPO");
            double costoConsulta = Double.parseDouble(consulta.getChildTextTrim("COSTO"));
            
            Especialidad newEspecialidad = new Especialidad(nombreEspecialiad, costoConsulta);
            insertarEspecialidades(newEspecialidad);
        }
    }

    private void insertarAdmins(Administrador admin) {
        String query = "INSERT INTO ADMINISTRADOR VALUES (?,?,?,?)";

        String codigoAdmin = admin.getCodigo();
        String DPI = admin.getDPI();
        String nombre = admin.getNombre();
        String password = admin.getContraseña();

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, codigoAdmin);
            ps.setString(2, nombre);
            ps.setString(3, DPI);
            ps.setString(4, password);
            ps.execute();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void insertarPacientes(Paciente paciente) {
        String query = "INSERT INTO PACIENTE VALUES (?,?,?,?,?,?,?,?,?,?)";

        String codigoPaciente = paciente.getCodigo();
        String nombre = paciente.getNombre();
        String sexo = paciente.getSexo();
        Date fechaNacimiento = paciente.getFechaNacimiento();
        String DPI = paciente.getDPI();
        String telefono = paciente.getTelefono();
        String peso = paciente.getPeso();
        String tipoSangre = paciente.getTipoSangre();
        String correoElectronico = paciente.getCorreoElectronico();
        String password = paciente.getContraseña();

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, Integer.parseInt(codigoPaciente));
            ps.setString(2, nombre);
            ps.setString(3, sexo);
            ps.setDate(4, fechaNacimiento);
            ps.setString(5, DPI);
            ps.setString(6, telefono);
            ps.setString(7, peso);
            ps.setString(8, tipoSangre);
            ps.setString(9, correoElectronico);
            ps.setString(10, password);

            ps.execute();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void insertarEspecialidades(Especialidad especialidad) {
        String query = "INSERT INTO ESPECIALIDAD (nombre, costo_consulta) VALUES (?,?)";

        String nombreEspecialidad = especialidad.getNombre();
        double costoConsulta = especialidad.getCostoConsulta();

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, nombreEspecialidad);
            ps.setDouble(2, costoConsulta);
            
            ps.execute();
        } catch (Exception e) {
            e.getMessage();
        }
    }

}
