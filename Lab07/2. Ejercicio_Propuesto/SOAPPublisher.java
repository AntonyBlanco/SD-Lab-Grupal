package WSSOAP; 

import javax.xml.ws.Endpoint; // Importa la clase Endpoint del paquete javax.xml.ws

// Clase que publica el servicio web SOAP
public class SOAPPublisher {
    
    // Método principal que se ejecuta al iniciar la aplicación
    public static void main(String[] args) {
        // Publica el servicio web SOAP en la dirección especificada y con la implementación proporcionada
        Endpoint.publish("http://localhost:1516/WS/Products", new SOAPImpl());
        // Imprime un mensaje indicando que el servicio ha sido publicado exitosamente
        System.out.println("Service is published!");
    }
}
