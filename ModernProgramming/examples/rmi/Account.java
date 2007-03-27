package examples.rmi;

import java.rmi.*;

public interface Account extends Remote {
	// ������ �����䨪���
	public String getId() throws RemoteException;

	// ������ ������⢮ �����
	public int getAmount() throws RemoteException;

	// ��⠭����� ������⢮ �����
	public void setAmount(int amount) throws RemoteException;

	public LocalPerson getLocalPerson() throws RemoteException;

	public RemotePerson getRemotePerson() throws RemoteException;

	public void setPersonData(String firstName, String lastName, String password)
			throws RemoteException;
}