package com.coursework.enrolment.service;

import com.coursework.enrolment.model.Student;
import com.coursework.enrolment.structure.DoublyLinkedList;

/**
 * Service layer between the UI and the Doubly Linked List.
 *
 * UI should call this class instead of directly changing the DLL.
 * This keeps the code cleaner and easier for group members to work on.
 */
public class StudentEnrolmentService {
    private DoublyLinkedList studentList;
    private int nextId;

    public StudentEnrolmentService() {
        this.studentList = new DoublyLinkedList();
        this.nextId = 1;
    }

    /**
     * TODO: Task 2
     * Validate input, create Student object, then call studentList.addStudent(student).
     */
    public boolean addStudent(String name, String email, String course) {
        Student student = new Student(nextId, name, email, course);
        boolean isAdded = studentList.addStudent(student);

        if (isAdded) {
            nextId++;
        }

        return isAdded;
    }

    /**
     * TODO: Task 2
     */
    public boolean deleteStudent(String name) {
        return studentList.deleteStudentByName(name);
    }

    /**
     * Task 3
     */
    public Student[] searchStudents(String keyword) {
        return studentList.searchStudents(keyword);
    }

    /**
     * Task 3
     */
    public Student[] getAllStudents() {
        return studentList.getAllStudents();
    }

    /**
     * Task 3
     */
    public Student[] getReverseCopy() {
        return studentList.getReverseCopy();
    }

    /**
     * TODO: Task 4
     */
    public String generateReport() {
        return studentList.generateReport();
    }
}
