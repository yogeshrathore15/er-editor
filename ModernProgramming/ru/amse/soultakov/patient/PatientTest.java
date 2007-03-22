/*
 * Created on 17.10.2006
 */
package ru.amse.soultakov.patient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Soultakov Maxim
 */
public class PatientTest {
    
    private static final int NUM_ELEMENTS = 10;
    
    private static final String[] SURNAMES = {"Иванов", "Петров", "Сидоров",
                                              "Смирнов", "Кузнецов"};
    private static final String[] NAMES = {"Иван", "Пётр", "Николай", 
                                            "Алексей", "Александр" };
    private static final String[] PATRONYMICS = {"Иванович", "Петрович", "Владимирович"};
    
    public static int random(int highBound) {
        return (int)(Math.random()*highBound);
    }
    
    public static List<Patient> getPatientList() {
        List<Patient> list = new ArrayList<Patient>();
        Calendar calendar = Calendar.getInstance();
        for(int i = 0; i < NUM_ELEMENTS; i++) {
            int cardNum = random(10000000);
            String surname = SURNAMES[random(5)];
            String name = NAMES[random(5)];
            String patronymic = PATRONYMICS[random(3)];
            calendar.set(1950 + random(50), random(12), random(28));
            String doctorName = SURNAMES[random(5)] + " " + NAMES[random(5)].charAt(0) 
                                + "." + NAMES[random(5)].charAt(0) + ".";
            list.add(new Patient(cardNum, surname, name, patronymic, calendar.getTime(),
                    doctorName));
        }
        
        return list;
    }
    
    public static void main(String args[]) throws FileNotFoundException, IOException, ParseException {
        testBinaryStreams();
        testTextStreams();
    }

    private static void testTextStreams() throws FileNotFoundException, IOException, ParseException {
        List<Patient> patients = getPatientList();
        System.out.println("ДВОИЧНЫЙ ФОРМАТ");
        System.out.println("----------Созданные данные-------------------------");
        System.out.println(patients);
        File file = new File("patients.txt");
        Writer writer = new FileWriter(file);
        for(Patient p : patients) {
            p.writeToTextStream(writer);
        }
        writer.close();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<Patient> readPatients = new ArrayList<Patient>(NUM_ELEMENTS);
        for(int i = 0; i < NUM_ELEMENTS; i++) {
            readPatients.add(Patient.readFromTextStream(reader));
        }
        System.out.println("----------Считанные данные-------------------------");
        System.out.println(readPatients);
        System.out.println("-----------------------------------");
        System.out.println("Lists are equal = " + patients.equals(readPatients));
        patients = null;
        readPatients = null;
    }

    private static void testBinaryStreams() throws FileNotFoundException, IOException {
        List<Patient> patients = getPatientList();
        System.out.println("ТЕКСТОВЫЙ ФОРМАТ");
        System.out.println("----------Созданные данные-------------------------");
        System.out.println(patients);
        File file = new File("patients.bin");
        DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(file));
        outputStream.writeInt(patients.size());
        for(Patient p : patients) {
            p.writeToBinaryStream(outputStream);
        }
        outputStream.close();
        DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
        List<Patient> readPatients = new ArrayList<Patient>();
        while(inputStream.available() != 0) {
            readPatients.add(Patient.readFromBinaryStream(inputStream));
        }
        System.out.println("----------Считанные данные-------------------------");
        System.out.println(readPatients);
        System.out.println("-----------------------------------");
        System.out.println("Lists are equal = " + patients.equals(readPatients));
    }
}
