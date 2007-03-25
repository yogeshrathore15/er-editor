package ru.amse.soultakov.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * ������ ����� ������������� ������ � ���������� �������� ������� �� �������
 * ����� � ������. �� ������ ���� �������, ��������������� ������� �������.
 * 
 * @author Soultakov Maxim
 */
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
