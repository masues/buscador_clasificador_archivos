package buscadorclasificadorarchivos;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Buscador extends Thread{
    
    File Ruta;
    String Tab="";
    boolean esPrimero = true;
    int [] contador;
    boolean disponible;
    Controlador controlador;
    
    public Buscador(File ruta,int [] contador){
        this.Ruta = ruta;
        this.contador = contador;
        this.disponible = true;
        this.controlador = new Controlador(contador,disponible);
    }
    public Buscador(File ruta, String tab, boolean esPrimero, 
        String nomSubcarpeta,int [] contador, Controlador controlador){ 
        this.Ruta = ruta;
        this.Tab = tab;
        this.esPrimero=esPrimero;
        setName(getName()+" ("+nomSubcarpeta+") ");
        this.contador=contador;
        this.controlador=controlador;
        this.start();
    }
    @Override
    public void run(){
        File [] Lista = Ruta.listFiles();
        if (esPrimero){
            System.out.println(
                    "=================================================");
            System.out.println("Origen:\t"+Ruta.getPath());
            System.out.println(
                    "=================================================");
            System.out.println(
                    "============== Árbol de directorios =============");
        
            if(Lista!=null)
                getArbolDirectorios(Ruta);
        
            System.out.print("\n"+
                    "===================== Listado ==================="+"\n");
        }
        switch (contador.length){
            case 1:
                if(Lista != null){
                    Buscador esperar[] = new Buscador[contarDir(Lista)];
                    int dir = 0;
                    for (int i=0; i<Lista.length; i++) {
                        if(Lista[i].isDirectory()){
                            System.out.println(Tab+this.getName()+
                                    ": Directorio: "+Lista[i].getName());
                            System.out.println(
                                "... creando hilo para el directorio "
                                +Lista[i].getName());
                            esperar[dir] = new Buscador(Lista[i],"\t",false,
                                Lista[i].getName(),this.contador,this.controlador);
                            dir ++;
                        }else{
                            this.controlador.contar(0, getName());
                            if(this.esPrimero){
                                System.out.println(Tab+this.getName()+
                                        ": Archivo: "+Lista[i].getName());
                            }else{
                                System.out.println(Tab+this.getName()
                                    +"-->: Archivo: "+Lista[i].getName());
                            }
                        }
                    }
                    for(Buscador n : esperar){
                        try {
                            n.join();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Buscador.class.getName()).log(
                                Level.SEVERE, null, ex);
                        }
                    }
                }
                break;
            case 2:
                if(Lista != null){
                    Buscador esperar[] = new Buscador[contarDir(Lista)];
                    int dir = 0;
                    for (int i=0; i<Lista.length; i++) {
                        if(Lista[i].isDirectory()){
                            this.controlador.contar(1,getName());
                            System.out.println(Tab+this.getName()+
                                    ": Directorio: "+Lista[i].getName());
                            System.out.println(
                                "... creando hilo para el directorio "
                                +Lista[i].getName());
                            esperar[dir] = new Buscador(Lista[i],"\t",false,
                                Lista[i].getName(),this.contador,this.controlador);
                            dir ++;
                        }else{
                            this.controlador.contar(0, getName());
                            if(this.esPrimero){
                                System.out.println(Tab+this.getName()+
                                        ": Archivo: "+Lista[i].getName());
                            }else{
                                System.out.println(Tab+this.getName()
                                    +"-->: Archivo: "+Lista[i].getName());
                            }
                        }
                    }
                    for(Buscador n : esperar){
                        try {
                            n.join();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Buscador.class.getName()).log(
                                Level.SEVERE, null, ex);
                        }
                    }
                }
                break;
        }
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

    
    private int contarDir(File Lista[]){
        int n = 0;
        for(File f : Lista){
            if(f.isDirectory()){
                n = n + 1;
            }
        }
        return n;
    }
    
}
