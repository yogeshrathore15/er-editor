package ru.amse.soultakov.rmi;

import java.rmi.*;

/**
 * ������ ��������� ������������� ���������������� �����. �������� �������
 * �������� � ��������� ����� �� ��������� ����������� ������. ����� ���������
 * ��������� ��������� <code>Remote</code>, ��� ��������� ��� ������������
 * ��� ���������� ��������� ������� (RMI).
 * 
 * @author Soultakov Maxim
 */
public interface Bank extends Remote {

    /**
     * ������� ���� � ��������� �����������.
     * 
     * @param id
     *            ���������� ����� �����
     * @param firstName
     *            ��� ���������
     * @param lastName
     *            ������� ���������
     * @param password
     *            ������
     * @return ��������� ����
     * @throws RemoteException
     *             ���� ��������� �������� � ��������� ������� ������
     */
    public Account createAccount(String id, String firstName, String lastName,
            String password) throws RemoteException;

    /**
     * ���������� ������������ ���� ��� <code>null</code>, ���� ���� �
     * �������� ���������� ������� �� ����������
     * 
     * @param id
     *            ���������� ����� �����
     * @return ������������ ���� ��� <code>null</code>, ���� ���� � ��������
     *         ���������� ������� �� ����������
     * @throws RemoteException
     *             ���� ��������� �������� � ��������� ������� ������
     */
    public Account getAccount(String id) throws RemoteException;
}
