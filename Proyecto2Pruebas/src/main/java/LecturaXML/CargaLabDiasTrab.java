/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LecturaXML;

import ConexionDB.ConsultasSubidaXML;
import Encriptar.Encriptacion;
import EntidadesAsignacion.AsignarDiasLab;
import EntidadesHospital.TipoExamen;
import Personas.Laboratorista;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Element;

/**
 *
 * @author joel
 */
public class CargaLabDiasTrab {

    private Connection conexion;
    private Encriptacion encriptacion;
    private CargaResultadosExamen cargaResultsExamen;
    private ConsultasSubidaXML consultasXML;
    private List<Element> listaLaboratoristas;
    private List<Element> listaExamenes;
    private ArrayList<Integer> codigosExamenConOrden;

    public CargaLabDiasTrab(Connection conexion, Encriptacion encriptacion, List<Element> listaLaboratoristas, List<Element> listaExamenes, List<Element> listaResultados) {
        this.conexion = conexion;
        this.encriptacion = encriptacion;
        this.listaLaboratoristas = listaLaboratoristas;
        this.listaExamenes = listaExamenes;
        codigosExamenConOrden = new ArrayList<>();
        cargaResultsExamen = new CargaResultadosExamen(conexion,listaResultados);
        consultasXML = new ConsultasSubidaXML();
    }

    public void ejecutarCarga() throws Exception {
        agregarTiposExamen();
        agregarLaboratoristaYDiasTrabajo();
        cargaResultsExamen.agregarExamenesYResultados();
    }

    private void agregarTiposExamen() {
        for (int i = 0; i < listaExamenes.size(); i++) {
            Element tipoExamen = listaExamenes.get(i);

            int codigoTipoExamen = Integer.parseInt(tipoExamen.getChildTextTrim("CODIGO"));
            String nombreExamen = tipoExamen.getChildTextTrim("NOMBRE");
            boolean requiereOrden = Boolean.parseBoolean(tipoExamen.getChildTextTrim("ORDEN"));
            if (requiereOrden) {
                if (!codigosExamenConOrden.contains(codigoTipoExamen)) {
                    codigosExamenConOrden.add(codigoTipoExamen);
                }
            }
            String descripcionExamen = tipoExamen.getChildTextTrim("DESCRIPCION");
            double costoExamen = Double.parseDouble(tipoExamen.getChildTextTrim("COSTO"));
            String formatoInforme = tipoExamen.getChildTextTrim("INFORME");

            TipoExamen tipoExam = new TipoExamen(String.valueOf(codigoTipoExamen), nombreExamen,
                    descripcionExamen, costoExamen, formatoInforme);
            cargaResultsExamen.agregarTipoExamen(tipoExam);
        }
        //Devolver el codigo de examen que tenga orden
        cargaResultsExamen.setCodigosExamenConOrden(codigosExamenConOrden);
    }

    private void agregarLaboratoristaYDiasTrabajo() throws Exception {
        for (int i = 0; i < listaLaboratoristas.size(); i++) {
            Element lab = listaLaboratoristas.get(i);

            String codigoLab = lab.getChildTextTrim("CODIGO");
            String nombreLab = lab.getChildTextTrim("NOMBRE");
            String registroSalud = lab.getChildTextTrim("REGISTRO");
            String DPI = lab.getChildTextTrim("DPI");
            String telefono = lab.getChildTextTrim("TELEFONO");
            int codigoTipoExamen = consultasXML.obtenerCodigoTipoExamenPorNombre(lab.getChildTextTrim("EXAMEN"), conexion);
            String correoElectronico = lab.getChildTextTrim("CORREO");

            //Guardar los dias que trabaja
            ArrayList<String> listaDias = new ArrayList<>();
            List<Element> listadoDias = lab.getChildren("TRABAJO"); //Guardan las etiquetas dias
            List<Element> dias = ((Element) listadoDias.get(0)).getChildren("DIA"); //Obtiene lo que este adentro de dias
            for (int j = 0; j < dias.size(); j++) {
                Element diaTrabajo = dias.get(j);
                listaDias.add(diaTrabajo.getValue());
            }
            //Depende del formato de la carga de archivo
            Date fechaInicioHospital = Date.valueOf(lab.getChildTextTrim("TRABAJOF"));
            String contraseña = encriptacion.encriptar(lab.getChildTextTrim("PASSWORD"));

            Laboratorista newLaboratorista = new Laboratorista(fechaInicioHospital, codigoTipoExamen,
                    registroSalud, codigoLab, nombreLab, DPI, contraseña, telefono, correoElectronico);
            agregarLaboratorista(newLaboratorista);

            AsignarDiasLab asignarDias = new AsignarDiasLab(codigoLab);
            //Asignar los dias de trabajo
            if (listaDias.contains("Lunes")) {
                asignarDias.setLunes(true);
            }if (listaDias.contains("Martes")) {
                asignarDias.setMartes(true);
            }if (listaDias.contains("Miercoles")) {
                asignarDias.setMiercoles(true);
            }if (listaDias.contains("Jueves")) {
                asignarDias.setJueves(true);
            }if (listaDias.contains("Viernes")) {
                asignarDias.setViernes(true);
            }if (listaDias.contains("Sabado")) {
                asignarDias.setSabado(true);
            }if (listaDias.contains("Domingo")) {
                asignarDias.setDomingo(true);
            }
            agregarDiasTrabajo(asignarDias);
        }
    }

    private void agregarLaboratorista(Laboratorista laboratorista) {
        String query = "INSERT INTO LABORATORISTA VALUES (?,?,?,?,?,?,?,?,?)";

        String codigoMedico = laboratorista.getCodigo();
        String nombre = laboratorista.getNombre();
        String registro = laboratorista.getRegistroSalud();
        String DPI = laboratorista.getDPI();
        String telefono = laboratorista.getTelefono();
        String correoElectronico = laboratorista.getCorreoElectronico();
        Date fechaInicioHospital = laboratorista.getFechaInicioHospital();
        String password = laboratorista.getContraseña();
        int codigoTipoExamen = laboratorista.getCodigoTipoExamen();

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, codigoMedico);
            ps.setString(2, nombre);
            ps.setString(3, registro);
            ps.setString(4, DPI);
            ps.setString(5, telefono);
            ps.setString(6, correoElectronico);
            ps.setDate(7, fechaInicioHospital);
            ps.setString(8, password);
            ps.setInt(9, codigoTipoExamen);

            ps.execute();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace(System.out);
        }
    }

    private void agregarDiasTrabajo(AsignarDiasLab asignarDLab) {
        String query = "INSERT INTO ASIGNAR_DIAS_LAB VALUES (?,?,?,?,?,?,?,?)";

        String codigoLaboratorista = asignarDLab.getCodigoLaboratorista();
        boolean lunes = asignarDLab.isLunes();
        boolean martes = asignarDLab.isMartes();
        boolean miercoles = asignarDLab.isMiercoles();
        boolean jueves = asignarDLab.isJueves();
        boolean viernes = asignarDLab.isViernes();
        boolean sabado = asignarDLab.isSabado();
        boolean domingo = asignarDLab.isDomingo();

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, codigoLaboratorista);
            ps.setBoolean(2, lunes);
            ps.setBoolean(3, martes);
            ps.setBoolean(4, miercoles);
            ps.setBoolean(5, jueves);
            ps.setBoolean(6, viernes);
            ps.setBoolean(7, sabado);
            ps.setBoolean(8, domingo);

            ps.execute();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace(System.out);
        }
    }
}
