package com.coursework.enrolment.structure;

import com.coursework.enrolment.model.Student;

/**
 * ADT structure for managing student records using a Doubly Linked List.
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
     * Add a student record in sorted order by student name.
     * Duplicate checking is based on IC number, email, and phone number
     * because student names may not be unique.
     */
    public boolean addStudent(Student student) {
        if (student == null) {
            return false;
        }

        if (isBlank(student.getName())
                || isBlank(student.getIcNumber())
                || isBlank(student.getEmail())
                || isBlank(student.getPhoneNumber())
                || isBlank(student.getDateOfBirth())
                || isBlank(student.getCourse())) {
            return false;
        }

        if (isDuplicateStudent(student)) {
            return false;
        }

        Node newNode = new Node(student);

        if (head == null) {
            head = newNode;
            tail = newNode;
            size++;
            return true;
        }

        // Insert before head if the new student name should come first.
        if (student.getName().compareToIgnoreCase(head.getData().getName()) < 0) {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
            size++;
            return true;
        }

        Node current = head;

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
     * Delete a student record by email because email is unique.
     */
    public boolean deleteStudentByEmail(String email) {
        if (head == null || isBlank(email)) {
            return false;
        }

        Node current = head;
        String targetEmail = email.trim();

        while (current != null) {
            if (current.getData().getEmail().equalsIgnoreCase(targetEmail)) {
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
                    Node previousNode = current.getPrevious();
                    Node nextNode = current.getNext();
                    previousNode.setNext(nextNode);
                    nextNode.setPrevious(previousNode);
                }

                size--;
                return true;
            }

            current = current.getNext();
        }

        return false;
    }

    /**
     * Search student records by name, IC number, email, phone number, date of birth, or course.
     */
    public Student[] searchStudents(String keyword) {
        if (isBlank(keyword)) {
            return new Student[0];
        }

        String searchKeyword = keyword.trim().toLowerCase();
        int matchCount = 0;
        Node current = head;

        // First traversal: count matching records.
        while (current != null) {
            if (matchesKeyword(current.getData(), searchKeyword)) {
                matchCount++;
            }

            current = current.getNext();
        }

        Student[] results = new Student[matchCount];
        current = head;
        int index = 0;

        // Second traversal: store matching records.
        while (current != null) {
            Student student = current.getData();

            if (matchesKeyword(student, searchKeyword)) {
                results[index] = student;
                index++;
            }

            current = current.getNext();
        }

        return results;
    }

    /**
     * Return all student records in the current list order.
     */
    public Student[] getAllStudents() {
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
     * Create and return a reverse copy of the student list.
     */
    public Student[] getReverseCopy() {
        Student[] students = new Student[size];
        Node current = tail;
        int index = 0;

        while (current != null) {
            students[index] = current.getData();
            index++;
            current = current.getPrevious();
        }

        return students;
    }

    /**
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

    private boolean isDuplicateStudent(Student student) {
        Node current = head;

        while (current != null) {
            Student existingStudent = current.getData();

            if (existingStudent.getIcNumber().equalsIgnoreCase(student.getIcNumber().trim())
                    || existingStudent.getEmail().equalsIgnoreCase(student.getEmail().trim())
                    || existingStudent.getPhoneNumber().equalsIgnoreCase(student.getPhoneNumber().trim())) {
                return true;
            }

            current = current.getNext();
        }

        return false;
    }

    private boolean matchesKeyword(Student student, String keyword) {
        return student.getName().toLowerCase().contains(keyword)
                || student.getIcNumber().toLowerCase().contains(keyword)
                || student.getEmail().toLowerCase().contains(keyword)
                || student.getPhoneNumber().toLowerCase().contains(keyword)
                || student.getDateOfBirth().toLowerCase().contains(keyword)
                || student.getCourse().toLowerCase().contains(keyword);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
