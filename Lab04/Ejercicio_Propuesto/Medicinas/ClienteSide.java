package Medicinas;

import java.rmi.Naming; 
import java.util.Scanner; 
import java.util.HashMap; 

public class ClienteSide {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in); // Crea un objeto Scanner para la entrada de usuario
        StockInterface pharm = (StockInterface) Naming.lookup("PHARMACY"); // Busca el objeto remoto con el nombre "PHARMACY" y lo asigna a la interfaz StockInterface

        // Muestra un menú de opciones al usuario
        System.out.println("Ingresa la opcion\n" + "1: Listar productos\n" + "2: Comprar Producto\n");

        int selection = sc.nextInt(); // Lee la opción seleccionada por el usuario

        // Si la opción seleccionada es 1, listar productos
        if (selection == 1) {
            // Obtiene el stock de productos del servidor y lo asigna a un HashMap
            HashMap<String, MedicineInterface> aux = (HashMap<String, MedicineInterface>) pharm.getStockProducts();

            // Itera sobre el HashMap y muestra los detalles de cada medicina
            for (String key : aux.keySet()) {
                MedicineInterface e = (MedicineInterface) aux.get(key);
                System.out.println(e.print());
                System.out.println("*--------------*");
            }
        }

}
