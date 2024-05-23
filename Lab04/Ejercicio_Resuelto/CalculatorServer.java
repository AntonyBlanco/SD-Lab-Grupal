package Resuelto;

import java.rmi.Naming;

public class CalculatorServer {
    public CalculatorServer() {
        try {
            // Crea una instancia del objeto remoto
            Calculator c = new CalculatorImpl();

            // Registra el objeto remoto en el RMI Registry con el nombre "CalculatorService"
            Naming.rebind("rmi://localhost:1099/CalculatorService", c);
            
            // Mensaje de confirmación
            System.out.println("CalculatorServer is ready.");
        } catch (Exception e) {
            // Manejo de excepciones
            System.out.println("Trouble: " + e);
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        // Inicia el servidor
        new CalculatorServer();
    }
}
