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
        if (student == null) {
            return false;
        }
    
        if (student.getName().trim().isEmpty()
                || student.getEmail().trim().isEmpty()
                || student.getCourse().trim().isEmpty()) {
            return false;
        }
    
        Node current = head;
    
        // Check duplicate email
        while (current != null) {
            if (current.getData().getEmail().equalsIgnoreCase(student.getEmail())) {
                return false;
            }
    
            current = current.getNext();
        }
    
        Node newNode = new Node(student);
    
        if (head == null) {
            head = newNode;
            tail = newNode;
            size++;
            return true;
        }
    
        // Insert sorted by student name
        if (student.getName().compareToIgnoreCase(head.getData().getName()) < 0) {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
            size++;
            return true;
        }
    
        current = head;
    
        while (current.getNext() != null
                && student.getName().compareToIgnoreCase(current.getNext().getData().getName()) > 0) {
            current = current.getNext();
        }
    
        newNode.setNext(current.getNext());
        newNode.setPrevious(current);
    
        if (current.getNext() != null) {
            current.getNext().setPrevious(newNode);
        } else {
            tail = newNode;
        }
    
        current.setNext(newNode);
        size++;
    
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
     * Task 3  Search a student record by name or course.
     */
    public Student[] searchStudents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new Student[0];
        }
    
        keyword = keyword.trim().toLowerCase();
    
        int matchCount = 0;
        Node current = head;
    
        // First traversal: count matching records
        while (current != null) {
            Student student = current.getData();
    
            if (student.getName().toLowerCase().contains(keyword)
                    || student.getEmail().toLowerCase().contains(keyword)
                    || student.getCourse().toLowerCase().contains(keyword)) {
                matchCount++;
            }
    
            current = current.getNext();
        }
    
        Student[] results = new Student[matchCount];
        current = head;
        int index = 0;
    
        // Second traversal: store matching records
        while (current != null) {
            Student student = current.getData();
    
            if (student.getName().toLowerCase().contains(keyword)
                    || student.getEmail().toLowerCase().contains(keyword)
                    || student.getCourse().toLowerCase().contains(keyword)) {
                results[index] = student;
                index++;
            }
    
            current = current.getNext();
        }
    
        return results;
    }

    /**
     * Task 3  Return all student records in the current list order.
     */
    public Student[] getAllStudents() {
        Student[] students = new Student[size];

        if (head == null) {
            return students;
        }

        Node current = head;
        int index = 0;

        do {
            students[index] = current.getData();
            index++;
            current = current.getNext();
        } while (current != null && current != head);

        return students;
    }

    /**
     * Task 3  Create and return a reverse copy of the student list.
     */
    public Student[] getReverseCopy() {
        Student[] students = new Student[size];

        if (tail == null) {
            return students;
        }

        Node current = tail;
        int index = 0;

        do {
            students[index] = current.getData();
            index++;
            current = current.getPrevious();
        } while (current != null && current != tail);

        return students;
    }

    /**
     * TODO: Task 4
     * Generate simple report information.
     */
    public String generateReport() {
        if (head == null) {
            return "===== Enrolment Report =====\n"
                    + "No student enrolment records available.";
        }
    
        int totalRecords = 0;
    
        int computerScienceCount = 0;
        int softwareEngineeringCount = 0;
        int cyberSecurityCount = 0;
        int dataScienceCount = 0;
        int businessITCount = 0;
        int othersCount = 0;
    
        Node current = head;
    
        while (current != null) {
            totalRecords++;
    
            String course = current.getData().getCourse();
    
            if (course.equalsIgnoreCase("Computer Science")) {
                computerScienceCount++;
            } else if (course.equalsIgnoreCase("Software Engineering")) {
                softwareEngineeringCount++;
            } else if (course.equalsIgnoreCase("Cybersecurity")) {
                cyberSecurityCount++;
            } else if (course.equalsIgnoreCase("Data Science")) {
                dataScienceCount++;
            } else if (course.equalsIgnoreCase("Business Information Technology")) {
                businessITCount++;
            } else {
                othersCount++;
            }
    
            current = current.getNext();
        }
    
        return "===== Enrolment Report =====\n\n"
                + "Total number of enrolment records: " + totalRecords + "\n\n"
                + "Students by Course:\n"
                + "Computer Science: " + computerScienceCount + "\n"
                + "Software Engineering: " + softwareEngineeringCount + "\n"
                + "Cybersecurity: " + cyberSecurityCount + "\n"
                + "Data Science: " + dataScienceCount + "\n"
                + "Business Information Technology: " + businessITCount + "\n"
                + "Others: " + othersCount;
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
