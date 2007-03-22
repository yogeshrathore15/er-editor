/*
 * Created on 17.10.2006
 */
package ru.amse.soultakov.patient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * Класс, описывающий пациента в поликлинике. Обладает возможностями записи(считывания) 
 * в бинарный и текстовый потоки.
 * @author Soultakov Maxim
 */
public class Patient {

    private static final String MATCH_NAME_AND_INITIALS = 
        ":(\\p{Space}*)(\\S+)(\\p{Space}*)(\\S+)(\\p{Space}*)(\\.{1})" +
        "(\\p{Space}*)(\\S+)(\\p{Space}*)(\\.{1})";

    private static final String MATCH_DATE = "(\\d{2})(\\.)(\\d{2})(\\.)(\\d{4})";

    //  "\\S+" - для поддержки русского
    private static final String MATCH_NAME = "(\\p{Space}*)((\\S)+)";

    private static final String MATCH_CARD_NUMBER = "№(\\p{Space}*)(\\d+)(\\p{Space}*);";

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    
    private int ambulatoryCardNumber;

    private String surname;

    private String name;

    private String patronymic;

    private Date birthDate;

    private String doctorName;
    
    private String stringPresentation;//значение для метода toString();
    
      
    /**
     * Создает экземпляр класса пациент.
     * 
     * @param ambulatoryCardNumber -
     *            номер амбулаторной карты пациента
     * @param surname -
     *            фамилия пациента
     * @param name -
     *            имя пациента
     * @param patronymic -
     *            отчество пациента
     * @param birthDate -
     *            дата рождения пациента
     * @param doctorName -
     *            имя лечащего врача пациента
     */
    public Patient(int ambulatoryCardNumber, String surname, String name,
            String patronymic, Date birthDate, String doctorName) {
        if ((surname == null)||(name == null)||(patronymic == null)
                ||(birthDate == null)||(doctorName == null)) {
            throw new IllegalArgumentException("Поля не могут иметь значение null");
        }
        this.ambulatoryCardNumber = ambulatoryCardNumber;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.doctorName = doctorName;
    }

    /**
     * Возвращает номер амбулаторной карты пациента
     * @return номер амбулаторной карты пациента
     */
    public int getAmbulatoryCardNumber() {
        return ambulatoryCardNumber;
    }

    /**
     * Возвращает дату рождения пациента
     * @return дату рождения пациента
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Возвращает имя лечащего врача пациента
     * @return имя лечащего врача пациента
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * Возвращает имя пациента
     * @return имя пациента
     */
    public String getName() {
        return name;
    }

    /**
     * Возврвщает отчество пациента
     * @return отчество пациента
     */
    public String getPatronymic() {
        return patronymic;
    }

    /**
     * Возвращает фамилию пациента
     * @return фамилию пациента
     */
    public String getSurname() {
        return surname;
    }    
    
    /**
     * Считывает объект из бинарного потока
     * @param in - поток, из которого читается объект
     * @return объект, сохраненный в потоке
     * @throws IOException если случилась ошибка ввода-вывода
     */
    public static Patient readFromBinaryStream(DataInputStream in) throws IOException {
        int cardNumber = in.readInt();
        String surname = in.readUTF();
        String name = in.readUTF();
        String patronymic = in.readUTF();
        Date date = new Date(in.readLong());
        String doctorName = in.readUTF();        
        return new Patient(cardNumber, surname, name, patronymic, date, doctorName);
    }
    
    /**
     * Записывает объект в бинарный поток.
     * @param out - поток, в который записывается объект
     * @throws IOException если случилась ошибка ввода-вывода
     */
    public void writeToBinaryStream(DataOutputStream out) throws IOException {
        out.writeInt(getAmbulatoryCardNumber());
        out.writeUTF(getSurname());
        out.writeUTF(getName());
        out.writeUTF(getPatronymic());
        out.writeLong(birthDate.getTime());
        out.writeUTF(getDoctorName());
    }
    
