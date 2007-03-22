/**
 * 
 */
package rmi.examples.rmi;


/**
 * @author sma
 *
 */
public class RemotePersonImpl implements RemotePerson
{

	private final String firstName;
	private final String lastName;
	private final String password;
	
	public RemotePersonImpl(final String firstName, final String lastName, final String password)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getPassword()
	{
		return password;
	}

}
