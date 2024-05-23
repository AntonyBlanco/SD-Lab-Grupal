package Resuelto;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// Implementación de la interfaz remota, extiende UnicastRemoteObject
public class CalculatorImpl extends UnicastRemoteObject implements Calculator {
    // Constructor explícito que lanza RemoteException
    public CalculatorImpl() throws RemoteException {
        super();
    }

    // Implementación de los métodos remotos
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }

    public int sub(int a, int b) throws RemoteException {
        return a - b;
    }

    public int mul(int a, int b) throws RemoteException {
        return a * b;
    }

    public int div(int a, int b) throws RemoteException {
        return a / b;
    }
}
