/*
 * Created on 22.10.2006
 */
package ru.amse.soultakov.classviewer;

import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/** 
 * Класс, предназначенный для распечатки информации о структуре других классов. 
 */
public class ClassViewer {
    
    public static final int NOT_INTERFACE = Modifier.INTERFACE^Integer.MAX_VALUE;
    public static final int VISIBILITY = 
        (Modifier.PRIVATE|Modifier.PUBLIC|Modifier.PROTECTED);
    public static final int NOT_ABSTRACT = Modifier.ABSTRACT^Integer.MAX_VALUE;
    
    private Class clazz;
    private PrintStream out;
    
    private static String TAB = "    ";
    
    /**
     * Создает объект, который может распечатывать информацию о полях, методах, конструкторах
     * и т.д. класса, представленного параметром <code>clazz</code>
     * @param clazz - класс, информация о котором может быть распечатана
     * @param out - поток, в который выводится информация о классе
     */
    public ClassViewer(Class clazz, PrintStream out) {
        if ((clazz == null)||(out == null)) {
            throw new IllegalArgumentException("Class не может быть null");
        }
        this.clazz = clazz;
        this.out = out;
    }
    
    public ClassViewer(Class clazz) {
        this(clazz, System.out);
    }
    
    public void printClass() {
        printAnnotations();
        printModifiers();
        if (!clazz.isAnnotation()&&!clazz.isEnum()) {
            printSuperclass();
            printInterfaces();
        }
        out.println("{\n");
        printFields();
        printConstructors();
        printMethods();
        out.println("}");
    }

    private void printConstructors() {
        Constructor[] constructors = clazz.getDeclaredConstructors();
        int FLAG = Integer.MAX_VALUE;
        if (clazz.isInterface() || clazz.isAnnotation()) {
            FLAG = NOT_ABSTRACT;
        }
        int printedCount = 0;
        for(Constructor c : constructors) {
            if ((c.getModifiers()& Modifier.PUBLIC) != 0) {
                printConstructor(c, FLAG);
                printedCount++;
            }
        }
        if (printedCount != 0) {
            out.println();
        }
    }
    
    private void printConstructor(Constructor c, int FLAG) {
        out.print(TAB + Modifier.toString(c.getModifiers()&FLAG)
                 + " " + getClassName(clazz) + "(");
        Class[] paramTypes = c.getParameterTypes();
        for(int i = 0; i < paramTypes.length; i++) {
            if (i > 0) {
                out.print(", ");
            }
            out.print(getClassName(paramTypes[i]) + " arg" + i);
        }
        out.print(")");
        Class<?>[] exceptionTypes = c.getExceptionTypes();
        if (exceptionTypes.length != 0) {
            out.println();
            out.print(TAB + TAB + "throws ");
            for(int i = 0; i < exceptionTypes.length; i++) {
                if (i > 0) {
                    out.print(", ");
                }
                out.print(getClassName(exceptionTypes[i]));
            }
        }
        out.println(";");
    }

    private void printFields() {
        Field[] fields = clazz.getDeclaredFields();
        int printedCount = 0;
        for(Field f : fields) {
            if (((f.getModifiers()&Modifier.PUBLIC) != 0)||(f.isAnnotationPresent(Doc.class))) {
                Annotation a = f.getAnnotation(Doc.class);
                if (a != null) {
                    out.println(TAB + "/*" + getValue(a.toString()) + "*/");
                } 
                out.println(TAB + Modifier.toString(f.getModifiers()) + " " 
                        + getClassName(f.getType()) + " " + f.getName() + ";");
                printedCount++;
            }
        }
        if (printedCount != 0) {
            out.println();
        }
    }
    
    private String getValue(String s) {
        String val = s.split("=")[1];
        return (val.substring(0, val.length()-1));
    }
    
    private void printMethods() {
        Method[] methods = clazz.getDeclaredMethods();
        int FLAG = Integer.MAX_VALUE;
        if (clazz.isInterface() || clazz.isAnnotation()) {
            FLAG = NOT_ABSTRACT;
        }
        int printedCount = 0;
        for(Method m : methods) {
            if ((m.getModifiers()&Modifier.PUBLIC) != 0 || m.isAnnotationPresent(Doc.class)) {
                Annotation a = m.getAnnotation(Doc.class);
                if (a != null) {
                    out.println(TAB + "/*" + getValue(a.toString()) + "*/");
                } 
                printMethod(m, FLAG);
                printedCount++;
            }
        }
        if (printedCount != 0) {
            out.println();
        }
    }

    private void printMethod(Method m, int FLAG) {
        out.print(TAB + Modifier.toString(m.getModifiers()&FLAG) + " "
                + getClassName(m.getReturnType()) + " " + m.getName() + "(");
        Class[] paramTypes = m.getParameterTypes();
        for(int i = 0; i < paramTypes.length; i++) {
            if (i > 0) {
                out.print(", ");
            }
            out.print(getClassName(paramTypes[i]) + " arg" + i);
        }
        out.print(")");
        Class<?>[] exceptionTypes = m.getExceptionTypes();
        if (exceptionTypes.length != 0) {
            out.println();
            out.print(TAB + TAB + "throws ");
            for(int i = 0; i < exceptionTypes.length; i++) {
                if (i > 0) {
                    out.print(", ");
                }
                out.print(getClassName(exceptionTypes[i]));
            }
        }
        out.println(";");
    }

    private void printAnnotations() {
        Annotation[] ans = clazz.getAnnotations();
        for(Annotation a : ans) {
            printAnnotation(a);
        }
    }

    private void printAnnotation(Annotation a) {
        String name = a.annotationType().getName();
        name = "@" + name.substring(name.lastIndexOf('.') + 1) + " ";
        out.println(name);
        //out.println(getValue(a.toString()));
    }

    private void printInterfaces() {
        Class[] interfaces = clazz.getInterfaces();
        if ((interfaces != null)&& (interfaces.length != 0)) {
            out.println();
            out.print(TAB + TAB);
            if (clazz.isInterface()) {
                out.print(" extends ");
            } else {
                out.print(" implements ");
            }
            for(int i = 0; i < interfaces.length; i++) {
                if (i > 0) {
                    out.print(", ");
                }
                out.print(getClassName(interfaces[i]));
            }
            out.print(" ");
        }
    }

    private void printSuperclass() {
        if ((clazz.getSuperclass() != null)&&(clazz.getSuperclass() != Object.class)) {
            out.print("extends " + getClassName(clazz.getSuperclass()) + " ");
        }
    }

    private void printModifiers() {
        if (clazz.isAnnotation()) {
            out.print(Modifier.toString(clazz.getModifiers()&VISIBILITY));
            out.print(" @interface");
        } else if (clazz.isEnum()) {
            out.print(Modifier.toString(clazz.getModifiers()&VISIBILITY));
            out.print(" enum");
        } else if (clazz.isInterface()){
            out.print(Modifier.toString(clazz.getModifiers()&NOT_ABSTRACT));
        } else {
            out.print(Modifier.toString(clazz.getModifiers()&NOT_INTERFACE));
            out.print(" class");
        }
        out.print(" " + getClassName(clazz) + " ");
    }
    
    private String getClassName(Class c) {
        String brackets = "";
        while(c.isArray()) {
            brackets += "[]";
            c = c.getComponentType();
        }
        String name = c.getName();
        name = name.substring(name.lastIndexOf('.') + 1);
        return name + brackets;
    }
}
