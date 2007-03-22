/*
 * Created on 14.11.2006
 */
package ru.amse.soultakov.swingtasks.tables;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel{

    private int columnCount;
    private int rowCount;
    
    public MyTableModel(int columnCount, int rowCount) {
        this.columnCount = columnCount;
        this.rowCount = rowCount;
    }
    
    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount + 1;
    }
    
    @Override
    public String getColumnName(int column) {
        return String.valueOf(column);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowIndex*columnIndex;
    }

    /**
     * @param columnCount the columnCount to set
     */
    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
        fireTableStructureChanged();
    }

    /**
     * @param rowCount the rowCount to set
     */
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
        fireTableDataChanged();
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

}
