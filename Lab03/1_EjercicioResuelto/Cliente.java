package LabSD3;
import java.io.*;
import java.net.*;

public class Cliente {
    // Se define la dirección del servidor al que se conectará el cliente
    static final String HOST = "localhost";
    // Se define el número de puerto en el que el servidor está escuchando las conexiones
    static final int PUERTO=5000;
    
    // Constructor de la clase Cliente
    public Cliente( ) {
        try{
            // Se crea un socket para conectarse al servidor en la dirección y puerto especificados
            Socket skCliente = new Socket( HOST , PUERTO );
            // Se obtiene un flujo de entrada desde el socket del cliente para recibir datos del servidor
            InputStream aux = skCliente.getInputStream();
            // Se envuelve el flujo de entrada en un DataInputStream para facilitar la lectura de datos
            DataInputStream flujo = new DataInputStream( aux );
            // Se lee y se imprime en la consola el mensaje recibido del servidor
            System.out.println( flujo.readUTF() );
            // Se cierra el socket del cliente después de completar la comunicación
            skCliente.close();
        } catch( Exception e ) {
            // Se captura cualquier excepción que pueda ocurrir durante la ejecución del código y se imprime en la consola
            System.out.println( e.getMessage() );
        }
    }
    
    // Método principal que crea una instancia de la clase Cliente al ejecutar el programa
    public static void main( String[] arg ) {
        new Cliente();
    }
}
