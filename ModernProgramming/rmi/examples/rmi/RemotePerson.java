/*
 * Created on 13.03.2007
 */
package rmi.examples.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemotePerson extends Remote {

    public String getFirstName() throws RemoteException;
    
    public String getPassword() throws RemoteException;
    
    public String getLastName() throws RemoteException;
}
