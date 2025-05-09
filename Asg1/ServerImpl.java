import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements ServerIntf {
    public ServerImpl() throws RemoteException {
        super();
    }

    @Override
    public double addition(double num1, double num2) throws RemoteException {
        return num1 + num2;
    }

    @Override
    public double subtraction(double num1, double num2) throws RemoteException {
        return num1 - num2;
    }

    @Override
    public double multiplication(double num1, double num2) throws RemoteException {
        return num1 * num2;
    }

    @Override
    public double division(double num1, double num2) throws RemoteException {
        if (num2 == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return num1 / num2;
    }
}
