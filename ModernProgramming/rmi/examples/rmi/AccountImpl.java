package rmi.examples.rmi;

import java.rmi.*;

/**
 * Данный класс является реализацией интерфейса <code>Account</code>,
 * представляющего данные о банковском счете и поддерживающего использование в
 * удаленных вызовах методов.
 * 
 * @author Soultakov Maxim
 */
public class AccountImpl implements Account {

    /**
     * Уникальный номер счета
     */
    private final String id;

    /**
     * Текущее количество денег на счете
     */
    private int amount;

    /**
     * Имя владельца
     */
    private String firstName;

    /**
     * Фамилия владельца
     */
    private String lastName;

    /**
     * Пароль владельца
     */
    private String password;

    /**
     * Создает счет с указанными уникальным номером и именем, фамилией и паролем
     * владельца. Количество денег на счету равно 0.
     * 
     * @param id
     *            уникальный номер счета
     * @param firstName
     *            имя владельца
     * @param lastName
     *            фамилия владельца
     * @param password
     *            пароль владельца
     */
    public AccountImpl(String id, String firstName, String lastName, String password) {
        this.id = id;
        amount = 0;
    }

    public String getId() {
        return id;
    }

    /**
     * Возвращает текущее количество денег на счету
     * 
     * @return текущее количество денег на счету
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Устанавливает текущее количество денег на счету
     * 
     * @param amount
     *            количество денег
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Возвращает данные о владельце, инкапсулированные в сериализуемом классе
     * 
     * @return дынные о владельце
     */
    public LocalPerson getLocalPerson() throws RemoteException {
        return new LocalPersonImpl(firstName, lastName, password);
    }

    /**
     * Возвращает данные о владельце, инкапсулированные в классе, который может
     * использоваться при удаленном вызове методов (RMI).
     * 
     * @return данные о владельце
     */
    public RemotePerson getRemotePerson() throws RemoteException {
        return new RemotePersonImpl(firstName, lastName, password);
    }

    /**
     * Устанавливает данные о владельце
     * 
     * @param firstName
     *            имя владельца
     * @param lastName
     *            фамилия владельца
     * @param password
     *            пароль доступа
     */
    public void setData(String firstName, String lastName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

}