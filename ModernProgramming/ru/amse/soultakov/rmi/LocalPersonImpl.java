/*
 * Created on 13.03.2007
 */
package ru.amse.soultakov.rmi;

public class LocalPersonImpl implements LocalPerson {

    private String firstName;
    
    private String lastName;
    
    private transient String password;
    
    public LocalPersonImpl(String firstName, String lastName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    

}
