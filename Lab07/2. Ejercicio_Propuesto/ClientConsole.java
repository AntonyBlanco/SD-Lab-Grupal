package WSSOAP; 

import javax.xml.namespace.QName; // Importa la clase QName del paquete javax.xml.namespace
import javax.xml.ws.Service; // Importa la clase Service del paquete javax.xml.ws
import java.net.URL; // Importa la clase URL del paquete java.net
import java.util.*; // Importa clases del paquete java.util para utilizar listas, mapas y entrada de usuario

// Clase que representa un cliente de la tienda online
public class ClientConsole {
    
    // Método principal que se ejecuta al iniciar el cliente
    public static void main(String[] args) throws Exception {
        // URL del servicio web SOAP
        URL url = new URL("http://localhost:1516/WS/Products?wsdl");
        // QName que identifica al servicio web
        QName qname = new QName("http://WSSOAP/", "SOAPImplService");
        // Crea un objeto de servicio utilizando la URL y QName especificados
        Service service = Service.create(url, qname);
        // Obtiene el puerto del servicio web utilizando la interfaz SOAPI
        SOAPI soap = service.getPort(SOAPI.class);

        // Scanner para la entrada de usuario
        Scanner scanner = new Scanner(System.in);
        // Mapa para almacenar los productos en el carrito junto con sus cantidades
        Map<Product, Integer> cart = new HashMap<>();
        // Bandera para indicar si el usuario desea continuar comprando
        boolean continueShopping = true;

        // Bucle principal para la interacción con el usuario
        while (continueShopping) {
            // Muestra la lista de productos disponibles en la tienda
            System.out.println("***********************************");
            System.out.println("******** TIENDA ONLINE ***********");
            System.out.println("***********************************");
            System.out.printf("| %-2s | %-10s | %-7s | %-5s |\n", "ID", "Producto", "Precio", "Stock");
            System.out.println("---------------------------------------------");

            List<Product> products = soap.getProducts(); // Obtiene la lista de productos del servicio web
            for (Product product : products) { // Itera sobre cada producto
                System.out.printf("| %-2s | %-10s | %-7.2f | %-5d |\n", product.getId(), product.getName(), product.getPrice(), product.getStock());
            }

            // Solicita al usuario ingresar el ID del producto que desea comprar y la cantidad
            System.out.print("Ingrese el ID del producto que desea comprar: ");
            String productId = scanner.nextLine();
            System.out.print("Ingrese la cantidad: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            // Obtiene el producto del servicio web utilizando su ID
            Product product = soap.getProductById(productId);
            // Si el producto existe y hay suficiente stock, lo agrega al carrito y realiza la compra
            if (product != null && product.getStock() >= quantity) {
                cart.put(product, cart.getOrDefault(product, 0) + quantity);
                soap.purchaseProduct(productId, quantity); // Realiza la compra del producto
                System.out.println("Añadido al carrito: " + product.getName() + " x" + quantity);
            } else {
                System.out.println("ID de producto inválido o stock insuficiente.");
            }

            // Pregunta al usuario si desea seguir comprando
            System.out.print("¿Desea seguir comprando? (yes/no): ");
            continueShopping = scanner.nextLine().equalsIgnoreCase("yes");
        }

        // Calcula el total de la compra
        double total = 0;
        System.out.println("\nProductos en su carrito:");
        System.out.printf("%-10s %-10s %-10s\n", "Producto", "Costo", "Cantidad");
        System.out.println("------------------------------------");

        // Itera sobre los productos en el carrito y calcula el costo total
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product product = entry.getKey();
            int qty = entry.getValue();
            double cost = product.getPrice() * qty;
            total += cost;
            System.out.printf("%-10s %-10.2f %-10d\n", product.getName(), product.getPrice(), qty);
        }

        // Muestra el total de la compra
        System.out.println("------------------------------------");
        System.out.printf("--> Total a pagar: %.2f\n", total);
        System.out.println("\nGracias por su compra");
    }
}

