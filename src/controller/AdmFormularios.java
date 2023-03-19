/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.DAO.SingletonDAO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import model.Carrera;
import model.CentroAplicacion;
import model.DatosExamen;
import model.DireccionPCD;
import model.FormularioSolicitante;
import model.TEstadoSolicitante;

/**
 *
 * @author ersolano
 */
public class AdmFormularios {

    public AdmFormularios() {
    }
   
    public boolean registrarFormulario (DTOFormulario elDTO){
        
        // verifica que el solicitante no haya registrado otro anteriormente
        FormularioSolicitante elForm = SingletonDAO.getInstance().consultarFormulario (elDTO.getIdSolic());
        
        if (elForm == null){
            elForm = new FormularioSolicitante();
            elForm.setIdSolic(elDTO.getIdSolic());
            elForm.setNombreSolic(elDTO.getNombreSolic());
            elForm.setCorreoSolic(elDTO.getCorreoSolic());
            elForm.setCelularSolic(elDTO.getCelularSolic());
            elForm.setColegioSolic(elDTO.getColegioSolic());
            elForm.setDirSolicPCD(elDTO.getDirSolic());
            elForm.setDetalleDirSolic(elDTO.getDetalleDireccion());
            elForm.setCarreraSolic(elDTO.getCarreraSolic());
            
            boolean res= SingletonDAO.getInstance().agregarFormulario(elForm);
            return res;
        }
        return false;
    }
    
    public FormularioSolicitante consultarFormulario (int idSolic){
        return SingletonDAO.getInstance().consultarFormulario(idSolic);
    }
    
    public List<FormularioSolicitante> getFormularios(TEstadoSolicitante estado){
        return SingletonDAO.getInstance().getFormulario(estado);
    }
    
    
    
    public boolean registrarCitaExamen(int numero){
        List<CentroAplicacion> todosCentros = SingletonDAO.getInstance().getCentrosAplicacion();
        List<Calendar> todasFechas = SingletonDAO.getInstance().getFechas();
        List<FormularioSolicitante> todosFormularios = getFormularios(TEstadoSolicitante.SOLICITANTE);
        for (FormularioSolicitante form : todosFormularios) {
            if (form.getNumero()== numero){
                Calendar pFechaCita = todasFechas.get(ThreadLocalRandom.current().nextInt(0,todasFechas.size()));
                CentroAplicacion pCentro = todosCentros.get(ThreadLocalRandom.current().nextInt(0,todosCentros.size()));
                DatosExamen pDatos = new DatosExamen();
                pDatos.setCitaExamen(pFechaCita);
                pDatos.setLugarExamen(pCentro);
                form.setDetalleExamen(pDatos);                                
            }            
        }
        return true;        
    }
    
    public void simulacionAplicacionExamen(int pPuntaje){
        List<FormularioSolicitante> todosForms = this.getFormularios(TEstadoSolicitante.SOLICITANTE);
        List<Carrera> todasCarreras = SingletonDAO.getInstance().getCarreras();
        for (Iterator<FormularioSolicitante> it = todosForms.iterator(); it.hasNext();) {
            FormularioSolicitante pForm = it.next();
            Random r1 = new Random();
            int low1 = 1;
            int high1 = 10;
            int ausenteRandom = r1.nextInt(high1-low1) + low1;
            if (ausenteRandom != 3 && ausenteRandom != 6){
                for (Carrera carrera : todasCarreras){
                    if (carrera.getNombre().equals(pForm.getCarreraSolic().getNombre())){
                        Random r2 = new Random();
                        int low2 = 0;
                        int high2 = pPuntaje;
                        int result = r2.nextInt(high2-low2) + low2;
                        pForm.getDetalleExamen().setPuntajeObtenido(result);
                        pForm.setEstado(TEstadoSolicitante.CANDIDATO);
                        System.out.println("Situación: " + pForm.getEstado().toString() );
                        System.out.println("Nombre: " + pForm.getNombreSolic() + " Nota: " + pForm.getDetalleExamen().getPuntajeObtenido());
                        break;
                    }
                }
            }else{
                pForm.getDetalleExamen().setPuntajeObtenido(0);
                pForm.setEstado(TEstadoSolicitante.AUSENTE);
                System.out.println("Situación: " + pForm.getEstado().toString() );
                System.out.println("Nombre: " + pForm.getNombreSolic() + " Nota: " + pForm.getDetalleExamen().getPuntajeObtenido());
            }    
        }
    }
    
