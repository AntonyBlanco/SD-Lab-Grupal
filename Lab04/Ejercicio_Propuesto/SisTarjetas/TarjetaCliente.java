package SisTarjetas;

import java.rmi.Naming;
import java.util.Scanner;
import java.util.List;

// Clase que representa un cliente para interactuar con el sistema de tarjetas
public class TarjetaCliente {
    public static void main(String[] args) {
        try {
            // Se busca la referencia al objeto remoto del servicio de tarjetas
            TarjetaInterfaz creditCardService = (TarjetaInterfaz) Naming.lookup("CreditCardService");
            Scanner scanner = new Scanner(System.in);
            int opcion;
            // Menú de opciones para interactuar con la tarjeta
            do {
                System.out.println("Seleccione una opción:");
                System.out.println("1. Ver saldo");
                System.out.println("2. Recarga");
                System.out.println("3. Compras");
                System.out.println("4. Retiro");
                System.out.println("5. Ver estado de cuenta");
                System.out.println("6. Salir");
                opcion = scanner.nextInt();
                switch (opcion) {
                    case 1:
                        // Obtiene y muestra el saldo actual de la tarjeta
                        double saldo = creditCardService.saldo();
                        System.out.println("Saldo actual: " + saldo);
                        break;
                    case 2:
                        // Realiza una recarga en la tarjeta
                        System.out.println("Ingrese la cantidad a depositar: ");
                        double recarga = scanner.nextDouble();
                        creditCardService.deposito(recarga);
                        System.out.println("Depósito realizado");
                        break;
                    case 3:
                        // Realiza una compra con la tarjeta
                        System.out.println("Ingrese el monto de la compra: ");
                        double montoCompra = scanner.nextDouble();
                        creditCardService.compra(montoCompra);
                        System.out.println("Compra confirmada");
                        break;
                    case 4:
                        // Realiza un retiro de la tarjeta
                        System.out.println("Ingrese la cantidad a retirar: ");
                        double montoRetiro = scanner.nextDouble();
                        creditCardService.retiro(montoRetiro);
                        System.out.println("Retiro confirmado");
                        break;
                    case 5:
                        // Obtiene y muestra el estado de cuenta de la tarjeta
                        List<String> historial = creditCardService.estadoCuenta();
                        System.out.println("Estado de cuenta:");
                        for (String transaccion : historial) {
                            System.out.println(transaccion);
                        }
                        break;
                    case 6:
                        // Sale del sistema
                        System.out.println("Saliendo del Sistema");
                        break;
                    default:
                        // Opción no válida
                        System.out.println("Opción no permitida");
                }
            } while (opcion != 6);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
