/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscadorclasificadorarchivos;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario Alberto Suarez Espinoza
 */
public class Controlador {
    int [] contador;
    boolean disponible;

    public Controlador(int[] contador, boolean disponible) {
        this.contador = contador;
        this.disponible = disponible;
    }

    
    
    //Procedimiento sincronizado para contar
    public synchronized void contar(int pos, String nombre){
        if(disponible == false){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Buscador.class.getName()).log(Level.SEVERE,
                    null, ex);
            }
        }
        disponible = false;
        contador[pos] = contador[pos] + 1;
        if(pos == 0){
            System.out.println("\tREGISTRO ARCHIVOS: "+contador[pos]
                +"  ("+nombre+")");
        }else if(pos == 1){
            System.out.println("\tREGISTRO DIRECTORIOS: "+contador[pos]
                +"  ("+nombre+")");
        }
        disponible = true;
        notifyAll();
    }
}
