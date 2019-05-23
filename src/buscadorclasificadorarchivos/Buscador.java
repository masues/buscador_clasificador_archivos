package buscadorclasificadorarchivos;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Buscador extends Thread{
    //constructor para directorio raíz (inicial)
    public Buscador(File ruta,int [] contador){
        this.Ruta = ruta;
        this.contador = contador;
        this.disponible = true;
    }
    //Constructor para (sub carpetas)
    public Buscador(File ruta, String tab, boolean esPrimero, 
        String nomSubcarpeta, int [] contador,boolean disponible){ 
        this.Ruta = ruta;
        this.Tab = tab;
        this.esPrimero=esPrimero;
        setName(getName()+" ("+nomSubcarpeta+") ");
        this.contador = contador;
        this.disponible = disponible;
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
        ArrayList<Buscador> hilos = new ArrayList<>();

        for (File x : Lista) {
            if (x.isDirectory()) {
                System.out.println(Tab+this.getName()+": Directorio: " +
                        x.getName());
                System.out.println("... creando hilo para el directorio " + 
                        x.getName());
                Buscador newHilo = new Buscador(x, this.Tab+"\t", false,
                        x.getName(),this.contador, this.disponible);
                newHilo.start();
                hilos.add(newHilo);
                contar(1);//incrementa el contador de directorios
            } else {
                System.out.println(Tab+this.getName()+": Archivo: " +
                        x.getName());
                contar(0);
            }
        }
        hilos.forEach((Buscador x) -> {
            try {
                (x).join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Buscador.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        });
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
    
    private synchronized void contar(int n){
        //si n es cero, se refiere a archivos
        String mensaje = (n==0) ? "archivos":"directorios";
        if(!disponible){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Buscador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        disponible = false;
        contador[n]++;
        System.out.println("Registro "+mensaje+": "+contador[n]
            +" ("+getName()+")");
        disponible = true;
        notifyAll();
    }
    
    File Ruta;
    String Tab="";
    boolean esPrimero = true;
    int [] contador;
    boolean disponible;
}
