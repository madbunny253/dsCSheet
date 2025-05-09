import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerIntf extends Remote {
    double addition(double num1, double num2) throws RemoteException;
    double subtraction(double num1, double num2) throws RemoteException;
    double multiplication(double num1, double num2) throws RemoteException;
    double division(double num1, double num2) throws RemoteException;
}
