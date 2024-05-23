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
        // Si la opción seleccionada es 2, comprar un producto
        else if (selection == 2) {
            System.out.println("Ingrese nombre de la medicina"); // Solicita al usuario que ingrese el nombre de la medicina
            String medicine = sc.next(); // Lee el nombre de la medicina ingresada por el usuario
            System.out.println("Ingrese cantidad a comprar"); // Solicita al usuario que ingrese la cantidad de medicina a comprar
            int amount = sc.nextInt(); // Lee la cantidad de medicina ingresada por el usuario

            // Realiza la compra de la medicina especificada por el usuario
            MedicineInterface aux = pharm.buyMedicine(medicine, amount);

            // Muestra un mensaje de confirmación y los detalles de la medicina comprada
            System.out.println("Usted acaba de comprar");
            System.out.println(aux.print());
        }
        // Si la opción seleccionada no es válida, muestra un mensaje de error
        else {
            System.out.println("Seleccione una opcion valida");
        }

        sc.close(); // Cierra el Scanner para liberar recursos
    }
}
