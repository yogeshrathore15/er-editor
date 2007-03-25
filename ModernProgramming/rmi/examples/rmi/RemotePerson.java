/*
 * Created on 13.03.2007
 */
package rmi.examples.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Интерфейс, представляющий данные о владельце банковского счета. Данный
 * интерфейс расширяет интерфейс <code>Remote</code>, что позволяет
 * сериализовать классы, его сериализующие
 * 
 * @author Soultakov Maxim
 */
public interface RemotePerson extends Remote {

    /**
     * Возвращает имя владельца
     * 
     * @return имя владельца
     */
    public String getFirstName() throws RemoteException;

    /**
     * Возвращает пароль владельца
     * 
     * @return пароль владельца
     */
    public String getPassword() throws RemoteException;

    /**
     * Возвращает фамилию владельца
     * 
     * @return фамилию владельца
     */
    public String getLastName() throws RemoteException;
}
