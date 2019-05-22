package buscadorclasificadorarchivos;
import java.io.File;

public class BuscadorClasificadorArchivos {
    public static void main(String[] args) {
        //Datos de entrada
        String nomDirectorio = "/home/mario/Pictures";//modificar para usar 
        //una ruta de su computadora
        int numElementos = 2;
        File archivo = new File(nomDirectorio);
        Buscador buscador = new Buscador(archivo);
        buscador.start();
        //System.out.println("TERMINÓ: main");
    }
}
