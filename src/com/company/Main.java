package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        MaxHeap mh = new MaxHeap(20);
        FileIO f = new FileIO();
        f.readFile(mh);
        System.out.println(mh.toString());

    }
}




class MaxHeap {
    private static final int INIT_SIZE = 64;

    /*
    The backing array store of the heap. We're using vanilla arrays
    because they make the code look much cleaner.
     */
    private Student[] array;
    /*
    The size of the heap.
     */
    private int heapSize;

    /*
    Construct an empty heap. This is the constructor you would use if you have a
    priority queue being maintained.
     */
    public MaxHeap() {
        this.array = new Student[INIT_SIZE];
        this.heapSize = 0;
    }

    /*
    Construct an empty heap, and initialize the capacity of its backing array.
    Given a good value to capacity, resizing the array might never have to be done.
     */
    public MaxHeap(int capacity) {
        this.array = new Student[capacity];
        this.heapSize = 0;
    }

    /*
    Construct a max heap out of the given array. The array doesn't have to satisfy
    the max-heap ordering (in fact, we expect it not to).
     */
    public MaxHeap(Student[] array) {
        this.array = new Student[array.length];
        System.arraycopy(array, 0, this.array, 0, array.length);
        this.heapSize = array.length;
        maxHeapify();
    }

    /*
    Get the backing array store of this heap.
     */
    public Student[] backingArray() {
        return array;
    }

    /*
    Return the index of the node that is the parent of the node at index i.
     */
    private int parent(int i) {
        return i / 2;
    }

    /*
    Return the index of the node that is the left child of the node at index i.
     */
    private int left(int i) {
        return i * 2;
    }

    /*
    Return the index of the node that is the right child of the node at index i.
     */
    private int right(int i) {
        return left(i) + 1;
    }

    /*
    Pre-condition:
        - the binary trees rooted at left(i) and right(i) are max-heaps
        - array[i] may be smaller than its children, violating the max heap property
    Post-condition:
        - Puts the value of the node at index i in the correct index, as per the
        max-heap property.
     */
    private void maxHeapify(int i) {
        /*
        Determine which of the elements at array[i], array[left(i)] and array[right(i)]
        is the largest, and extract that index.
         */
        //left > i
        // array[left].getSurname().compareTo(array[i].getSurname())
        int left = left(i), right = right(i);
        int largest;
        if (left < heapSize && array[left].getSurname().compareTo(array[i].getSurname()) > 0) {
            largest = left;
        } else {
            largest = i;
        }

        if (right < heapSize &&array[right].getSurname().compareTo(array[largest].getSurname()) > 0){
            largest = right;
        }

        /*
        Once we have the index of the largest element out of array[i], array[left(i)], and
        array[right(i)], we swap the largest element to become the parent of this subtree,
        and continue max-heapifying at the largest index.
         */
        if (largest != i) {
            Student temp = array[i];
            array[i] = array[largest];
            array[largest] = temp;
            maxHeapify(largest);
        }
    }

    /*
    Pre-condition:
        - the array field of this class has been initialized to be a copy of a caller-given
        array, but the array is untampered with and may not be a max-heap.
    Post-condition:
        - the array field comes to represent a max-heap.
        - the heapSize member is mutated.
     */
    private void maxHeapify() {
        for (int i = array.length / 2; i >= 0; --i) {
            maxHeapify(i);
        }
    }

    /*
    Pre-condition:
        - the given array is an array of integers that is unsorted (or sorted).
    Post-condition:
        - the returned array is sorted.
     */
    public static Student[] heapsort(Student[] a) {
        /*
        First step: construct a max heap with the elements of A.
         */
        MaxHeap heap = new MaxHeap(a);
        Student[] arr = heap.array;

        /*
        Second step: the selection sort idea; take the max and place it at the end.
        Every time we put the max at the end, we need to restore the max-heap property.
         */
        for (int i = arr.length - 1; i > 0; i--) {
            // swap arr[0] with arr[i]
            Student temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heap.heapSize--;
            heap.maxHeapify(0);
        }

        return arr;
    }

    /*
    Return the maximum valued element in the max-heap.
     */
    public Student max() {
        return array[0];
    }

    /*
    Extract the maximum valued element in the max-heap and return it.
    This mutates the heap.
     */
    public Student extractMax() throws Exception {
        if (heapSize < 1) {
            throw new Exception("Heap underflow");
        }
        Student max = max();
        array[0] = array[heapSize - 1];
        heapSize--;
        maxHeapify(0);
        return max;
    }

    /*
    Increase the value of a key at a given index in the heap. This is mostly just a helper
    method to be used in the add method.
     */
    public void increaseKey(int i, Student key) throws Exception {
        if (array[i].getSurname().compareTo(key.getSurname()) > 0) {
            throw new Exception("The new key is smaller than current key.");
        }
        array[i] = key;
        while (i > 0 &&  0 < array[i].getSurname().compareTo(array[parent(i)].getSurname())) {
            Student temp = array[i];
            array[i] = array[parent(i)];
            array[parent(i)] = temp;
            i = parent(i);
        }
    }

    /*
    Add a new key to this heap. This mutates the heap, but after completion, is still a max-heap.
     */
    public void add(Student key) {
        heapSize++;
        if (heapSize > array.length) {
            resizeArray();
        }
        array[heapSize - 1] = key;
        try {
            increaseKey(heapSize - 1, key);
        } catch (Exception ignored) {

        }
    }

    /*
    Return the string representation of this max-heap. Since the array
    is often bigger than the heap, we need to manually construct this string.
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < heapSize; ++i) {
            builder.append(array[i]);

        }
        return builder.toString();
    }

    /*
    Resize the underlying array store to double its current size. This is the same
    idea used in vectors.
     */
    private void resizeArray() {
        Student[] arr = array;
        array = new Student[arr.length * 2];
        // copy elements over
        System.arraycopy(arr, 0, array, 0, arr.length);
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
    public void readFile(MaxHeap mh) throws IOException {
        File f = new File("src/com/company/students.txt");
        if(!f.exists())
            f.createNewFile();
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        String line;
        String[] input;

        while((line=br.readLine())!=null){
            input= line.split(",");
            mh.add(new Student(input[0],input[1],Integer.parseInt(input[2]),Double.parseDouble(input[3])));
        }

        br.close();
        fr.close();



    }
}