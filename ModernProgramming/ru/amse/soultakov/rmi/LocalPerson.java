/*
 * Created on 13.03.2007
 */
package ru.amse.soultakov.rmi;

import java.io.Serializable;

/**
 * ���������, �������������� ������ � ��������� ����������� �����. ������
 * ��������� ��������� ��������� <code>Serializable</code>, ��� ���������
 * ������������� ������, ��� �����������.
 * 
 * @author Soultakov Maxim
 */
public interface LocalPerson extends Serializable {

    /**
     * ���������� ��� ���������
     * 
     * @return ��� ���������
     */
    public String getFirstName();

    /**
     * ���������� ������ ���������
     * 
     * @return ������ ���������
     */
    public String getPassword();

    /**
     * ���������� ������� ���������
     * 
     * @return ������� ���������
     */
    public String getLastName();

}
