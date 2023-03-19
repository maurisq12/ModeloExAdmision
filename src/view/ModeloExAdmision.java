/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controlador;
import controller.DAO.SingletonDAO;
import controller.DTOFormulario;
import controller.IParametros;
import model.Configuracion;
import model.DireccionPCD;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ersolano
 */
public class ModeloExAdmision {

    /**
     * @param args the command line arguments
     */
    
    public static Controlador elCtrl = new Controlador();
    
    
    public static void demoFormulario(){
    int idSolic = 1000;
    String nombreSolic ="Solicitante 1000";
    String correoSolic ="correo1000@gmail.com";
    String celularSolic ="8123-4567";
    String colegioSolic ="Colegio del Solicitante 1000";
    DireccionPCD dirSolic = SingletonDAO.getInstance().getPCD(4); 
    String detalleDir = "Cerquita del Morazán";
    String carreraSolic = "IC";
    String sedeSolic = "SJ";
    
    DTOFormulario elDTO = new  DTOFormulario(idSolic, nombreSolic, 
                                correoSolic, celularSolic, colegioSolic, 
                                dirSolic, detalleDir, carreraSolic, sedeSolic);
    
    boolean resultado = elCtrl.registrarFormulario(elDTO);
    System.out.println( resultado   ? "Formulario registrado y es el numero: "+
            elCtrl.getFormulario(idSolic): "No pudo registrar el formulario");
    }
    
    public static void demoCarreras(){
        System.out.println("Visualizar todas las carreras de la institucion");
        System.out.println(elCtrl.getCarreras());
        
        String unaSede = "SJ";
        String unaCarrera = "IC";
        int nuevoPuntajeCarrera= 600;
        int nuevaAdmisionCarrera = 100;
        
        System.out.println("Modificar puntaje de admision a " + 
                            nuevoPuntajeCarrera +" de una carrera particular " + 
                           unaSede+"-"+unaCarrera );
        boolean resultado = elCtrl.editarPuntajeMinimoAdmision(unaCarrera, unaSede, nuevoPuntajeCarrera);
        System.out.println( resultado ? "Puntaje minimo modificado" : "No encontro la carrera para cambio de puntaje");
                
        System.out.println("Modificar capacidad de admision a " +
                            nuevaAdmisionCarrera +" de una carrera particular " + 
                           unaSede+"-"+unaCarrera );
        resultado = elCtrl.editarCapacidadAdmision(unaCarrera, unaSede, nuevaAdmisionCarrera);
        System.out.println( resultado ? "Capacidad de admisión modificada" : "No encontro la carrera para cambio de capacidad de admision");

        System.out.println("visualizar las carreras de la sede "+ unaSede);
        System.out.println(elCtrl.getCarrerasPorSede(unaSede));
        
    }
    
    public static void demoConfiguracion(){
        int nuevoPuntaje = 900;
         System.out.println("Obteniendo Puntaje General de Admisión actual" +
                            elCtrl.getPuntajeGeneralAdmision()); 
         
         System.out.println("Editando Puntaje General de Admisión ");
        elCtrl.editarPuntajeGeneralAdmision( nuevoPuntaje );
        
        System.out.println("Obteniendo Puntaje General de Admisión " +
                            elCtrl.getPuntajeGeneralAdmision());        
        
        System.out.println("Obteniendo parámetros de forma generica"); 
        System.out.println(Configuracion.getInstance().getParam(IParametros.MAXIMO_PUNTAJE, Integer.class));
        System.out.println(Configuracion.getInstance().getParam(IParametros.ADMIN_USR, String.class));
        System.out.println(Configuracion.getInstance().getParam(IParametros.ADMIN_PWD, String.class));
        System.out.println(Configuracion.getInstance().getParam("INTENTOS", Integer.class));
        System.out.println(Configuracion.getInstance().getParam("CONSECUTIVO_FORM", Integer.class));
        
        System.out.println("Guardando Configuración...");
        elCtrl.guardarConfiguracion();
       Configuracion.getInstance().guardarProperties();
    }
    
    public static void generacionFormularios(int pCant){
        int contador = 1;
        Random r1 = new Random();
        Random r2 = new Random();
        for(int i=0;i<pCant;i++){
            List<String> carreras = new ArrayList(Arrays.asList("IC","PI","AE"));
            List<String> sedes = new ArrayList(Arrays.asList("CA","SJ","LI","SC","AL"));
            
            String carreraR=carreras.get(r1.nextInt(4-1) + 0);
            String sedeR=sedes.get(r2.nextInt(6-1) + 0);

            String nombreSolic ="Solicitante "+contador;
            String correoSolic ="correo"+contador+"@gmail.com";
            String celularSolic ="8123-4567"+contador;
            String colegioSolic ="Colegio del Solicitante "+contador;
            DireccionPCD dirSolic = SingletonDAO.getInstance().getPCD(2); 
            String detalleDir = "Cerquita del Morazán";
            String carreraSolic = carreraR;
            String sedeSolic = sedeR;

            DTOFormulario elDTO = new  DTOFormulario(contador, nombreSolic, 
                                        correoSolic, celularSolic, colegioSolic, 
                                        dirSolic, detalleDir, carreraSolic, sedeSolic);

            elCtrl.registrarFormulario(elDTO);
            contador++;
        }
        
    }
        
        
        
    
    
    public static void demoCitas(){
        System.out.println("Generando citas...");
        elCtrl.generarCitas();        
    }
    
    public static void demoSimulacionExamen(){
        System.out.println("Simulando examen...");
        elCtrl.simulacionAplicacionExamen();
    }
    
    public static void demoEstados(){
        System.out.println("Simulando estados de admision de candidatos...");
        elCtrl.definirSituacionCandidatos();
    }
    
    public static void demoVisualizacionFormulario(int pIdSolicitante){
        System.out.println("Solicitud para un formulario específico");
        elCtrl.getDesgloseFormulario(pIdSolicitante);        
    }
    
    

    
    public static void main(String[] args) {
        System.out.println("En demoConfiguracion");
        demoConfiguracion();
    
        System.out.println("En demoCarreras");
        demoCarreras();
        
        System.out.println("En demoFormulario");
        demoFormulario();
        
        generacionFormularios(50);
        
        System.out.println("Generación y notificación de cita de prueba de admisión");
        demoCitas();
        
        System.out.println("Simulación de Examen de Admisión.");
        demoSimulacionExamen();
        
        System.out.println("Cambio de estados de admisión");
        demoEstados();
        
        System.out.println("Solicitud de un formulario específico");
        demoVisualizacionFormulario(26);
        
        System.out.println();
        
        
        
       
     }
    
}
