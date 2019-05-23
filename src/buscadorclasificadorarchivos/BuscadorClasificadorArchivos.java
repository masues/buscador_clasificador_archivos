package buscadorclasificadorarchivos;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuscadorClasificadorArchivos {
    public static void main(String[] args) {
        //Datos de entrada
        String nomDirectorio = "/tmp/prueba";//modificar para usar 
        //una ruta de su computadora
        int numElementos = 2;
        int [] contador = new int[numElementos];
        //usaré posición 0 para archivos y 1 para directorios
        
        File archivo = new File(nomDirectorio);
        Buscador buscador = new Buscador(archivo,contador);
        buscador.start();
        try {
            buscador.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(
                BuscadorClasificadorArchivos.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        System.out.println(
                    "=================================================");
        System.out.println("main:\tDirectorios: "+contador[1]+","
            +" Archivos: "+contador[0]);
        System.out.println(
                    "=================================================");
        System.out.println("TERMINÓ: main");
    }
}
