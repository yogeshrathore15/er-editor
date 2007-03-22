package examples.rmi;

import java.rmi.*;

public interface Account extends Remote {
    // ������ �����䨪���
    public String getId() 
        throws RemoteException;

    // ������ ������⢮ �����
    public int getAmount() 
        throws RemoteException;

    // ��⠭����� ������⢮ �����
    public void setAmount(int amount) 
        throws RemoteException;
}