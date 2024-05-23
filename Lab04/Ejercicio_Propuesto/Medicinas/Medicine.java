package Medicinas;

import java.rmi.server.UnicastRemoteObject;

/**
 * Clase que representa una medicina en el proyecto.
 * Esta clase implementa la interfaz MedicineInterface y proporciona la lógica para comprar y consultar medicinas.
 * Se utiliza en el servidor para representar las medicinas disponibles en el inventario.
 * 
 * @author rventurar
 */
public class Medicine extends UnicastRemoteObject implements MedicineInterface {
    private String name; // Nombre de la medicina
    private float unitPrice; // Precio unitario de la medicina
    private int stock; // Stock disponible de la medicina
    
    // Constructor por defecto
    public Medicine() throws Exception {
        super();
    }
    
    // Constructor con parámetros
    public Medicine(String name, float price, int stock) throws Exception {
        super();
        this.name = name;
        unitPrice = price;
        this.stock = stock;
    }
    

}
