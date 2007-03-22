/*
 * Created on 06.11.2006
 */
package ru.amse.soultakov.swingtasks.polyclinic;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import ru.amse.soultakov.patient.Patient;

public class PolyclinicFrame extends JFrame {

    private static final int DEFAULT_FIELD_LENGTH = 15;

    private ArrayList<Patient> patients = new ArrayList<Patient>();

    private ActionsStuff actions = new ActionsStuff();

    private Patient currentPatient;

    private int currentIndex;

    private boolean editMode;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "dd.MM.yyyy");

    private JTextField cardNumField = new JTextField(DEFAULT_FIELD_LENGTH);

    private JTextField nameField = new JTextField(DEFAULT_FIELD_LENGTH);

    private JTextField surnameField = new JTextField(DEFAULT_FIELD_LENGTH);

    private JTextField patronymicField = new JTextField(DEFAULT_FIELD_LENGTH);

    private JTextField birthDateField = new JTextField(DEFAULT_FIELD_LENGTH);

    private JTextField doctorField = new JTextField(DEFAULT_FIELD_LENGTH);

    public PolyclinicFrame() {
        super("Поликлиника");
        setupMain();
        setupMenu();
        setupToolBar();
        enableActions(false);
    }

    private void setupToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(actions.openAction);
        toolBar.add(actions.saveCurrent);
        toolBar.add(actions.saveAll);
        toolBar.add(actions.addCard);
        toolBar.add(actions.removeCard);
        toolBar.add(actions.setEditMode);
        toolBar.add(actions.goPrior);
        toolBar.add(actions.goNext);
        add(toolBar, BorderLayout.NORTH);
    }

    private void enableActions(boolean b) {
        actions.removeCard.setEnabled(b);
        actions.saveAll.setEnabled(b);
        actions.saveCurrent.setEnabled(b);
        actions.setEditMode.setEnabled(b);
        actions.goNext.setEnabled(b);
        actions.goPrior.setEnabled(b);
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.add(actions.openAction);
        fileMenu.add(actions.saveCurrent);
        fileMenu.add(actions.saveAll);
        fileMenu.addSeparator();
        JMenuItem menuItem = new JMenuItem(actions.exit);
        fileMenu.add(menuItem);
        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        editMenu.add(actions.addCard);
        editMenu.add(actions.setEditMode);
        editMenu.add(actions.removeCard);
        menuBar.add(editMenu);

        JMenu navigationMenu = new JMenu("Navigation");
        navigationMenu.setMnemonic(KeyEvent.VK_N);
        menuItem = new JMenuItem(actions.goNext);
        navigationMenu.add(menuItem);
        menuItem = new JMenuItem(actions.goPrior);
        navigationMenu.add(menuItem);
        menuBar.add(navigationMenu);

        menuItem = new JMenuItem();

        this.setJMenuBar(menuBar);
    }

    private void setupMain() {
        this.setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel labelsPanel = new JPanel(new GridLayout(0, 1));
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 1));
        labelsPanel.add(new JLabel("Card #"));
        fieldsPanel.add(cardNumField);
        labelsPanel.add(new JLabel("Surname"));
        fieldsPanel.add(surnameField);
        labelsPanel.add(new JLabel("Name"));
        fieldsPanel.add(nameField);
        labelsPanel.add(new JLabel("Patronymic"));
        fieldsPanel.add(patronymicField);
        labelsPanel.add(new JLabel("Birth Date"));
        fieldsPanel.add(birthDateField);
        labelsPanel.add(new JLabel("Doctor"));
        fieldsPanel.add(doctorField);
        mainPanel.add(labelsPanel, BorderLayout.LINE_START);
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        setEditableAll();
        this.add(mainPanel, BorderLayout.CENTER);
    }

    private void setEditableAll() {
        cardNumField.setEditable(editMode);
        nameField.setEditable(editMode);
        surnameField.setEditable(editMode);
        patronymicField.setEditable(editMode);
        birthDateField.setEditable(editMode);
        doctorField.setEditable(editMode);
    }

    private boolean checkFieldsData() {
        boolean result = true;
        try {
            int n = Integer.parseInt(cardNumField.getText());
            if (n <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        result = result && (nameField.getText().length() > 0);
        result = result && (surnameField.getText().length() > 0);
        result = result && (patronymicField.getText().length() > 0);
        try {
            DATE_FORMAT.parse(birthDateField.getText());
        } catch (ParseException e) {
            return false;
        }
        result = result && (doctorField.getText().length() > 0);
        return result;
    }

    private void updateCurrentPatient() {
        currentPatient.setAmbulatoryCardNumber(Integer.parseInt(cardNumField
                .getText()));
        currentPatient.setSurname(surnameField.getText());
        currentPatient.setName(nameField.getText());
        currentPatient.setPatronymic(patronymicField.getText());
        Date date = null;
        try {
            date = DATE_FORMAT.parse(birthDateField.getText());
        } catch (ParseException e) {
        }
        currentPatient.setBirthDate(date);
        currentPatient.setDoctorName(doctorField.getText());
        showCurrentPatient();
    }

    private void showCurrentPatient() {
        Integer cardNum = currentPatient.getAmbulatoryCardNumber();
        cardNumField.setText(cardNum.toString());
        nameField.setText(currentPatient.getName());
        surnameField.setText(currentPatient.getSurname());
        patronymicField.setText(currentPatient.getPatronymic());
        birthDateField.setText(DATE_FORMAT
                .format(currentPatient.getBirthDate()));
        doctorField.setText(currentPatient.getDoctorName());
    }

    private void clearFields() {
        cardNumField.setText("");
        nameField.setText("");
        surnameField.setText("");
        patronymicField.setText("");
        birthDateField.setText("");
        doctorField.setText("");
    }

    private void enableActionsForEditMode(boolean b) {
        actions.addCard.setEnabled(b);
        actions.goNext.setEnabled(b);
        actions.goPrior.setEnabled(b);
        actions.saveAll.setEnabled(b);
        actions.saveCurrent.setEnabled(b);
        actions.removeCard.setEnabled(b);
        actions.openAction.setEnabled(b);
    }

    private class ActionsStuff {

        public final Action exit = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                PolyclinicFrame.this.dispose();
            }
        };

        public final Action goPrior = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (currentIndex > 0) {
                    currentPatient = patients.get(--currentIndex);
                    showCurrentPatient();
                } else {
                    JOptionPane.showMessageDialog(PolyclinicFrame.this,
                            "This patient" + " is the first one", "Error",
                            JOptionPane.ERROR_MESSAGE);

                }
            }
        };

        public final Action goNext = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (currentIndex < patients.size() - 1) {
                    currentPatient = patients.get(++currentIndex);
                    showCurrentPatient();
                } else {
                    JOptionPane.showMessageDialog(PolyclinicFrame.this,
                            "This patient " + " is the last one", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        public final Action setEditMode = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (editMode) {
                    int res = JOptionPane.showConfirmDialog(
                            PolyclinicFrame.this,
                            "Do you really want to save changes?");
                    if (res == JOptionPane.YES_OPTION) {
                        if (!checkFieldsData()) {
                            JOptionPane.showMessageDialog(PolyclinicFrame.this,
                                    "Fields are filled" + " incorrectly",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            updateCurrentPatient();
                            if (adding) {
                                Patient p = new Patient(currentPatient
                                        .getAmbulatoryCardNumber(),
                                        currentPatient.getSurname(),
                                        currentPatient.getName(),
                                        currentPatient.getPatronymic(),
                                        currentPatient.getBirthDate(),
                                        currentPatient.getDoctorName());
                                patients.add(p);
                                currentIndex = patients.size() - 1;
                                adding = false;
                            }
                            editMode = false;
                            setEditMode.putValue(Action.NAME, "Edit Mode");
                            enableActionsForEditMode(true);
                        }
                    } else if (res == JOptionPane.NO_OPTION) {
                        editMode = false;
                        setEditMode.putValue(Action.NAME, "Edit Mode");
                        if (adding) {
                            if (patients.size() > 0) {
                                currentPatient = patients
                                        .get(patients.size() - 1);
                                showCurrentPatient();
                            } else {
                                clearFields();
                            }
                            adding = false;
                        } else {
                            showCurrentPatient();
                        }
                        if (patients.size() > 0) {
                            enableActionsForEditMode(true);
                        }
                    }
                } else {
                    setEditMode.putValue(Action.NAME, "Apply");
                    editMode = true;
                    enableActionsForEditMode(false);
                }
                setEditableAll();
            }
        };

        public final Action openAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("Opening file");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnVal = fileChooser
                        .showOpenDialog(PolyclinicFrame.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (loadPatientsFromFile(file)) {
                        currentIndex = 0;
                        currentPatient = patients.get(currentIndex);
                        showCurrentPatient();
                        editMode = false;
                        setEditableAll();
                        enableActions(true);
                        enableActionsForEditMode(true);
                    } else {
                        JOptionPane.showMessageDialog(PolyclinicFrame.this,
                                "It's impossible"
                                        + "to read data from selected file",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        ;
                    }
                }
            }
        };

        public final Action saveCurrent = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
                fileChooser.setDialogTitle("Save current card as...");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnVal = fileChooser
                        .showSaveDialog(PolyclinicFrame.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (!savePatientToFile(file)) {
                        JOptionPane.showMessageDialog(PolyclinicFrame.this,
                                "It's impossible" + "to save data to the file",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        ;
                    }
                }
            }
        };

        protected boolean adding;

        public final Action addCard = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!adding) {
                    adding = true;
                    currentPatient = new Patient(0, "", "", "", new Date(), "");
                    showCurrentPatient();
                    clearFields();
                    editMode = false;
                    enableActions(true);
                    setEditMode.actionPerformed(null);
                }
            }
        };

        public final Action removeCard = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                patients.remove(currentIndex--);
                if (currentIndex > patients.size() - 1) {
                    currentIndex = patients.size() - 1;
                } else if (currentIndex < 0) {
                    currentIndex = 0;
                }
                if (patients.size() > 0) {
                    currentPatient = patients.get(currentIndex);
                    showCurrentPatient();
                } else {
                    clearFields();
                    enableActions(false);
                }
            }
        };

        public final Action saveAll = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
                fileChooser.setDialogTitle("Save all cards as...");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnVal = fileChooser
                        .showSaveDialog(PolyclinicFrame.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (!saveAllCardsToFile(file)) {
                        JOptionPane.showMessageDialog(PolyclinicFrame.this,
                                "Impossible" + "to save data to the file",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        ;
                    }
                }
            }
        };

        public ActionsStuff() {
            openAction.putValue(Action.NAME, "Open");
            openAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

            saveCurrent.putValue(Action.NAME, "Save current card as...");
            saveCurrent.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

            setEditMode.putValue(Action.NAME, "Edit Mode");
            setEditMode.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);

            addCard.putValue(Action.NAME, "Add Card");
            addCard.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);

            goNext.putValue(Action.NAME, "Next");
            goNext.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

            goPrior.putValue(Action.NAME, "Previous");
            goPrior.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);

            removeCard.putValue(Action.NAME, "Remove Card");
            removeCard.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);

            saveAll.putValue(Action.NAME, "Save all cards as...");
            saveAll.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

            exit.putValue(Action.NAME, "Exit");
            exit.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
        }

        private boolean saveAllCardsToFile(File file) {
            try {
                DataOutputStream out = new DataOutputStream(
                        new FileOutputStream(file));
                out.writeInt(patients.size());
                for (int i = 0; i < patients.size(); i++) {
                    patients.get(i).writeToBinaryStream(out);
                }
                out.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        private final boolean loadPatientsFromFile(File file) {
            try {
                DataInputStream in = new DataInputStream(new FileInputStream(
                        file));
                ArrayList<Patient> tmpPatients = new ArrayList<Patient>();
                int numPatients = in.readInt();
                for (int i = 0; i < numPatients; i++) {
                    tmpPatients.add(Patient.readFromBinaryStream(in));
                }
                patients = tmpPatients;
                in.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        private boolean savePatientToFile(File file) {
            try {
                DataOutputStream out = new DataOutputStream(
                        new FileOutputStream(file));
                out.writeInt(1);// в файле будет храниться один пациент
                currentPatient.writeToBinaryStream(out);
                out.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }
}
