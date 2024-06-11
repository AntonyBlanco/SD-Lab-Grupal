package WSSOAP;

import javax.xml.ws.Endpoint;

public class SOAPPublisher {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:1516/WS/Products", new SOAPImpl());
        System.out.println("Service is published!");
    }
}
