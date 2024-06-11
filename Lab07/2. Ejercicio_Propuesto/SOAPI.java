package WSSOAP; 

import javax.jws.WebMethod; // Importa la anotación WebMethod
import javax.jws.WebService; // Importa la anotación WebService
import java.util.List; // Importa la clase List del paquete java.util

//describe un conjunto de operaciones disponibles en un servicio web. 
@WebService // Marca esta interfaz como un servicio web
public interface SOAPI {

    // Método para obtener una lista de productos
    @WebMethod 
    List<Product> getProducts();

    // Método para obtener un producto por su identificador
    @WebMethod 
    Product getProductById(String id);

    // Método para comprar un producto
    @WebMethod 
    String purchaseProduct(String id, int quantity);
}

