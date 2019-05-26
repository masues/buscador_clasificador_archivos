package buscadorclasificadorarchivos;
import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuscadorClasificadorArchivos {
    public static void main(String[] args) {
        //Datos de entrada
        String nomDirectorio = "/tmp/prueba2/directorio1";//modificar para usar 
        //una ruta de su computadora
        int numElementos = 2;
        
        Scanner teclado = new Scanner(System.in);
        System.out.println("Introduzca la ruta: ");
        nomDirectorio = teclado.nextLine();
        System.out.println("Introduzca el número de elementos: ");
        numElementos = teclado.nextInt();
        
        int [] contador = new int[numElementos];
        
        File archivo = new File(nomDirectorio);
        if(!archivo.exists()){
            System.out.print("El directorio ingresado no existe\n"+
                    "Terminando ejecución...\n");
            System.exit(0);
        }
        Buscador buscador = new Buscador(archivo,contador);
        buscador.start();
        
        try {
            buscador.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(BuscadorClasificadorArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("=================================================");
        System.out.println("Main: Directorios: "+contador[1]+",  Archivos: "+contador[0]);
        System.out.println("=================================================");
        System.out.println("TERMINÓ: main");
    }
}