    /**
     * Считывает объект из текстового потока
     * @param in - поток, из которого читается объект
     * @throws IOException если случилась ошибка ввода-вывода
     * @return объект, сохраненный в потоке
     * @throws ParseException если данные записаны в файле некорректно
     */
    public static Patient readFromTextStream(Reader in) throws IOException, ParseException { 
        Scanner scanner = new Scanner(readLine(in));
        
        scanner.findInLine(MATCH_CARD_NUMBER);
        int cardNumber = Integer.parseInt(scanner.match().group(2));

        scanner.findInLine(MATCH_NAME);
        String surname = scanner.match().group(2);
        
        scanner.findInLine(MATCH_NAME);
        String name = scanner.match().group(2);
        
        scanner.findInLine(MATCH_NAME);
        String patronymic = scanner.match().group(2);
        patronymic = patronymic.charAt(patronymic.length()-1) == ';' 
                    ? (patronymic.substring(0, patronymic.length()-1)) 
                    : (patronymic);   
        
        scanner.findInLine(MATCH_DATE);
        Date date = DATE_FORMAT.parse(scanner.match().group());
        
        scanner.findInLine(MATCH_NAME_AND_INITIALS);
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i <= scanner.match().groupCount(); i++) {
            sb = sb.append(scanner.match().group(i));
        }
        String doctorName = sb.toString();
        scanner.close();
        return new Patient(cardNumber, surname, name, patronymic, date, doctorName);
    }

    private static String readLine(Reader in) throws IOException {
        StringBuilder sb = new StringBuilder();
        char chr;
        while ((chr = (char) in.read() ) != '\n' ) {
            sb.append(chr);
        }
        return sb.toString();
    }
    
    /**
     * Записывает объект в текстовый поток.
     * @param out - поток, в который записывается объект
     * @throws IOException если случилась ошибка ввода-вывода
     */
    public void writeToTextStream(Writer out) throws IOException {
        out.write(this.toString() + '\n');
    }

    /**
     * Возвращает хэш-код объекта
     * @return хэш-код объекта
     */
    @Override
    public int hashCode() {//auto-generated
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ambulatoryCardNumber;
        result = PRIME * result + ((birthDate == null) ? 0 : birthDate.hashCode());
        result = PRIME * result + ((doctorName == null) ? 0 : doctorName.hashCode());
        result = PRIME * result + ((name == null) ? 0 : name.hashCode());
        result = PRIME * result + ((patronymic == null) ? 0 : patronymic.hashCode());
        result = PRIME * result + ((surname == null) ? 0 : surname.hashCode());
        return result;
    }

    /**
     * Проверяет равенство объектов
     * @param obj - объект для сравнения
     * @return <code>true</code> тогда и только тогда, когда объекты равны, <code>false</code>
     * - в противоположном случае
     */
    @Override
    public boolean equals(Object obj) {//auto-generated
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Patient other = (Patient) obj;
        if (ambulatoryCardNumber != other.ambulatoryCardNumber) {
            return false;
        }
        final Calendar calendar1 = Calendar.getInstance();
        final Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(birthDate);
        calendar2.setTime(other.birthDate);
        if ((calendar1.get(Calendar.YEAR) != calendar2.get(Calendar.YEAR)) 
                || (calendar1.get(Calendar.MONTH) != calendar2.get(Calendar.MONTH))
                || (calendar1.get(Calendar.DATE) != calendar2.get(Calendar.DATE))) {
            return false;
        }
        if (!doctorName.equals(other.doctorName)) {
            return false;
        }
        if (!name.equals(other.name)) {
            return false;
        }
        if (!patronymic.equals(other.patronymic)) {
            return false;
        }
        if (!surname.equals(other.surname)) {
            return false;
        }
        return true;
    }
    
    /**
     * Возвращает строковое представление объекта
     * @return строковое представление объекта
     */
    @Override
    public String toString() {
        return createStringPresentation();
    }
    
    private String createStringPresentation() {
        if (stringPresentation == null) {
            stringPresentation = "[Карта №" + ambulatoryCardNumber +"; "
                    + surname + " " + name + " " + patronymic + "; "
                    + "Дата рождения: " + DATE_FORMAT.format(birthDate) + "; "
                    + "Врач: " + doctorName + "]"; 
        }
        return stringPresentation;
    }

    /**
     * @param ambulatoryCardNumber the ambulatoryCardNumber to set
     */
    public void setAmbulatoryCardNumber(int ambulatoryCardNumber) {
        this.ambulatoryCardNumber = ambulatoryCardNumber;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @param doctorName the doctorName to set
     */
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param patronymic the patronymic to set
     */
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
}