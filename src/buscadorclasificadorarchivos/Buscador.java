package buscadorclasificadorarchivos;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Buscador extends Thread{
    
    File Ruta;
    String Tab="";
    boolean esPrimero = true;
    int [] contador;
    boolean disponible;
    Thread ultimo = Thread.currentThread();
    
    public Buscador(File ruta,int [] contador){
        this.Ruta = ruta;
        this.contador = contador;
        this.disponible = true;
    }
    //Constructor para (sub carpetas)
    public Buscador(File ruta, String tab, boolean esPrimero, 
        String nomSubcarpeta, int [] contador, boolean disponible){ 
        this.Ruta = ruta;
        this.Tab = tab;
        this.esPrimero=esPrimero;
        setName(getName()+" ("+nomSubcarpeta+") ");
        this.contador = contador;
        this.disponible = disponible;
        //this.ultimo = ultimo;
        this.start();
    }
    @Override
    public void run()
    {   if (esPrimero){
            System.out.println(
                    "=================================================");
            System.out.println("Origen:\t"+Ruta.getPath());
            System.out.println(
                    "=================================================");
            System.out.println(
                    "============== Árbol de directorios =============");
        
            //Obtiene el árbol de directorios si es el hilo creado por el main
            getArbolDirectorios(Ruta);
        
            System.out.print("\n"+
                    "===================== Listado ==================="+"\n");
        }
        
        File [] Lista = Ruta.listFiles();

        for (int i=0; i<Lista.length; i++) {
            if(Lista[i].isDirectory()){
                contar(1);
                System.out.println(Tab+this.getName()+": Directorio: "
                    +Lista[i].getName());
                System.out.println("... creando hilo para el directorio "
                    +Lista[i].getName());
                actualizaUltimo(new Buscador(Lista[i],this.Tab+"\t",false,
                    Lista[i].getName(),this.contador, this.disponible));     //Agregar start
            }else{
                contar(0);
                System.out.println(Tab+this.getName()+": Archivo: "
                    +Lista[i].getName());
            }
        }
        esperar();
        System.out.println("TERMINÓ: "+getName());
    }
    
    private void getArbolDirectorios(File x){
        File [] lista = x.listFiles();
        for(File y:lista){
            if (y.isDirectory()){
                System.out.println(getName()+" DIRECTORIO: "+y.getPath());
                getArbolDirectorios(y);
            }    
        }   
    }
    
    //Procedimiento sincronizado para contar
    private synchronized void contar(int pos){
        if(disponible == false){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Buscador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        disponible = false;
        contador[pos] = contador[pos] + 1;
        if(pos == 0){
            System.out.println("\tREGISTRO ARCHIVOS: "+contador[pos]+"  ("+getName()+")");
        }else if(pos == 1){
            System.out.println("\tREGISTRO DIRECTORIOS: "+contador[pos]+"  ("+getName()+")");
        }
        disponible = true;
        notifyAll();
    }
    
    //Procedimiento para actualizar ultimo hilo
    private synchronized void actualizaUltimo(Thread a){
        this.ultimo = a;
        notifyAll();
    }
    
    //procedimiento para esperar hilos internos
    private synchronized void esperar(){
        try {
            this.ultimo.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Buscador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
