package SisTarjetas;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// Interfaz remota que define los métodos que pueden ser invocados de forma remota
public interface TarjetaInterfaz extends Remote {
    
    // Método para obtener el saldo de la tarjeta
    double saldo() throws RemoteException;
    
    // Método para realizar un depósito en la tarjeta
    void deposito(double monto) throws RemoteException;
    
    // Método para realizar una compra con la tarjeta
    void compra(double monto) throws RemoteException;
    
    // Método para realizar un retiro de la tarjeta
    void retiro(double monto) throws RemoteException;
    
    // Método para obtener el estado de cuenta de la tarjeta
    List<String> estadoCuenta() throws RemoteException;
}
