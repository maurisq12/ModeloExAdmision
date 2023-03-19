/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.AdmFormularios;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import model.Carrera;
import model.FormularioSolicitante;
import model.TEstadoSolicitante;



/**
 *
 * @author maurisq
 */
public class GeneradorCitas {
    
    public GeneradorCitas(){
    }
    
    public void asignarCitasASolcitantes(){
        AdmFormularios adminForms = new AdmFormularios();
        List<FormularioSolicitante> todosForms = adminForms.getFormularios(TEstadoSolicitante.SOLICITANTE);
        for (Iterator<FormularioSolicitante> it = todosForms.iterator(); it.hasNext();) {
            FormularioSolicitante pForm = it.next();
            int pNumero = pForm.getNumero();
            adminForms.registrarCitaExamen(pNumero);
            
            System.out.println(pForm.toString());
        }           
    }
    
    public void notificarFormulario(){
        UtilitarioComunicacion comun = new UtilitarioComunicacion();
        AdmFormularios adminForms = new AdmFormularios();
        List<FormularioSolicitante> todosForms = adminForms.getFormularios(TEstadoSolicitante.SOLICITANTE);
        for (Iterator<FormularioSolicitante> it = todosForms.iterator(); it.hasNext();) {
            FormularioSolicitante pForm = it.next();
            String asunto1 = "Se le ha asignado una cita para el examen de admisión 2023\n";
            
            //convertimos la fecha a string para el asunto
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String fechaFormato = sdf.format(pForm.getDetalleExamen().getCitaExamen().getTime());
            
            String asunto2= "Fecha : " + fechaFormato + " \n";
            String asunto3= "Lugar de aplicación : " + pForm.getDetalleExamen().getLugarExamen().getNombre() + " \n";
            String elAsunto=asunto1+asunto2+asunto3;
            comun.enviarCorreo("sistemaAdmision@itcr.ac.cr",pForm.getCorreoSolic(),elAsunto);
        } 
         
         
    }
    
    
}
