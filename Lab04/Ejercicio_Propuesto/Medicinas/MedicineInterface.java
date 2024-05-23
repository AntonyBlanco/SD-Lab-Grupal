package Medicinas;

import java.rmi.Remote;

/**
 * Interfaz que define los métodos que pueden ser invocados de forma remota en objetos de tipo Medicine.
 * Esta interfaz extiende la interfaz Remote para habilitar la comunicación remota.
 * Contiene métodos para comprar medicinas, obtener el stock y imprimir detalles de la medicina.
 * Estos métodos son implementados por la clase Medicine.
 */
public interface MedicineInterface extends Remote {
    
    /**
     * Método para comprar una cantidad específica de medicina.
     * @param amount La cantidad de medicina a comprar.
     * @return La medicina comprada.
     * @throws Exception Si ocurre algún error durante la compra.
     */
    public Medicine getMedicine(int amount) throws Exception;
    
    /**
     * Método para obtener el stock disponible de la medicina.
     * @return El stock disponible de la medicina.
     * @throws Exception Si ocurre algún error al obtener el stock.
     */
    public int getStock() throws Exception;
    
    /**
     * Método para imprimir los detalles de la medicina.
     * @return Una cadena de texto con los detalles de la medicina.
     * @throws Exception Si ocurre algún error al imprimir los detalles.
     */
    public String print() throws Exception;
}
