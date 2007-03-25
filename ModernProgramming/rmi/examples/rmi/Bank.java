package rmi.examples.rmi;

import java.rmi.*;

/**
 * ƒанный интерфейс предоставл€ет функциональность банка. ƒоступны функции
 * создани€ и получени€ счета по заданному уникальному номеру. анный интерфейс
 * расшир€ет интерфейс <code>Remote</code>, что позвол€ет его использовать
 * при выполнении удаленных методов (RMI).
 * 
 * @author Soultakov Maxim
 */
public interface Bank extends Remote {

    /**
     * —оздает счет с заданными параметрами.
     * 
     * @param id
     *            уникальный номер счета
     * @param firstName
     *            им€ владельца
     * @param lastName
     *            фамили€ владельца
     * @param password
     *            пароль
     * @return созданный счет
     * @throws RemoteException
     *             если возникает проблема с удаленным вызовом метода
     */
    public Account createAccount(String id, String firstName, String lastName,
            String password) throws RemoteException;

    /**
     * ¬озвращает существующий счет или <code>null</code>, если счет с
     * указаным уникальным номером не существует
     * 
     * @param id
     *            уникальный номер счета
     * @return существующий счет или <code>null</code>, если счет с указаным
     *         уникальным номером не существует
     * @throws RemoteException
     *             если возникает проблема с удаленным вызовом метода
     */
    public Account getAccount(String id) throws RemoteException;
}
