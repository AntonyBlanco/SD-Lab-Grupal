package WSSOAP; 

import java.io.Serializable; 


public class Product implements Serializable {
    
    // Variables de instancia
    private String id; // Identificador del producto
    private String name; // Nombre del producto
    private double price; // Precio del producto
    private int stock; // Cantidad en stock del producto

    // Constructor 1
    public Product() {
    }

    // Constructor 2
    public Product(String id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Método getter para obtener el id del producto
    public String getId() {
        return id;
    }

    // Método setter para establecer el id del producto
    public void setId(String id) {
        this.id = id;
    }

    // Método getter para obtener el nombre del producto
    public String getName() {
        return name;
    }

    // Método setter para establecer el nombre del producto
    public void setName(String name) {
        this.name = name;
    }

    // Método getter para obtener el precio del producto
    public double getPrice() {
        return price;
    }

    // Método setter para establecer el precio del producto
    public void setPrice(double price) {
        this.price = price;
    }

    // Método getter para obtener la cantidad en stock del producto
    public int getStock() {
        return stock;
    }

    // Método setter para establecer la cantidad en stock del producto
    public void setStock(int stock) {
        this.stock = stock;
    }

    // Método toString para obtener una representación de cadena del objeto
    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}


