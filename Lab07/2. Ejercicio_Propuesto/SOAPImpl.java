package WSSOAP; 
import javax.jws.WebService; // Importa la anotación WebService
import java.util.ArrayList; // Importa la clase ArrayList del paquete java.util
import java.util.List; // Importa la clase List del paquete java.util

@WebService(endpointInterface = "WSSOAP.SOAPI") // Marca esta clase como un servicio web y especifica la interfaz que implementa
public class SOAPImpl implements SOAPI {
    private List<Product> products; // Lista de productos

    // Constructor que inicializa la lista de productos con algunos productos predeterminados
    public SOAPImpl() {
        products = new ArrayList<>(); // Inicializa la lista como un ArrayList vacío
        // Agrega algunos productos a la lista
        products.add(new Product("1", "Laptop", 2500.0, 10));
        products.add(new Product("2", "Celular", 1200.0, 20));
        products.add(new Product("3", "Televisor", 800.0, 15));
        products.add(new Product("4", "Headphone", 130.0, 30));
        products.add(new Product("5", "Audifonos", 50.0, 50));
        products.add(new Product("6", "Monitor", 300.0, 25));
        products.add(new Product("7", "Teclado", 70.0, 40));
        products.add(new Product("8", "Mouse", 40.0, 60));
        products.add(new Product("9", "Impresora", 150.0, 10));
        products.add(new Product("10", "Router", 80.0, 35));
    }

    // Método para obtener todos los productos disponibles
    @Override
    public List<Product> getProducts() {
        return products;
    }

    // Método para obtener un producto por su identificador
    @Override
    public Product getProductById(String id) {
        // Itera sobre la lista de productos para encontrar el producto con el ID especificado
        for (Product product : products) {
            if (product.getId().equals(id)) { // Compara los IDs
                return product; // Retorna el producto si se encuentra
            }
        }
        return null; // Retorna null si no se encuentra ningún producto con el ID especificado
    }

    // Método para realizar una compra de un producto
    @Override
    public String purchaseProduct(String id, int quantity) {
        // Itera sobre la lista de productos para encontrar el producto con el ID especificado
        for (Product product : products) {
            if (product.getId().equals(id) && product.getStock() >= quantity) { // Comprueba si el ID coincide y si hay suficiente stock
                product.setStock(product.getStock() - quantity); // Actualiza el stock del producto
                return "Purchase successful."; // Retorna un mensaje de compra exitosa
            }
        }
        return "Purchase failed."; // Retorna un mensaje de compra fallida si no se encuentra el producto o no hay suficiente stock
    }
}



