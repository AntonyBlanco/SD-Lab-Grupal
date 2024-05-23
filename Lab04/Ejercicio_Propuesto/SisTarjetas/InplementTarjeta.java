package SisTarjetas;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

// Clase que implementa la interfaz remota TarjetaInterfaz y proporciona la funcionalidad para operar con una tarjeta
public class InplementTarjeta extends UnicastRemoteObject implements TarjetaInterfaz {

    // Atributos para el saldo y el estado de cuenta de la tarjeta
    private double saldo;
    private List<String> estCuenta;

    // Constructor que inicializa el saldo de la tarjeta y el estado de cuenta
    public InplementTarjeta() throws RemoteException {
        saldo = 5000.0; // Inicializa el saldo en 5000.0
        estCuenta = new ArrayList<>(); // Inicializa la lista de transacciones vacía
    }

    // Método para obtener el saldo de la tarjeta
    @Override
    public double saldo() throws RemoteException {
        return saldo;
    }

    // Método para realizar un depósito en la tarjeta
    @Override
    public void deposito(double monto) throws RemoteException {
        saldo += monto; // Incrementa el saldo con el monto del depósito
        estCuenta.add("Depósito realizado: +" + monto); // Agrega una transacción al estado de cuenta
    }

    // Método para realizar una compra con la tarjeta
    @Override
    public void compra(double monto) throws RemoteException {
        if (monto <= saldo) { // Verifica si hay suficiente saldo para realizar la compra
            saldo -= monto; // Reduce el saldo por el monto de la compra
            estCuenta.add("Compra realizada: -" + monto); // Agrega una transacción al estado de cuenta
        }
    }

    // Método para realizar un retiro de la tarjeta
    @Override
    public void retiro(double monto) throws RemoteException {
        if (monto <= saldo) { // Verifica si hay suficiente saldo para realizar el retiro
            saldo -= monto; // Reduce el saldo por el monto del retiro
            estCuenta.add("Retiro realizado: -" + monto); // Agrega una transacción al estado de cuenta
        }
    }

    // Método para obtener el estado de cuenta de la tarjeta
    @Override
    public List<String> estadoCuenta() throws RemoteException {
        return new ArrayList<>(estCuenta); // Devuelve una copia de la lista de transacciones
    }
}
