package com.coursework.enrolment.structure;

import com.coursework.enrolment.model.Student;

/**
 * ADT structure for managing student records using a Doubly Linked List.
 *
 * Task 1 only prepares the class and method signatures.
 * Other members should complete the TODO sections based on their assigned tasks.
 */
public class DoublyLinkedList {
    private Node head;
    private Node tail;
    private int size;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    public int getSize() {
        return size;
    }

    /**
     * Task 2 
     */
    public boolean addStudent(Student student) {
        if (student == null || student.getName() == null || student.getName().trim().isEmpty()) {
            return false;
        }

        Node current = head;
        while (current != null) {
            if (current.getData().getName().equalsIgnoreCase(student.getName().trim())) {
                return false; 
            }
            current = current.getNext();
        }

        Node newNode = new Node(student);

        if (head == null) {
            head = newNode;
            tail = newNode;
            increaseSize();
            return true;
        }

        current = head;
        while (current != null && current.getData().getName().compareToIgnoreCase(student.getName()) < 0) {
            current = current.getNext();
        }

        if (current == head) {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        } else if (current == null) {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        } else {
            Node prev = current.getPrevious();
            prev.setNext(newNode);
            newNode.setPrevious(prev);
            
            newNode.setNext(current);
            current.setPrevious(newNode);
        }

        increaseSize(); 
        return true;
    }

    /**
     * Task 2  Delete a student record by student name.
     */
    public boolean deleteStudentByName(String name) {
        if (head == null || name == null || name.trim().isEmpty()) {
            return false;
        }

        Node current = head;
        while (current != null) {
            if (current.getData().getName().equalsIgnoreCase(name.trim())) {
                
                if (current == head && current == tail) {
                    head = null;
                    tail = null;
                } else if (current == head) {
                    head = head.getNext();
                    head.setPrevious(null);
                } else if (current == tail) {
                    tail = tail.getPrevious();
                    tail.setNext(null);
                } else {
                    Node prev = current.getPrevious();
                    Node nxt = current.getNext();
                    prev.setNext(nxt);
                    nxt.setPrevious(prev);
                }
                
                decreaseSize(); 
                return true;
            }
            current = current.getNext();
        }
        
        return false; 
    }

    /**
     * TODO: Task 3
     * Search a student record by name or course.
     */
    public Student searchStudent(String keyword) {
        return null;
    }

  public Student[] getAllStudents() {
        //
        Student[] students = new Student[size];
        Node current = head;
        int index = 0;
        while (current != null) {
            students[index] = current.getData();
            index++;
            current = current.getNext();
        }
        return students;
    }

    /**
     * TODO: Task 3
     * Create and return a reverse copy of the student list.
     */
    public Student[] getReverseCopy() {
        return new Student[0];
    }

    /**
     * TODO: Task 4
     * Generate simple report information.
     */
    public String generateReport() {
        return "Report function is not implemented yet.";
    }

    protected void increaseSize() {
        size++;
    }

    protected void decreaseSize() {
        if (size > 0) {
            size--;
        }
    }
}