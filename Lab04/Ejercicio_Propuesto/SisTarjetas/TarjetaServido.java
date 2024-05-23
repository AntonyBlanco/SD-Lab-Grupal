package SisTarjetas;

import java.rmi.Naming;

// Clase que representa el servidor para el sistema de tarjetas
public class TarjetaServidor {
    public static void main(String[] args) {
        try {
            // Se instancia la implementación de la interfaz TarjetaInterfaz
            TarjetaInterfaz creditCardService = new InplementTarjeta();
            System.out.println("Servidor listo"); // Mensaje indicando que el servidor está listo
            // Se vincula el objeto remoto al nombre "CreditCardService" en el registro RMI
            Naming.rebind("CreditCardService", creditCardService);
        } catch (Exception e) {
            e.printStackTrace(); // En caso de error, se imprime la traza del error
        }
    }
}
