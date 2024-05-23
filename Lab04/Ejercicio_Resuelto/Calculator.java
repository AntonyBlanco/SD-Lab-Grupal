package Resuelto;
import java.rmi.Remote;
import java.rmi.RemoteException;

// Interfaz remota que extiende java.rmi.Remote
public interface Calculator extends Remote {
    // Métodos que pueden ser invocados remotamente, todos lanzan RemoteException
    public int add(int a, int b) throws RemoteException;
    public int sub(int a, int b) throws RemoteException;
    public int mul(int a, int b) throws RemoteException;
    public int div(int a, int b) throws RemoteException;
}
