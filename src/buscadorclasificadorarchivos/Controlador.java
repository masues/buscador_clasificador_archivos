package buscadorclasificadorarchivos;

import java.util.logging.Level;
import java.util.logging.Logger;

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
