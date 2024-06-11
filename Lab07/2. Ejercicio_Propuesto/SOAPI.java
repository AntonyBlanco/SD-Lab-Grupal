package WSSOAP;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface SOAPI {
    @WebMethod
    List<Product> getProducts();

    @WebMethod
    Product getProductById(String id);

    @WebMethod
    String purchaseProduct(String id, int quantity);
}


