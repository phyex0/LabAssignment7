//Halit Burak Yeşildal 18050111043
package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) throws IOException {

        //Heap is already exist in Java but our required type is Student and we need to compare data due to surname.
        // We're going to create a comparator object to satisfy given condition. The condition on return statement gives us Max Heap.
        PriorityQueue<Student> maxHeap = new PriorityQueue<Student>(new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s2.getSurname().compareTo(s1.getSurname());
            }
        });

        FileIO f = new FileIO();
        f.readFile(maxHeap);
        System.out.println(maxHeap.toString());


    }
}




class Student{

    private String name;
    private String surname;
    private int age;
    private double gpa;

    public Student() {
    }

    public Student(String name, String surname, int age, double gpa) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gpa = gpa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return "name: "+name+"\nsurname: "+surname+"\nage: "+age+"\ngpa: "+gpa+"\n*************************\n";
    }
}

class FileIO{
    //com/company/students.txt
    public void readFile(PriorityQueue heap) throws IOException {
        File f = new File("src/com/company/students.txt");
        if(!f.exists())
            f.createNewFile();
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        String line;
        String[] input;

        while((line=br.readLine())!=null){
            input= line.split(",");
            heap.add(new Student(input[0],input[1],Integer.parseInt(input[2]),Double.parseDouble(input[3])));
        }

        br.close();
        fr.close();



    }
}