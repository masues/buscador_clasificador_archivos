package buscadorclasificadorarchivos;

import java.io.File;

public class Buscador extends Thread{
    public Buscador(File ruta,int [] contador){
        this.Ruta = ruta;
        this.contador = contador;
    }
    //Constructor para (sub carpetas)
    public Buscador(File ruta, String tab, boolean esPrimero, 
        String nomSubcarpeta){ 
        this.Ruta = ruta;
        this.Tab = tab;
        this.esPrimero=esPrimero;
        setName(getName()+" ("+nomSubcarpeta+") ");
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
                System.out.println(Tab+this.getName()+": Directorio: "
                    +Lista[i].getName());
                System.out.println("... creando hilo para el directorio "
                    +Lista[i].getName());
                new Buscador(Lista[i],this.Tab+"\t",false,
                    Lista[i].getName()).start();
            }else{
                System.out.println(Tab+this.getName()+": Archivo: "
                    +Lista[i].getName());
            }
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
    
    File Ruta;
    String Tab="";
    boolean esPrimero = true;
    int [] contador;
}