        public void definirEstadoAdmisionCandidatos(){
            List<FormularioSolicitante> todosForms = SingletonDAO.getInstance().getFormulario(TEstadoSolicitante.CANDIDATO);
            for (FormularioSolicitante pForm : todosForms) {
                if(pForm.getDetalleExamen().getPuntajeObtenido() >= pForm.getCarreraSolic().getPuntajeMinimo() &&  pForm.getCarreraSolic().getMaxAdmision() >0 ){
                    pForm.setEstado(TEstadoSolicitante.ADMITIDO);
                    pForm.getCarreraSolic().setMaxAdmision(pForm.getCarreraSolic().getMaxAdmision()-1);
                }
                else if(pForm.getDetalleExamen().getPuntajeObtenido() >= pForm.getCarreraSolic().getPuntajeMinimo()){
                    pForm.setEstado(TEstadoSolicitante.POSTULANTE);
                }
                else{
                    pForm.setEstado(TEstadoSolicitante.RECHAZADO);
                }
                System.out.println("Situación: " + pForm.getEstado().toString() );
                System.out.println("Nombre: " + pForm.getNombreSolic() + " Nota: " + pForm.getDetalleExamen().getPuntajeObtenido());
            }
        }
        
        public void getDesgloseCandidatoPorSolicitante(int pIdSolicitante){
            FormularioSolicitante elFormulario = SingletonDAO.getInstance().getFormulario(pIdSolicitante);
            if(elFormulario != null)
                if(elFormulario.getEstado()!= TEstadoSolicitante.SOLICITANTE || elFormulario.getEstado()!= TEstadoSolicitante.CANDIDATO){
                    System.out.println("Datos del estudiante: \n");
                    System.out.println(elFormulario.getNombreSolic());
                    System.out.println(elFormulario.getCorreoSolic());
                    System.out.println(elFormulario.getCelularSolic());
                    System.out.println(elFormulario.getColegioSolic());
                    System.out.println(elFormulario.getDirSolicPCD().toString());
                    System.out.println(elFormulario.getDetalleDirSolic());
                    System.out.println("Carrera seleccionada: \n");
                    System.out.println("Carrera: " + elFormulario.getCarreraSolic().getNombre());
                    System.out.println("Sede: " + elFormulario.getCarreraSolic().getSede().getNombre());
                    System.out.println("Detalles de admisión del estudiante: \n");
                    System.out.println("Puntuación mínima necesaria: " + elFormulario.getCarreraSolic().getPuntajeMinimo());
                    System.out.println("Puntuación obtenida: " + elFormulario.getDetalleExamen().getPuntajeObtenido());
                    System.out.println("Estado del estudiante:" + elFormulario.getEstado().toString());

                }
                else{
                    System.out.println("Aún no se encuentra disponible");
                }
            else{
                System.out.println("No existe ese formulario");
            }
        }
        
        public void getDesgloseCarreraPuntaje(){
            List<FormularioSolicitante> listaForms = SingletonDAO.getInstance().getFormulario(TEstadoSolicitante.ADMITIDO);
            listaForms.addAll(SingletonDAO.getInstance().getFormulario(TEstadoSolicitante.POSTULANTE ));
            listaForms.addAll(SingletonDAO.getInstance().getFormulario(TEstadoSolicitante.RECHAZADO));
            
            
            
            
        }
    
    
    
}
