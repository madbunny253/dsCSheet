import java.rmi.Naming;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            String serverURL = "rmi://localhost/Server";
            ServerIntf serverIntf = (ServerIntf) Naming.lookup(serverURL);

            System.out.print("Enter First Number: ");
            double num1 = sc.nextDouble();
            System.out.print("Enter Second Number: ");
            double num2 = sc.nextDouble();

            System.out.println("\n-------- Results --------");
            System.out.println("Addition: " + serverIntf.addition(num1, num2));
            System.out.println("Subtraction: " + serverIntf.subtraction(num1, num2));
            System.out.println("Multiplication: " + serverIntf.multiplication(num1, num2));
            System.out.println("Division: " + serverIntf.division(num1, num2));

        } catch (Exception e) {
            System.out.println("Exception occurred at Client: " + e);
        } finally {
            sc.close();
        }
    }
}
