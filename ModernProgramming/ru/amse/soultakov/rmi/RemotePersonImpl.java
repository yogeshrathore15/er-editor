/**
 * 
 */
package ru.amse.soultakov.rmi;

/**
 * �����, �������������� ������ � ��������� �����. ����� ������������
 * RMI
 * 
 * @author Soultakov Maxim
 * 
 */
public class RemotePersonImpl implements RemotePerson {

    /**
     * ��� ���������
     */
    private final String firstName;

    /**
     * ������� ���������
     */
    private final String lastName;

    /**
     * ������ ���������
     */
    private final String password;

    /**
     * ������� ��������� � ���������� ������, �������� � �������.
     * 
     * @param firstName
     *            ��� ������������
     * @param lastName
     *            ������� ������������
     * @param password
     *            ������
     */
    public RemotePersonImpl(final String firstName, final String lastName,
            final String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    /**
     * ���������� ��� ���������
     * 
     * @return ��� ���������
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * ���������� ������� ���������
     * 
     * @return ������� ���������
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * ���������� ������ ���������
     * 
     * @return ������ ���������
     */
    public String getPassword() {
        return password;
    }

}
