/*
 * Created on 13.03.2007
 */
package rmi.examples.rmi;

/**
 * �����, �������������� ������ � ��������� �����. ����� ������������
 * ������������
 * 
 * @author Soultakov Maxim
 */
public class LocalPersonImpl implements LocalPerson {

    /**
     * ��� ���������
     */
    private String firstName;

    /**
     * ������� ���������
     */
    private String lastName;

    /**
     * ������ ���������
     */
    private String password;

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
    public LocalPersonImpl(String firstName, String lastName, String password) {
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
