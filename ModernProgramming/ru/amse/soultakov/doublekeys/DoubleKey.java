/*
 * Created on 05.10.2006
 */
package ru.amse.soultakov.doublekeys;

/**
 * Класс <code>DoubleKey</code> позволяет хранить пару объектов типа
 * <code>Object</code>, в частности для использования "парного" ключа в отображениях 
 * <code>java.util.Map</code>
 * @author  Soultakov Maxim
 * @version 1.0
 */
public class DoubleKey {
    
    private Object firstField;
    private Object secondField;
    
    /**
     * Конструктор для создания "парного" ключа
     * @param firstField - первое поле
     * @param secondField - второе поле
     */
    public DoubleKey(Object firstField, Object secondField) {
        this.firstField = firstField;
        this.secondField = secondField;
    }

    /**
     * Метод для получения значения первого поля
     * @return значение первого поля
     */
    public Object getFirstField() {
        return firstField;
    }

    /**
     * Метод для получения значения второго поля
     * @return значение второго поля
     */
    public Object getSecondField() {
        return secondField;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = PRIME * result + ((firstField == null) ? 0 : firstField.hashCode());
        result = PRIME * result + ((secondField == null) ? 0 : secondField.hashCode());
        return result;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DoubleKey other = (DoubleKey) obj;
        if (firstField == null) {
            if (other.firstField != null) {
                return false;
            }
        } else if (!firstField.equals(other.firstField)) {
            return false;
        }
        if (secondField == null) {
            if (other.secondField != null) {
                return false;
            }
        } else if (!secondField.equals(other.secondField)) {
            return false;
        }
        return true;
    }
        
}
