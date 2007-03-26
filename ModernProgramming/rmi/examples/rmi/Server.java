package examples.rmi;

import java.rmi.*;
import java.rmi.server.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        Bank bank = new BankImpl();
        try {
            UnicastRemoteObject.exportObject(bank);
            Naming.rebind("rmi://localhost/bank", bank);
        } catch (RemoteException e) {
            System.out.println("Cannot export object: " + e.getMessage());
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL");
        }
    }
}
