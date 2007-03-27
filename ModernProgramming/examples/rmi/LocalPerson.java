package examples.rmi;

import java.io.Serializable;

/**
 * The LocalPerson class it's Person implimentation that extends serializable
 * interface
 * 
 * @author Ivankov Vladimir
 * 
 */
public class LocalPerson implements Serializable, Person {

	private static final long serialVersionUID = -3001193970119585339L;

	// Person's firstName
	private String firstName;

	// Person's lastName
	private String lastName;

	// Person's password not to serialize, this is private data
	private transient String password;

	/**
	 * creates LocalPerson
	 */
	public LocalPerson() {

	}

	/**
	 * return person's firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * return person's lastName
	 */
	public String getLastName() {

		return lastName;
	}

	/**
	 * return person's password
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
