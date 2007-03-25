/*
 * Created on 13.03.2007
 */
package rmi.examples.rmi;

import java.io.Serializable;

/**
 * Интерфейс, представляющий данные о владельце банковского счета. Данный
 * интерфейс расширяет интерфейс <code>Serializable</code>, что позволяет
 * сериализовать классы, его реализующие.
 * 
 * @author Soultakov Maxim
 */
public interface LocalPerson extends Serializable {

    /**
     * Возвращает имя владельца
     * 
     * @return имя владельца
     */
    public String getFirstName();

    /**
     * Возвращает пароль владельца
     * 
     * @return пароль владельца
     */
    public String getPassword();

    /**
     * Возвращает фамилию владельца
     * 
     * @return фамилию владельца
     */
    public String getLastName();

}
