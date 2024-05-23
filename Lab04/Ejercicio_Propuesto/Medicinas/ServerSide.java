package Medicinas;

import java.rmi.*;

/**
 * Clase que representa el servidor en el ejemplo de RMI.
 */
public class ServerSide {
    
    public static void main(String[] args) throws Exception {
        // Crea un objeto de la clase Stock para representar el inventario de medicinas
        Stock pharmacy = new Stock();
        
        // Agrega algunas medicinas al inventario con sus respectivos nombres, precios y cantidades iniciales
        pharmacy.addMedicine("Paracetamol", 3.2f, 10);
        pharmacy.addMedicine("Mejoral", 2.0f, 20);
        pharmacy.addMedicine("Amoxilina", 1.0f, 30);
        pharmacy.addMedicine("Aspirina", 5.0f, 40);
        
        // Registra el objeto del inventario de medicinas en el registro RMI con el nombre "PHARMACY"
        Naming.rebind("PHARMACY", pharmacy);
        
        // Muestra un mensaje indicando que el servidor está listo
        System.out.println("Server ready");
    }
}
