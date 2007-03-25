package rmi.examples.rmi;

import java.rmi.*;

/**
 * ������ ����� �������� ����������� ���������� <code>Account</code>,
 * ��������������� ������ � ���������� ����� � ��������������� ������������� �
 * ��������� ������� �������.
 * 
 * @author Soultakov Maxim
 */
public class AccountImpl implements Account {

    /**
     * ���������� ����� �����
     */
    private final String id;

    /**
     * ������� ���������� ����� �� �����
     */
    private int amount;

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
     * ������� ���� � ���������� ���������� ������� � ������, �������� � �������
     * ���������. ���������� ����� �� ����� ����� 0.
     * 
     * @param id
     *            ���������� ����� �����
     * @param firstName
     *            ��� ���������
     * @param lastName
     *            ������� ���������
     * @param password
     *            ������ ���������
     */
    public AccountImpl(String id, String firstName, String lastName, String password) {
        this.id = id;
        amount = 0;
    }

    public String getId() {
        return id;
    }

    /**
     * ���������� ������� ���������� ����� �� �����
     * 
     * @return ������� ���������� ����� �� �����
     */
    public int getAmount() {
        return amount;
    }

    /**
     * ������������� ������� ���������� ����� �� �����
     * 
     * @param amount
     *            ���������� �����
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * ���������� ������ � ���������, ����������������� � ������������� ������
     * 
     * @return ������ � ���������
     */
    public LocalPerson getLocalPerson() throws RemoteException {
        return new LocalPersonImpl(firstName, lastName, password);
    }

    /**
     * ���������� ������ � ���������, ����������������� � ������, ������� �����
     * �������������� ��� ��������� ������ ������� (RMI).
     * 
     * @return ������ � ���������
     */
    public RemotePerson getRemotePerson() throws RemoteException {
        return new RemotePersonImpl(firstName, lastName, password);
    }

    /**
     * ������������� ������ � ���������
     * 
     * @param firstName
     *            ��� ���������
     * @param lastName
     *            ������� ���������
     * @param password
     *            ������ �������
     */
    public void setData(String firstName, String lastName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

}