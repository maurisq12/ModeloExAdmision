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
import java.util.concurrent.ThreadLocalRandom;
import model.CentroAplicacion;
import model.DatosExamen;
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
    
    
    
}
