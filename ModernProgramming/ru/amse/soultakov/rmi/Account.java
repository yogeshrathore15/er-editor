package ru.amse.soultakov.rmi;

import java.rmi.*;

/**
 * Данный интерфейс представляет счет, который характеризуется уникальным
 * идентификатором, текущей суммой на счете, а также данными о владельце. Данный
 * интерфейс расширяет интерфейс <code>Remote</code>, что позволяет его
 * использовать при выполнении удаленных методов (RMI).
 * 
 * @author Soultakov Maxim
 * 
 */
public interface Account extends Remote {

    /**
     * Возвращает уникальный идентификатор счета
     * 
     * @return уникальный идентификатор счета
     * @throws RemoteException
     *             если возникает проблема с удаленным вызовом метода
     */
    public String getId() throws RemoteException;

    /**
     * Возвращает текущее количество денег на счету
     * 
     * @return текущее количество денег на счету
     * @throws RemoteException
     *             если возникает проблема с удаленным вызовом метода
     */
    public int getAmount() throws RemoteException;

    /**
     * Устанавливает текущее количество денег на счету
     * 
     * @param amount
     *            количество денег
     * @throws RemoteException
     *             если возникает проблема с удаленным вызовом метода
     */
    public void setAmount(int amount) throws RemoteException;

    /**
     * Возвращает данные о владельце, инкапсулированные в сериализуемом классе
     * 
     * @return дынные о владельце
     * @throws RemoteException
     *             если возникает проблема с удаленным вызовом метода
     */
    public LocalPerson getLocalPerson() throws RemoteException;

    /**
     * Возвращает данные о владельце, инкапсулированные в классе, который может
     * использоваться при удаленном вызове методов (RMI).
     * 
     * @return данные о владельце
     * @throws RemoteException
     *             если возникает проблема с удаленным вызовом метода
     */
    public RemotePerson getRemotePerson() throws RemoteException;

    /**
     * Устанавливает данные о владельце
     * 
     * @param firstName
     *            имя владельца
     * @param lastName
     *            фамилия владельца
     * @param password
     *            пароль доступа
     * @throws RemoteException
     *             если возникает проблема с удаленным вызовом метода
     */
    public void setData(String firstName, String lastName, String password)
            throws RemoteException;

}