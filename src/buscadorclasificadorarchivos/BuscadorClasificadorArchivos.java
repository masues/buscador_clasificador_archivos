package buscadorclasificadorarchivos;
import java.io.File;

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
        //System.out.println("TERMINÓ: main");
    }
}
