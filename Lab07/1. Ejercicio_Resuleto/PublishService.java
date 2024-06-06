package es.rosamarfil; // Define el paquete al que pertenece la clase PublishServices.

import javax.xml.ws.Endpoint; // Importa la clase Endpoint para publicar servicios web.
import es.rosamarfil.soap.SOAPImpl; // Importa la implementación del servicio web SOAPImpl.

public class PublishServices { // Define la clase PublishServices.
    public static void main(String[] args) { // Método principal que se ejecuta al iniciar la aplicación.
        /*
         * Se publican los servicios a través de un servidor virtual.
         * El puerto puede ser cualquiera.
         * Una vez ejecutada la aplicación, se publica el contrato WSDL.
         */
        Endpoint.publish("http://localhost:1516/WS/Users", new SOAPImpl()); // Publica el servicio web en la URL especificada.
    }
}

