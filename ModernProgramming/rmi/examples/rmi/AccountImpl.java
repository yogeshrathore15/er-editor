package rmi.examples.rmi;

import java.rmi.*;

/**
 * @author sma
 *
 */
public class AccountImpl implements Account {
   
    private final String id;
    private int amount;
    
    private String firstName;
    private String lastName;
    private String password;

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
        return new LocalPersonImpl(firstName, lastName, password);
    }

    public RemotePerson getRemotePerson() throws RemoteException {
        return new RemotePersonImpl(firstName, lastName, password);
    }

	public void setData(String firstName, String lastName, String password)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}
    
}