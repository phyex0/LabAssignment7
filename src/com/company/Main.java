//Halit Burak Yeşildal 18050111043
package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {

        MaxHeap maxHeap = new MaxHeap();
        FileIO f = new FileIO();
        f.readFile(maxHeap);
        System.out.println(maxHeap.toString());
        maxHeap.extractMax();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(maxHeap.toString());

        maxHeap.extractMax();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
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
    public void readFile(MaxHeap heap) throws IOException {
        File f = new File("src/com/company/students.txt");
        if(!f.exists())
            f.createNewFile();
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        String line;
        String[] input;
        while((line=br.readLine())!=null){
            input= line.split(",");
            if(input.length==4)
                heap.insert(new Student(input[0],input[1],Integer.parseInt(input[2]),Double.parseDouble(input[3])));
        }
        br.close();
        fr.close();

    }
}



class MaxHeap {
    private Student[] Heap;
    private int size;
    private int maxsize=10;

    // Constructor to initialize an
    // empty max heap with given maximum
    // capacity.
    public MaxHeap() {
        this.size = 0;
        Heap = new Student[this.maxsize + 1];
        Heap[0] = new Student("a","a",1,1);
    }

    // Returns position of parent
    private int parent(int pos) {
        return pos / 2;
    }

    // Below two functions return left and
    // right children.
    private int leftChild(int pos) {
        return (2 * pos);
    }

    private int rightChild(int pos) {
        return (2 * pos) + 1;
    }

    // Returns true of given node is leaf
    private boolean isLeaf(int pos) {
        if (pos > (size / 2) && pos <= size) {
            return true;
        }
        return false;
    }

    private void swap(int fpos, int spos) {
        Student tmp;
        tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }

    // A recursive function to max heapify the given
    // subtree. This function assumes that the left and
    // right subtrees are already heapified, we only need
    // to fix the root.
    private void maxHeapify(int pos) {
        if (isLeaf(pos))
            return;
        //Heap[leftChild(pos)].getSurname().compareTo(Heap[pos].getSurname())>0
        if (Heap[leftChild(pos)].getSurname().compareTo(Heap[pos].getSurname())>0
                || Heap[rightChild(pos)].getSurname().compareTo(Heap[pos].getSurname())>0) {

            if (Heap[leftChild(pos)].getSurname().compareTo(Heap[rightChild(pos)].getSurname())>0) {
                swap(pos, leftChild(pos));
                maxHeapify(leftChild(pos));
            } else {
                swap(pos, rightChild(pos));
                maxHeapify(rightChild(pos));
            }
        }
    }

    // Inserts a new element to max heap
    public void insert(Student element) {
        if(maxsize<=size)
            resizeArray();

        Heap[++size] = element;

        // Traverse up and fix violated property
        int current = size;
        while (Heap[current].getSurname().compareTo(Heap[parent(current)].getSurname())>0) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public void print() {
        for (int i = 1; i <= size / 2; i++) {
            System.out.print(
                    " PARENT : " + Heap[i]
                            + " LEFT CHILD : " + Heap[2 * i]
                            + " RIGHT CHILD :" + Heap[2 * i + 1]);
            System.out.println();
        }
    }

    // Remove an element from max heap
    public Student extractMax() {
        Student popped = Heap[1];
        Heap[1] = Heap[size--];
        maxHeapify(1);
        return popped;
    }

    private void resizeArray() {
        Student[] arr = Heap;
        Heap = new Student[arr.length * 2];
        // copy elements over
        System.arraycopy(arr, 0, Heap, 0, arr.length);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < size; ++i)
            builder.append(Heap[i]);

        return builder.toString();
    }
}