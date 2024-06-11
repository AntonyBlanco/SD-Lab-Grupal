package WSSOAP;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "WSSOAP.SOAPI")
public class SOAPImpl implements SOAPI {
    private List<Product> products;

    public SOAPImpl() {
        products = new ArrayList<>();
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

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Product getProductById(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public String purchaseProduct(String id, int quantity) {
        for (Product product : products) {
            if (product.getId().equals(id) && product.getStock() >= quantity) {
                product.setStock(product.getStock() - quantity);
                return "Purchase successful.";
            }
        }
        return "Purchase failed.";
    }
}



