/*
 * Created on 13.03.2007
 */
package rmi.examples.rmi;

import java.io.Serializable;

public interface LocalPerson extends Serializable {

    public String getFirstName();
    
    public String getPassword();
    
    public String getLastName();
    
}
