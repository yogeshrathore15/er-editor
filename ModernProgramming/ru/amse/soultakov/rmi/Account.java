package ru.amse.soultakov.rmi;

import java.rmi.*;

public interface Account extends Remote {

    /**
     * Returns identifier
     * 
     * @return identifier
     * @throws RemoteException
     *             if any RMI problem occured
     */
    public String getId() throws RemoteException;

    /**
     * Returns value in account
     * 
     * @return amount
     * @throws RemoteException
     *             if any RMI problem occured
     */
    public int getAmount() throws RemoteException;

    /**
     * Sets the amount
     * 
     * @param amount
     *            the amount to set
     * @throws RemoteException
     *             if any RMI problem occured
     */
    public void setAmount(int amount) throws RemoteException;
    
    /**
     * @return
     * @throws RemoteException
     */
    public LocalPerson getLocalPerson() throws RemoteException;
    
    /**
     * @return
     * @throws RemoteException
     */
    public RemotePerson getRemotePerson() throws RemoteException;

}