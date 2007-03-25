package ru.amse.soultakov.rmi;

import java.rmi.*;
import java.net.*;

/**
 * ������ ����� ������������� ������ � ���������� �������� ������� �� �������
 * ����� � ������. �� ������ ���� �������, �������������� ���������� � �������
 * 
 * @author Soultakov Maxim
 */
public class Client {

    /**
     * ������� ������� ���������
     * 
     * @param args
     *            ��������� ��������� ������(� ������ ���������� ��
     *            ������������)
     * @throws RemoteException
     *             ���� ��������� �������� � ��������� ������� ������
     */
    public static void main(String[] args) throws RemoteException {
        Bank bank;
        try {
            bank = (Bank) Naming.lookup("rmi://localhost/bank");
        } catch (NotBoundException e) {
            System.out.println("Bank URL is invalid");
            return;
        } catch (MalformedURLException e) {
            System.out.println("Bank is not bound");
            return;
        }
        Account account = bank.getAccount("geo");
        if (account == null) {
            System.out.println("Creating account");
            account = bank.createAccount("geo", "����","������","password");
        } else {
            System.out.println("Account already exists");
        }
        System.out.println("Local person:");
        LocalPerson localPerson = account.getLocalPerson();
        System.out.println("FirstName : " + localPerson.getFirstName());
        System.out.println("LastName : " + localPerson.getLastName());
        System.out.println("--------");
        System.out.println("Remote person:");
//        RemotePerson remotePerson = account.getRemotePerson();
//        System.out.println("FirstName : " + remotePerson.getFirstName());
//        System.out.println("LastName : " + remotePerson.getLastName());
//        System.out.println("--------");
        System.out.println("Money: " + account.getAmount());
        System.out.println("Adding money");
        account.setAmount(account.getAmount() + 100);
        System.out.println("Money: " + account.getAmount());
    }
}
