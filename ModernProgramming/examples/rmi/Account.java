package examples.rmi;

import java.rmi.*;

public interface Account extends Remote {
	// Узнать идентификатор
	public String getId() throws RemoteException;

	// Узнать количество денег
	public int getAmount() throws RemoteException;

	// Установить количество денег
	public void setAmount(int amount) throws RemoteException;

	public LocalPerson getLocalPerson() throws RemoteException;

	public RemotePerson getRemotePerson() throws RemoteException;

	public void setPersonData(String firstName, String lastName, String password)
			throws RemoteException;
}