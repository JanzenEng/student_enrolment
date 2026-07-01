package com.coursework.enrolment.structure;

import com.coursework.enrolment.model.Student;

/**
 * Custom Doubly Linked List ADT for storing student enrolment records.
 *
 * Requirement covered:
 * - Add record in sorted order by student name
 * - Reject empty input through validation
 * - Handle duplicate records
 * - Delete record
 * - Search record
 * - View all records
 * - Create reverse copy
 * - Generate simple report data
 */
public class DoublyLinkedList {

    /**
     * Node is kept private because only the linked list should manage pointers.
     */
    private static class Node {
        private final Student data;
        private Node previous;
        private Node next;

        private Node(Student data) {
            this.data = data;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Adds a student record in sorted order by student name.
     */
    public void addStudent(Student student) {
        validateStudent(student);
        ensureNoDuplicate(student);

        Node newNode = new Node(student);

        if (head == null) {
            head = newNode;
            tail = newNode;
            size++;
            return;
        }

        if (compareName(student, head.data) < 0) {
            insertBeforeHead(newNode);
            size++;
            return;
        }

        Node current = head;
        while (current.next != null && compareName(student, current.next.data) > 0) {
            current = current.next;
        }

        if (current.next == null) {
            insertAfterTail(newNode);
        } else {
            insertAfterCurrent(current, newNode);
        }
        size++;
    }

    public boolean deleteStudentById(int studentId) {
        Node current = head;

        while (current != null) {
            if (current.data.getId() == studentId) {
                removeNode(current);
                return true;
            }
            current = current.next;
        }

        return false;
    }

    /**
     * Deletes the first matching student by name.
     * This method is useful for showing the assignment requirement: delete by string field.
     */
    public boolean deleteStudentByName(String name) {
        if (isBlank(name)) {
            return false;
        }

        Node current = head;
        while (current != null) {
            if (current.data.getName().equalsIgnoreCase(name.trim())) {
                removeNode(current);
                return true;
            }
            current = current.next;
        }

        return false;
    }

    public Student findStudentById(int studentId) {
        Node current = head;

        while (current != null) {
            if (current.data.getId() == studentId) {
                return current.data;
            }
            current = current.next;
        }

        return null;
    }

    /**
     * Searches by student name or course.
     */
    public Student[] searchStudents(String keyword) {
        if (isBlank(keyword)) {
            return getAllStudents();
        }

        String keywordLower = keyword.trim().toLowerCase();
        int matchCount = 0;
        Node current = head;

        while (current != null) {
            if (matchesKeyword(current.data, keywordLower)) {
                matchCount++;
            }
            current = current.next;
        }

        Student[] results = new Student[matchCount];
        current = head;
        int index = 0;

        while (current != null) {
            if (matchesKeyword(current.data, keywordLower)) {
                results[index++] = current.data;
            }
            current = current.next;
        }

        return results;
    }

    public Student[] getAllStudents() {
        Student[] students = new Student[size];
        Node current = head;
        int index = 0;

        while (current != null) {
            students[index++] = current.data;
            current = current.next;
        }

        return students;
    }

    /**
     * Creates and returns a reverse copy of the list data.
     * The original DLL order is not changed.
     */
    public Student[] getReverseCopy() {
        Student[] students = new Student[size];
        Node current = tail;
        int index = 0;

        while (current != null) {
            students[index++] = current.data;
            current = current.previous;
        }

        return students;
    }

    /**
     * Generates a simple report: total records and course grouping.
     */
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("===== Enrolment Report =====\n");
        report.append("Total enrolment records: ").append(size).append("\n\n");

        if (isEmpty()) {
            report.append("No student records available.\n");
            return report.toString();
        }

        Student[] students = getAllStudents();
        boolean[] counted = new boolean[students.length];

        report.append("Students by Course:\n");
        for (int i = 0; i < students.length; i++) {
            if (counted[i]) {
                continue;
            }

            String course = students[i].getCourse();
            int courseCount = 1;
            counted[i] = true;

            for (int j = i + 1; j < students.length; j++) {
                if (!counted[j] && course.equalsIgnoreCase(students[j].getCourse())) {
                    courseCount++;
                    counted[j] = true;
                }
            }

            report.append("- ").append(course).append(": ").append(courseCount).append("\n");
        }

        return report.toString();
    }

    private void insertBeforeHead(Node newNode) {
        newNode.next = head;
        head.previous = newNode;
        head = newNode;
    }

    private void insertAfterTail(Node newNode) {
        tail.next = newNode;
        newNode.previous = tail;
        tail = newNode;
    }

    private void insertAfterCurrent(Node current, Node newNode) {
        newNode.next = current.next;
        newNode.previous = current;
        current.next.previous = newNode;
        current.next = newNode;
    }

    private void removeNode(Node node) {
        if (node == head && node == tail) {
            head = null;
            tail = null;
        } else if (node == head) {
            head = head.next;
            head.previous = null;
        } else if (node == tail) {
            tail = tail.previous;
            tail.next = null;
        } else {
            node.previous.next = node.next;
            node.next.previous = node.previous;
        }

        size--;
    }

    private void validateStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student record cannot be empty.");
        }
        if (isBlank(student.getName())) {
            throw new IllegalArgumentException("Student name cannot be empty.");
        }
        if (isBlank(student.getEmail())) {
            throw new IllegalArgumentException("Student email cannot be empty.");
        }
        if (isBlank(student.getCourse())) {
            throw new IllegalArgumentException("Course cannot be empty.");
        }
    }

    private void ensureNoDuplicate(Student student) {
        Node current = head;

        while (current != null) {
            boolean sameName = current.data.getName().equalsIgnoreCase(student.getName().trim());
            boolean sameEmail = current.data.getEmail().equalsIgnoreCase(student.getEmail().trim());

            if (sameName || sameEmail) {
                throw new IllegalArgumentException("Duplicate student name or email found.");
            }

            current = current.next;
        }
    }

    private int compareName(Student first, Student second) {
        return first.getName().compareToIgnoreCase(second.getName());
    }

    private boolean matchesKeyword(Student student, String keywordLower) {
        return student.getName().toLowerCase().contains(keywordLower)
                || student.getCourse().toLowerCase().contains(keywordLower);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
