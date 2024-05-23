package Medicinas;

import java.util.HashMap;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Clase que implementa la interfaz StockInterface y proporciona la implementación de los métodos para administrar el inventario de medicinas.
 * Extiende UnicastRemoteObject para permitir la comunicación remota.
 */
public class Stock extends UnicastRemoteObject implements StockInterface {
    private HashMap<String, MedicineInterface> medicines = new HashMap<>(); // HashMap para almacenar las medicinas del inventario
    
    // Constructor por defecto
    public Stock() throws RemoteException {
        super();
    }
    
    // Método para agregar una nueva medicina al inventario
    public void addMedicine(String name, float price, int stock) throws Exception {
        medicines.put(name, new Medicine(name, price, stock));
    }
    
    // Método para comprar una medicina del inventario
    @Override
    public MedicineInterface buyMedicine(String name, int amount) throws Exception {
        MedicineInterface aux = medicines.get(name); // Obtiene la medicina del inventario
        
        // Verifica si la medicina no está disponible en el inventario
        if (aux == null) {
            throw new Exception("Imposible encontrar " + name);
        }
        
        // Realiza la compra de la medicina especificada y la devuelve
        MedicineInterface element = aux.getMedicine(amount);
        return element;
    }
    
    // Método para obtener la lista de productos en el inventario
    @Override
    public HashMap<String, MedicineInterface> getStockProducts() throws Exception {
        return this.medicines;
    }
}
