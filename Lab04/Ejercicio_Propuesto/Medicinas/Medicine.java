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
    
    // Método para comprar una cantidad específica de medicina
    @Override
    public Medicine getMedicine(int amount) throws Exception {
        // Verifica si el stock de la medicina es suficiente para la compra
        if (this.stock <= 0)
            throw new StockException("Stock vacío");
        if (this.stock - amount < 0)
            throw new StockException("No hay suficiente cantidad de medicina en el stock");
        
        // Reduce el stock de la medicina y calcula el precio total
        this.stock -= amount;
        Medicine aux = new Medicine(name, unitPrice * amount, stock);
        return aux;
    }
    
    // Método para obtener el stock disponible de la medicina
    @Override
    public int getStock() throws Exception {
        return this.stock;
    }
    
    // Método para imprimir los detalles de la medicina
    public String print() throws Exception {
        return this.name + "\nPrecio: " + this.unitPrice + "\nStock: " + this.stock;
    }
}
