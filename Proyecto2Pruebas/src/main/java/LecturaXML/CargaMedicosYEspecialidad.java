/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LecturaXML;

import ConexionDB.ConsultasSubidaXML;
import Encriptar.Encriptacion;
import EntidadesAsignacion.AsignacionEspecialidad;
import Personas.Medico;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Element;

/**
 *
 * @author joel
 */
public class CargaMedicosYEspecialidad {

    private Connection conexion;
    private ConsultasSubidaXML consultasXML;
    private Encriptacion encriptacion;
    private List<Element> listaMedicos;

    public CargaMedicosYEspecialidad(Connection conexion, Encriptacion encriptacion,
            List<Element> listaMedicos) {
        this.conexion = conexion;
        this.encriptacion = encriptacion;
        this.listaMedicos = listaMedicos;
        consultasXML = new ConsultasSubidaXML();
    }

    public void ejecutarCarga() throws Exception {
        for (int i = 0; i < listaMedicos.size(); i++) {
            Element medico = listaMedicos.get(i);

            String codigo = medico.getChildTextTrim("CODIGO");
            String nombre = medico.getChildTextTrim("NOMBRE");
            String numeroColegiado = medico.getChildTextTrim("COLEGIADO");
            String DPI = medico.getChildTextTrim("DPI");
            String numeroTelefono = medico.getChildTextTrim("TELEFONO");

            //Para guardar todos los titulos del medico
            ArrayList<String> listadoTitulos = new ArrayList<>();
            //Obtener todas las especialidades del medico
            List<Element> listaEspecialidades = medico.getChildren("ESPECIALIDAD");
            List<Element> listaTitulos = ((Element) listaEspecialidades.get(0)).getChildren("TITULO");
            for (int k = 0; k < listaTitulos.size(); k++) {
                Element titulo = listaTitulos.get(k);
                listadoTitulos.add(titulo.getValue());
            }

            String correoElectronico = medico.getChildTextTrim("CORREO");

            //Obtener ambos horarios del medico
            List<Element> listaHorarios = medico.getChildren("HORARIO");            
            SimpleDateFormat format = new SimpleDateFormat("HH:mm"); // 12 hour format
            
            String horarioInicio = ((Element) listaHorarios.get(0)).getChildTextTrim("INICIO");
            java.util.Date fHora1 = (java.util.Date)format.parse(horarioInicio);
            Time horaInicio = new Time(fHora1.getTime());
            
            String horarioFin = ((Element) listaHorarios.get(0)).getChildTextTrim("FIN");
            java.util.Date fHora2 = (java.util.Date)format.parse(horarioFin);
            Time horaFin = new Time(fHora2.getTime());
            
            Date fechaInicioTrabajo = Date.valueOf(medico.getChildTextTrim("TRABAJO"));
            String password = medico.getChildTextTrim("PASSWORD");
            String encryptPass = encriptacion.encriptar(password);

            Medico newMedico = new Medico(horaInicio, horaFin, numeroColegiado, fechaInicioTrabajo,
                codigo, nombre, DPI, encryptPass, numeroTelefono, correoElectronico);
            agregarMedico(newMedico);
            
            //Asignamos especialidades
            for (int j = 0; j < listadoTitulos.size(); j++) {
                String nombreTitulo = listadoTitulos.get(j);
                AsignacionEspecialidad newAsignacion =
                new AsignacionEspecialidad(consultasXML.idEspecialidad(nombreTitulo,conexion), codigo);
                agregarAsignacionEspecialidad(newAsignacion);
            }
        }
    }

    private void agregarMedico(Medico medico) {
        String query = "INSERT INTO MEDICO VALUES (?,?,?,?,?,?,?,?,?,?)";

        String codigoMedico = medico.getCodigo();
        String nombre = medico.getNombre();
        String numeroColegiado = medico.getNumeroColegiado();
        Time horarioInicio = medico.getHoraInicio();
        Time horarioFin = medico.getHoraFin();
        String DPI = medico.getDPI();
        String telefono = medico.getTelefono();
        String correoElectronico = medico.getCorreoElectronico();
        Date fechaInicioHospital = medico.getFechaInicioHospital();
        String password = medico.getContraseña();

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, codigoMedico);
            ps.setString(2, nombre);
            ps.setString(3, numeroColegiado);
            ps.setTime(4, horarioInicio);
            ps.setTime(5, horarioFin);
            ps.setString(6, DPI);
            ps.setString(7, telefono);
            ps.setString(8, correoElectronico);
            ps.setDate(9, fechaInicioHospital);
            ps.setString(10, password);

            ps.execute();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void agregarAsignacionEspecialidad(AsignacionEspecialidad asigEspecialidad) {
        String query = "INSERT INTO ASIGNACION_ESPECIALIDAD VALUES (?,?)";

        String codigoMedico = asigEspecialidad.getCodigoMedico();
        String idEspecialidad = asigEspecialidad.getIdEspecialidad();

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, codigoMedico);
            ps.setInt(2, Integer.parseInt(idEspecialidad));

            ps.execute();
        } catch (Exception e) {
            e.getMessage();
        }
    }

}
