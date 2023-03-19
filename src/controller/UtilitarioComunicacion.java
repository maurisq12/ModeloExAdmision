/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author maurisq
 */
public class UtilitarioComunicacion {
    
    UtilitarioComunicacion(){
    }
    
    public void enviarCorreo(String emisor, String receptor, String asunto){
        System.out.println("Enviado por : "+ emisor);
        System.out.println("Enviado a: "+ receptor);
        System.out.println("Asunto : "+ asunto);
    }
    
    
    
    
}
