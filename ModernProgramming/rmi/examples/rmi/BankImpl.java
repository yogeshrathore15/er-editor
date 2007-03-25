package rmi.examples.rmi;

import java.util.*;
import java.rmi.server.*;
import java.rmi.*;

/**
 * ƒанный класс €вл€етс€ реализацией интерфейса <code>Bank</code>,
 * представл€ющего функции банка и поддерживающего использование в удаленных
 * вызовах методов.
 * 
 * @author Soultakov Maxim
 */
public class BankImpl implements Bank {

    /**
     * ’ранилище дл€ счетов клиентов.  аждому уникальному идентификатору
     * сопоставл€етс€ счет
     */
    private final Map<String, Account> accounts = new HashMap<String, Account>();

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
     */
    public Account createAccount(String id, String firstName, String lastName,
            String password) throws RemoteException {
        Account account = new AccountImpl(id, firstName, lastName, password);
        accounts.put(id, account);
        UnicastRemoteObject.exportObject(account);
        return account;
    }

    /**
     * ¬озвращает существующий счет или <code>null</code>, если счет с
     * указаным уникальным номером не существует
     * 
     * @param id
     *            уникальный номер счета
     * @return существующий счет или <code>null</code>, если счет с указаным
     *         уникальным номером не существует
     */
    public Account getAccount(String id) {
        return accounts.get(id);
    }
    
}
