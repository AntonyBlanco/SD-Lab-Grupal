package WSSOAP;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClientConsole {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:1516/WS/Products?wsdl");
        QName qname = new QName("http://WSSOAP/", "SOAPImplService");
        Service service = Service.create(url, qname);
        SOAPI soap = service.getPort(SOAPI.class);

        Scanner scanner = new Scanner(System.in);
        Map<Product, Integer> cart = new HashMap<>();
        boolean continueShopping = true;

        while (continueShopping) {
            System.out.println("***********************************");
            System.out.println("******** TIENDA ONLINE ***********");
            System.out.println("***********************************");
            System.out.printf("| %-2s | %-10s | %-7s | %-5s |\n", "ID", "Producto", "Precio", "Stock");
            System.out.println("---------------------------------------------");

            List<Product> products = soap.getProducts();
            for (Product product : products) {
                System.out.printf("| %-2s | %-10s | %-7.2f | %-5d |\n", product.getId(), product.getName(), product.getPrice(), product.getStock());
            }

            System.out.print("Ingrese el ID del producto que desea comprar: ");
            String productId = scanner.nextLine();
            System.out.print("Ingrese la cantidad: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            Product product = soap.getProductById(productId);
            if (product != null && product.getStock() >= quantity) {
                cart.put(product, cart.getOrDefault(product, 0) + quantity);
                soap.purchaseProduct(productId, quantity);
                System.out.println("Añadido al carrito: " + product.getName() + " x" + quantity);
            } else {
                System.out.println("ID de producto inválido o stock insuficiente.");
            }

            System.out.print("¿Desea seguir comprando? (yes/no): ");
            continueShopping = scanner.nextLine().equalsIgnoreCase("yes");
        }

        // Calcular el total
        double total = 0;
        System.out.println("\nProductos en su carrito:");
        System.out.printf("%-10s %-10s %-10s\n", "Producto", "Costo", "Cantidad");
        System.out.println("------------------------------------");

        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product product = entry.getKey();
            int qty = entry.getValue();
            double cost = product.getPrice() * qty;
            total += cost;
            System.out.printf("%-10s %-10.2f %-10d\n", product.getName(), product.getPrice(), qty);
        }

        System.out.println("------------------------------------");
        System.out.printf("--> Total a pagar: %.2f\n", total);
        System.out.println("\nGracias por su compra");
    }
}

