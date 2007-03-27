package examples.rmi;

import java.rmi.*;

public interface Bank extends Remote {
    // ������� ���
    public Account createAccount(String id) 
        throws RemoteException;
    // �����頥� ���
    public Account getAccount(String id) 
        throws RemoteException;
}
