package ru.amse.soultakov.rmi;

import java.rmi.*;

/**
 * ������ ��������� ������������ ����, ������� ��������������� ����������
 * ���������������, ������� ������ �� �����, � ����� ������� � ���������. ������
 * ��������� ��������� ��������� <code>Remote</code>, ��� ��������� ���
 * ������������ ��� ���������� ��������� ������� (RMI).
 * 
 * @author Soultakov Maxim
 * 
 */
public interface Account extends Remote {

    /**
     * ���������� ���������� ������������� �����
     * 
     * @return ���������� ������������� �����
     * @throws RemoteException
     *             ���� ��������� �������� � ��������� ������� ������
     */
    public String getId() throws RemoteException;

    /**
     * ���������� ������� ���������� ����� �� �����
     * 
     * @return ������� ���������� ����� �� �����
     * @throws RemoteException
     *             ���� ��������� �������� � ��������� ������� ������
     */
    public int getAmount() throws RemoteException;

    /**
     * ������������� ������� ���������� ����� �� �����
     * 
     * @param amount
     *            ���������� �����
     * @throws RemoteException
     *             ���� ��������� �������� � ��������� ������� ������
     */
    public void setAmount(int amount) throws RemoteException;

    /**
     * ���������� ������ � ���������, ����������������� � ������������� ������
     * 
     * @return ������ � ���������
     * @throws RemoteException
     *             ���� ��������� �������� � ��������� ������� ������
     */
    public LocalPerson getLocalPerson() throws RemoteException;

    /**
     * ���������� ������ � ���������, ����������������� � ������, ������� �����
     * �������������� ��� ��������� ������ ������� (RMI).
     * 
     * @return ������ � ���������
     * @throws RemoteException
     *             ���� ��������� �������� � ��������� ������� ������
     */
    public RemotePerson getRemotePerson() throws RemoteException;

    /**
     * ������������� ������ � ���������
     * 
     * @param firstName
     *            ��� ���������
     * @param lastName
     *            ������� ���������
     * @param password
     *            ������ �������
     * @throws RemoteException
     *             ���� ��������� �������� � ��������� ������� ������
     */
    public void setData(String firstName, String lastName, String password)
            throws RemoteException;

}