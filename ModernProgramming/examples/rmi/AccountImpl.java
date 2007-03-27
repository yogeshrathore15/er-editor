package examples.rmi;

import java.rmi.*;

public class AccountImpl implements Account {
	private final String id;

	private int amount;

	private LocalPerson localPerson;

	private RemotePerson remotePerson;

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
		if (localPerson == null) {
			localPerson = new LocalPerson();
		}
		return localPerson;
	}

	public RemotePerson getRemotePerson() throws RemoteException {
		if (remotePerson == null) {
			remotePerson = new RemotePerson();
		}

		return remotePerson;
	}

	public void setPersonData(String firstName, String lastName, String password)
			throws RemoteException {
		if (localPerson == null) {
			localPerson = new LocalPerson();
		}
		if (remotePerson == null) {
			remotePerson = new RemotePerson();
		}

		localPerson.setPersonData(firstName, lastName, password);
		remotePerson.setPersonData(firstName, lastName, password);

	}
}