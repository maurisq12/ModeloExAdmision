/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.DAO.SingletonDAO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import model.Carrera;
import model.Configuracion;
import model.FormularioSolicitante;
import model.TEstadoSolicitante;

/**
 *
 * @author ersolano
 */
public class Controlador {
    
    private AdmConfiguracion admConfig = new AdmConfiguracion();
    private AdmCarreras admCarreras = new AdmCarreras();
    private AdmFormularios admFormularios = new AdmFormularios();
    
    public Controlador() {
    }
    
    public boolean editarPuntajeGeneralAdmision(int nuevoValor){
        return admConfig.editarPuntajeAdmision(nuevoValor);
    }

    public int getPuntajeGeneralAdmision() {
        return admConfig.getPuntajeAdmision();
    }
    
    public boolean guardarConfiguracion(){
        return admConfig.guardarConfiguracion();
    }
    
    public List<Carrera> getCarreras(){
        return admCarreras.getCarreras();
    }
    
   
      
    public List<Carrera> getCarrerasPorSede(String sede){
        return admCarreras.getCarreras(sede);
    }
    
    public boolean editarCapacidadAdmision(String codigoCarrera, String codigoSede, int capacidad){
        return admCarreras.editarCarrera(codigoCarrera, codigoSede, capacidad);                
    }
    
    public boolean editarPuntajeMinimoAdmision(String codigoCarrera, String codigoSede, int puntaje){
        return admCarreras.editarCarrera(puntaje, codigoCarrera, codigoSede);                
    }

//    public Object getParam(String param, Class elTipo) {
//        if (Integer.class.equals(elTipo)) {
//            return Configuracion.getInstance().getParam(param, Integer.class);
//        }
//        if (String.class.equals(elTipo)) {
//            return Configuracion.getInstance().getParam(param, String.class);
//        }
//        if (Double.class.equals(elTipo)) {
//            return Configuracion.getInstance().getParam(param, Double.class);
//        }
//        return null;
//
//    }

    public boolean registrarFormulario(DTOFormulario elDTO) {
        // se hace cualquier otra operación que se pudiera requerir 
        return admFormularios.registrarFormulario(elDTO);
    }
    
    public FormularioSolicitante getFormulario(int idSolic){
        return admFormularios.consultarFormulario(idSolic);
    }
    
    public boolean generarCitas(){
        GeneradorCitas elGenerador = new GeneradorCitas();
        elGenerador.asignarCitasASolcitantes();
        elGenerador.notificarFormulario();
        return true;    
    }
    
    public void simulacionAplicacionExamen(){
        admFormularios.simulacionAplicacionExamen(this.getPuntajeGeneralAdmision() );
        
    }

    public void definirSituacionCandidatos(){
        admFormularios.definirEstadoAdmisionCandidatos();
    }
    
    public void getDesgloseFormulario(int pIdSolicitante){
        admFormularios.getDesgloseCandidatoPorSolicitante(pIdSolicitante);
    }

    public void getFormulariosPorCarrera_Solicitante(String carrera) {
        admFormularios.getDesgloseCandidatosPorSolicitante(carrera);
    }

    public void getFormulariosPorCarrera_Estado(String carrera) {
        admFormularios.getDesgloseCandidatosPorEstado(carrera);
    }
    
}