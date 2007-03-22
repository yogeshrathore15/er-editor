package ru.amse.soultakov.rmi;

import java.rmi.*;

public class AccountImpl implements Account {
   
    private final String id;
    private int amount;

    public AccountImpl(String id) {
        this.id = id;
        amount = 0;
    }
    
    public String getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalPerson getLocalPerson() throws RemoteException {
        return null;
    }

    public RemotePerson getRemotePerson() throws RemoteException {
        return null;
    }
    
}