package buscadorclasificadorarchivos;
import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuscadorClasificadorArchivos {
    public static void main(String[] args) {
        
        String nomDirectorio; 
        int numElementos = 2;
        
        Scanner teclado = new Scanner(System.in);
        System.out.println("Introduzca la ruta: ");
        nomDirectorio = teclado.nextLine();
        System.out.println("Introduzca el número de elementos a contar: ");
        numElementos = teclado.nextInt();
        
        int [] contador = new int[numElementos];
        
        File archivo = new File(nomDirectorio);
        
        if(!archivo.exists() || !archivo.isDirectory()){
            System.out.print("El directorio ingresado no existe o no es un "+
                    "directorio.\nTerminando ejecución...\n");
            System.exit(0);
        }
        if(numElementos<1 || numElementos>2){
            System.out.print("El número de elementos debe ser 1 o 2\n"+
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
        imprimeContador(contador);
        System.out.println("=================================================");
        System.out.println("TERMINÓ: main");
    }
    public static void imprimeContador(int [] contador){
        switch (contador.length){
            case 1:
                System.out.println("Main:\tArchivos: "+contador[0]);
                break;
            case 2:
                System.out.println("Main: Directorios: "+contador[1]+
                        ",  Archivos: "+contador[0]);
                break;
        }
    }
}
