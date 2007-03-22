/*
 * Created on 14.11.2006
 */
package ru.amse.soultakov.swingtasks.tables;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;

public class MainFrame extends JFrame{
    
    private int MAX_VAL = 500;
    private int INIT_VAL = 0;
    private int MIN_VAL = 0;
    
    private JSlider verticalSlider = 
        new JSlider(JSlider.VERTICAL, MIN_VAL, MAX_VAL, INIT_VAL);
    private JSlider horizontalSlider = 
        new JSlider(JSlider.HORIZONTAL, MIN_VAL, MAX_VAL, INIT_VAL);
    
    private MyTableModel model = new MyTableModel(INIT_VAL, INIT_VAL);
    private JTable table = new JTable(model); 
    
    public MainFrame() {
        super("Таблица умножения");
        this.setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(horizontalSlider, BorderLayout.NORTH);
        this.add(verticalSlider, BorderLayout.WEST);
        verticalSlider.setInverted(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.CENTER);
        setupActions();
    }

    private void setupActions() {
        verticalSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                model.setRowCount(verticalSlider.getValue());
            }
        });
        horizontalSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                model.setColumnCount(horizontalSlider.getValue());
            }
        });
        table.setDragEnabled(false);
        table.getTableHeader().setDraggedDistance(0);
        table.getColumnModel().addColumnModelListener(new TableColumnModelListener() {
            
            private volatile boolean isMoving = false;
            
            public void columnAdded(TableColumnModelEvent e) {
                //TableColumn tc = table.getColumnModel().getColumn(e.getToIndex());
                //tc.setPreferredWidth(40);
            }

            public void columnMarginChanged(ChangeEvent e) {
            }

            public void columnMoved(TableColumnModelEvent e) {
                if (isMoving) {
                    return;
                }
                if (e.getFromIndex()<0 || e.getToIndex()<0) {
                    return;
                }
                isMoving = true;
                table.getColumnModel().moveColumn(e.getFromIndex(), e.getToIndex());
                isMoving = false;
            }

            public void columnRemoved(TableColumnModelEvent e) {
            }

            public void columnSelectionChanged(ListSelectionEvent e) {
            }
            
        });
    }
    
    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.pack();
        mf.setVisible(true);
    }
        
}
