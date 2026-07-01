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
     * TODO: Task 2
     * Add a new student record in sorted order by student name.
     * Also handle empty input and duplicate records.
     */
    public boolean addStudent(Student student) {
        return false;
    }

    /**
     * TODO: Task 2
     * Delete a student record by student name.
     * Must handle deleting first node, middle node, last node, and record not found.
     */
    public boolean deleteStudentByName(String name) {
        return false;
    }

    /**
     * TODO: Task 3
     * Search a student record by name or course.
     */
    public Student searchStudent(String keyword) {
        return null;
    }

    /**
     * TODO: Task 3
     * Return all student records from head to tail.
     * The UI will use this to refresh the table.
     */
    public Student[] getAllStudents() {
        return new Student[0];
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
     * Example: total records and number of students by course.
     */
    public String generateReport() {
        return "Report function is not implemented yet.";
    }

    /**
     * Helper method for future members to update size safely.
     */
    protected void increaseSize() {
        size++;
    }

    /**
     * Helper method for future members to update size safely.
     */
    protected void decreaseSize() {
        if (size > 0) {
            size--;
        }
    }
}
