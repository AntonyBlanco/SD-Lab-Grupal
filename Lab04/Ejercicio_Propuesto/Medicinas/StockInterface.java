package Medicinas;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.*;

public interface StockInterface extends Remote {
    
    /**
     * Método para obtener la lista de productos en el inventario.
     * @return Un HashMap que mapea nombres de medicinas a objetos de tipo MedicineInterface.
     * @throws Exception Si ocurre algún error al obtener la lista de productos.
     */
    public HashMap<String, MedicineInterface> getStockProducts() throws Exception;
    
    /**
     * Método para agregar una nueva medicina al inventario.
     * @param name El nombre de la medicina a agregar.
     * @param price El precio unitario de la medicina a agregar.
     * @param stock El stock inicial de la medicina a agregar.
     * @throws Exception Si ocurre algún error al agregar la medicina.
     */
    public void addMedicine(String name, float price, int stock) throws Exception;
    
    /**
     * Método para comprar una medicina del inventario.
     * @param name El nombre de la medicina a comprar.
     * @param amount La cantidad de medicina a comprar.
     * @return La medicina comprada.
     * @throws Exception Si ocurre algún error durante la compra.
     */
    public MedicineInterface buyMedicine(String name, int amount) throws Exception;
}
