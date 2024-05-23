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
    

}
