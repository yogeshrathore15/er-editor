/**
 * 
 */
package ru.amse.soultakov.rmi;

/**
 * Класс, представляющий данные о владельце счета. Класс поддерживает
 * RMI
 * 
 * @author Soultakov Maxim
 * 
 */
public class RemotePersonImpl implements RemotePerson {

    /**
     * Имя владельца
     */
    private final String firstName;

    /**
     * Фамилия владельца
     */
    private final String lastName;

    /**
     * Пароль владельца
     */
    private final String password;

    /**
     * Создает владельца с указанными именеи, фамилией и паролем.
     * 
     * @param firstName
     *            имя пользователя
     * @param lastName
     *            фамилия пользователя
     * @param password
     *            пароль
     */
    public RemotePersonImpl(final String firstName, final String lastName,
            final String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    /**
     * Возвращает имя владельца
     * 
     * @return имя владельца
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Возвращает фамилию владельца
     * 
     * @return фамилию владельца
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Возвращает пароль владельца
     * 
     * @return пароль владельца
     */
    public String getPassword() {
        return password;
    }

}
