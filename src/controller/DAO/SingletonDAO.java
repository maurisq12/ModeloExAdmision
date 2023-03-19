/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.DAO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Carrera;
import model.CentroAplicacion;
import model.DireccionPCD;
import model.FormularioSolicitante;
import model.TEstadoSolicitante;
import model.Sede;
import model.TGrado;

/**
 *
 * @author ersolano
 */
public class SingletonDAO {
    private static SingletonDAO instance;

    private  ArrayList<Carrera> tablaCarreras = new ArrayList(); 
    private  ArrayList<Sede> tablaSedes = new ArrayList(); 
    private  ArrayList<CentroAplicacion> tablaCentros = new ArrayList(); 
    private  ArrayList<DireccionPCD> tablaPCD = new ArrayList(); 
    private  ArrayList<FormularioSolicitante> tablaFormularios = new ArrayList();
    private  ArrayList<Calendar> tablaFechas = new ArrayList();
    
    // temporalmente hay una representación de datos que estan en una BD 
    // simulando el acceso a una instancia del modelo de datos persistente
    private SingletonDAO() {
        tablaSedes.add(new Sede("CA","Cartago"));       //tablaSedes.get(0)
        tablaSedes.add(new Sede("SJ","San Jose"));      //tablaSedes.get(1)
        tablaSedes.add(new Sede("LI","Limon"));         //tablaSedes.get(2)
        tablaSedes.add(new Sede("SC","San Carlos"));    //tablaSedes.get(3)
        tablaSedes.add(new Sede("AL","Alajuela"));      //tablaSedes.get(4)

        //carreras de Cartago
        tablaCarreras.add(new Carrera("IC","Ingenieria en Computacion",tablaSedes.get(0), TGrado.BACHILLERATO, 140, 650));
        tablaCarreras.add(new Carrera("PI","Ingenieria en Produccion Industrial",tablaSedes.get(0), TGrado.LICENCIATURA, 100, 620));
        
        // carreras de San Jose
        tablaCarreras.add(new Carrera("IC","Ingenieria en Computacion",tablaSedes.get(1), TGrado.BACHILLERATO, 140, 650));
        
        // carreras de Limon
        tablaCarreras.add(new Carrera("PI","Ingenieria en Produccion Industrial",tablaSedes.get(2), TGrado.LICENCIATURA, 40, 520));
                        
        // direcciones PCD
        tablaPCD.add(new DireccionPCD("Cartago", "Central", "Oriental"));       //0
        tablaPCD.add(new DireccionPCD("Cartago", "Central", "Agua Caliente"));  //1
        tablaPCD.add(new DireccionPCD("Cartago", "Paraiso", "Santiago"));       //2
        tablaPCD.add(new DireccionPCD("Cartago", "Paraiso", "Orosi"));          //3
        tablaPCD.add(new DireccionPCD("San José", "Central", "Carmen"));        //4
        tablaPCD.add(new DireccionPCD("San José", "Central", "Hospital"));      //5
        tablaPCD.add(new DireccionPCD("San José", "Central", "Merced"));        //6
        
        // centros de aplicación de examen
        tablaCentros.add(new CentroAplicacion(100, "Colegio Agua Caliente", tablaPCD.get(1), "Colegio Académico"));
        tablaCentros.add(new CentroAplicacion(200, "TEC Campus Central", tablaPCD.get(0), "Campus Central ITCR"));
        tablaCentros.add(new CentroAplicacion(100, "TEC Campus Local SJ", tablaPCD.get(4), "CTLSJ"));
        
        //fechas establecidas
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(df.parse("12-08-2023"));
            tablaFechas.add(cal);
            cal.setTime(df.parse("19-08-2023"));
            tablaFechas.add(cal);
            cal.setTime(df.parse("26-08-2023"));
            tablaFechas.add(cal);
        } catch (ParseException ex) {
            Logger.getLogger(SingletonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        }
    
    public static SingletonDAO getInstance(){
        if (instance == null)
            instance = new SingletonDAO();
        return instance;
    }
    
    public List<Carrera> getCarreras(){
        // pendiente: conectar a la persistencia y recuperar las carreras
        return tablaCarreras;
    }
    
    public List<Sede> getSedes(){
        // pendiente: conectar a la persistencia y recuperar las sedes
        return tablaSedes;
    }
    
    public Calendar getFecha(int pos){
        if (pos >= 0 && pos < tablaFechas.size())
            return tablaFechas.get(pos);
        else
            return null;
    }
    
    public List<Calendar> getFechas(){
        return tablaFechas;
    }
    
    public String formatoFecha(Calendar fecha){
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(fecha.getTime());
    }
    
    public List<Carrera> consultarCarrerasdeUnaSede (String unaSede){
        List<Carrera> carrerasSede =new ArrayList();
        for (Iterator<Carrera> it = tablaCarreras.iterator(); it.hasNext();) {
            Carrera carrera = it.next();
            if (carrera.getSede().getCodigo().equals(unaSede))
                carrerasSede.add(carrera);
        }
       return carrerasSede;
    }
    
    public Carrera consultarUnaCarrera (String codigoCarrera, String codigoSede){
        for (Carrera unaCarrera : tablaCarreras) {
            if ( unaCarrera.getCodigo().equals(codigoCarrera) && unaCarrera.getSede().getCodigo().equals(codigoSede) )
                return unaCarrera;
        }
        return null;
    }
    
    public boolean editarCarrera(Carrera unaCarrera){
        for (int i=0; i< tablaCarreras.size(); i++) {
            Carrera actual = tablaCarreras.get(i);
            if (actual.getCodigo().equals(unaCarrera.getCodigo()) && 
                actual.getSede().getCodigo().equals(unaCarrera.getSede().getCodigo())){
                tablaCarreras.set(i, unaCarrera);
                return true;
            }
        }
        return false;
    }

    public DireccionPCD getPCD(int pos){
        if (pos >= 0 && pos < tablaPCD.size())
            return tablaPCD.get(pos);
        else
            return null;
    }
    
    public FormularioSolicitante consultarFormulario(int idSolic){
        for (FormularioSolicitante form : tablaFormularios) {
            if (form.getIdSolic() == idSolic)
                return form;
        }
        return null;
    }
    
    public boolean agregarFormulario(FormularioSolicitante unFormulario){
        for (FormularioSolicitante form : tablaFormularios) {
            if (form.getNumero()== unFormulario.getNumero())
                return false;
        }
        tablaFormularios.add(unFormulario);
        return true;
    }
    
    public List<FormularioSolicitante> getFormulario (TEstadoSolicitante estado){
        List<FormularioSolicitante> formsResult =new ArrayList();
        for (Iterator<FormularioSolicitante> it = tablaFormularios.iterator(); it.hasNext();) {
            FormularioSolicitante form = it.next();
            if (form.getEstado().equals(estado))
                formsResult.add(form);
        }
       return formsResult;
    }
    
    public List<CentroAplicacion> getCentrosAplicacion(){
        return tablaCentros;       
    }
    
}
