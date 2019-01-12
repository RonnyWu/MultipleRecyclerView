package com.ronny.mrv.javaexample;

import java.util.Random;

/**
 * TODO
 * <p>
 * Create By 吴荣 at 2019/1/12 23:08
 */
@SuppressWarnings("WeakerAccess")
public class DataUtil {

    public static String getStudentNumber() {
        int number = new Random().nextInt(1000);
        if (number < 1000) {
            number += 1000;
        }
        return String.valueOf(number);
    }

    public static String getStudentName() {
        String[] names = {"Lena Keats", "Colby Jessie", "Paula Woolley", "Giles Sidney",
                "Alvis Bowman", "Nicole Sophia", "Rae Augustine", "Page Piers", "Mark Carnegie",
                "Dennis Hewlett", "Ferdinand Hugh", "Gabriel Masefield", "Allen Judith", "Elroy Ellis",
                "Quintina Eugen"
        };
        return names[new Random().nextInt(names.length)];
    }

    public static String getStudentAge() {
        int age = new Random().nextInt(30);
        if (age < 15) {
            age += 15;
        }
        return String.valueOf(age);
    }

    public static String getStudentSex() {
        return new Random().nextBoolean() ? "Male" : "Female";
    }

    public static String getStudentClass() {
        int studentClass = new Random().nextInt(8) + 1;
        return "class " + studentClass;
    }

    public static Student getRandomStudent() {
        Student student = new Student();
        student.mNumber = getStudentNumber();
        student.mName = getStudentName();
        student.mAge = getStudentAge();
        student.mSex = getStudentSex();
        student.mClass = getStudentClass();
        return student;
    }
}
