package Medicinas;

/**
 * Clase que representa una excepción personalizada para manejar errores relacionados con el inventario de medicinas.
 * Extiende la clase Exception para definir una excepción personalizada.
 */
public class StockException extends Exception {
    
    /**
     * Constructor que recibe un mensaje de error y lo pasa al constructor de la superclase.
     * @param msg El mensaje de error asociado con la excepción.
     */
    public StockException(String msg) {
        super(msg);
    }
}
