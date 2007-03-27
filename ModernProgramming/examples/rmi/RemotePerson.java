package examples.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * The RemotePerson class it's Person implimentation that extends remote
 * interface
 * 
 * @author Soultakov Maxim
 *
 */
public class RemotePerson implements Remote, Person {
	// Person's firstName
	private String firstName;

	// Person's lastName
	private String lastName;

	// Person's password
	private String password;

	/**
	 * creates RemotePerson
	 * 
	 * @throws RemoteException
	 *             if can't export object
	 */
	public RemotePerson(String firstName, String lastName, String password) throws RemoteException {
		setPersonData(firstName, lastName, password);
		UnicastRemoteObject.exportObject(this);
	}

	/**
	 * @return person's firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return person's lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return person's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param firstName
	 *            firstname to set
	 * @param lastName
	 *            lastname to set
	 * @param password
	 *            password to set
	 */
	public void setPersonData(String firstName, String lastName, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}
	
}
