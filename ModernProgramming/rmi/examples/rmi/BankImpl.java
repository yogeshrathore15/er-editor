package rmi.examples.rmi;

import java.util.*;
import java.rmi.server.*;
import java.rmi.*;

public class BankImpl implements Bank {
    private final Map<String, Account> accounts = new HashMap<String, Account>();

    // ������� ���
    public Account createAccount(String id) throws RemoteException {
        Account account = new AccountImpl(id);
        accounts.put(id, account);
        UnicastRemoteObject.exportObject(account);
        return account;
    }

    // �����頥� ���
    public Account getAccount(String id) {
        return accounts.get(id);
    }
}
