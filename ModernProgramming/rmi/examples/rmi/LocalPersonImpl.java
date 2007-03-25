/*
 * Created on 13.03.2007
 */
package rmi.examples.rmi;

/**
 * Класс, представляющий данные о владельце счета. Класс поддерживает
 * сериализацию
 * 
 * @author Soultakov Maxim
 */
public class LocalPersonImpl implements LocalPerson {

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
     * Создает владельца с указанными именеи, фамилией и паролем.
     * 
     * @param firstName
     *            имя пользователя
     * @param lastName
     *            фамилия пользователя
     * @param password
     *            пароль
     */
    public LocalPersonImpl(String firstName, String lastName, String password) {
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
