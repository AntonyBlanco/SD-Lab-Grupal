package LabSD3;

import java.io.*;
import java.net.*;

public class Servidor {
    // Se define el número de puerto en el que el servidor estará escuchando las conexiones
    static final int PUERTO=5000;
    
    // Constructor de la clase Servidor
    public Servidor( ) {
        try {
            // Se crea un socket del servidor que escucha en el puerto especificado
            ServerSocket skServidor = new ServerSocket(PUERTO);
            // Se imprime en la consola un mensaje indicando que el servidor está escuchando en el puerto
            System.out.println("Escucho el puerto " + PUERTO);
            
            // Bucle para servir a tres clientes
            for ( int numCli = 0; numCli < 3; numCli++ ) {
                // Se espera a que un cliente se conecte al servidor y se crea un socket del cliente para manejar la comunicación
                Socket skCliente = skServidor.accept(); // Crea objeto
                // Se imprime en la consola un mensaje indicando que se está sirviendo al cliente actual
                System.out.println("Sirvo al cliente " + numCli);
                
                // Se obtiene un flujo de salida desde el socket del cliente para enviar datos al cliente
                OutputStream aux = skCliente.getOutputStream();
                // Se envuelve el flujo de salida en un DataOutputStream para facilitar la escritura de datos
                DataOutputStream flujo= new DataOutputStream( aux );
                // Se escribe y se envía al cliente un saludo que incluye el número de cliente
                flujo.writeUTF( "Hola cliente " + numCli );
                
                // Se cierra el socket del cliente después de completar la comunicación con él
                skCliente.close();
            }
            // Se imprime en la consola un mensaje indicando que no se aceptarán más clientes hoy
            System.out.println("Demasiados clientes por hoy");
        } catch( Exception e ) {
            // Se captura cualquier excepción que pueda ocurrir durante la ejecución del código y se imprime en la consola
            System.out.println( e.getMessage() );
        }
    }
    
    // Método principal que crea una instancia de la clase Servidor al ejecutar el programa
    public static void main( String[] arg ) {
        new Servidor();
    }
}
