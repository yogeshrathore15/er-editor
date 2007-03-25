/*
 * Created on 13.03.2007
 */
package ru.amse.soultakov.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * ���������, �������������� ������ � ��������� ����������� �����. ������
 * ��������� ��������� ��������� <code>Remote</code>, ��� ���������
 * ������������� ������, ��� �������������
 * 
 * @author Soultakov Maxim
 */
public interface RemotePerson extends Remote {

    /**
     * ���������� ��� ���������
     * 
     * @return ��� ���������
     */
    public String getFirstName() throws RemoteException;

    /**
     * ���������� ������ ���������
     * 
     * @return ������ ���������
     */
    public String getPassword() throws RemoteException;

    /**
     * ���������� ������� ���������
     * 
     * @return ������� ���������
     */
    public String getLastName() throws RemoteException;
}
