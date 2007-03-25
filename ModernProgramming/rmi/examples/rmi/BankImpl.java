package rmi.examples.rmi;

import java.util.*;
import java.rmi.server.*;
import java.rmi.*;

/**
 * ������ ����� �������� ����������� ���������� <code>Bank</code>,
 * ��������������� ������� ����� � ��������������� ������������� � ���������
 * ������� �������.
 * 
 * @author Soultakov Maxim
 */
public class BankImpl implements Bank {

    /**
     * ��������� ��� ������ ��������. ������� ����������� ��������������
     * �������������� ����
     */
    private final Map<String, Account> accounts = new HashMap<String, Account>();

    /**
     * ������� ���� � ��������� �����������.
     * 
     * @param id
     *            ���������� ����� �����
     * @param firstName
     *            ��� ���������
     * @param lastName
     *            ������� ���������
     * @param password
     *            ������
     * @return ��������� ����
     */
    public Account createAccount(String id, String firstName, String lastName,
            String password) throws RemoteException {
        Account account = new AccountImpl(id, firstName, lastName, password);
        accounts.put(id, account);
        UnicastRemoteObject.exportObject(account);
        return account;
    }

    /**
     * ���������� ������������ ���� ��� <code>null</code>, ���� ���� �
     * �������� ���������� ������� �� ����������
     * 
     * @param id
     *            ���������� ����� �����
     * @return ������������ ���� ��� <code>null</code>, ���� ���� � ��������
     *         ���������� ������� �� ����������
     */
    public Account getAccount(String id) {
        return accounts.get(id);
    }
    
}
