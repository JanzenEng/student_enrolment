package com.coursework.enrolment.service;

import com.coursework.enrolment.model.Student;
import com.coursework.enrolment.structure.DoublyLinkedList;

/**
 * Service layer between the UI and the Doubly Linked List.
 * UI should call this class instead of directly changing the DLL.
 */
public class StudentEnrolmentService {
    private DoublyLinkedList studentList;
    private int nextId;

    public StudentEnrolmentService() {
        this.studentList = new DoublyLinkedList();
        this.nextId = 1;
    }

    public boolean addStudent(String name, String icNumber, String email, String phoneNumber, String dateOfBirth, String course) {
        Student student = new Student(nextId, name, icNumber, email, phoneNumber, dateOfBirth, course);
        boolean isAdded = studentList.addStudent(student);

        if (isAdded) {
            nextId++;
        }

        return isAdded;
    }

    /**
     * Delete by email because email is unique.
     */
    public boolean deleteStudent(String email) {
        return studentList.deleteStudentByEmail(email);
    }

    public Student[] searchStudents(String keyword) {
        return studentList.searchStudents(keyword);
    }

    public Student[] getAllStudents() {
        return studentList.getAllStudents();
    }

    public Student[] getReverseCopy() {
        return studentList.getReverseCopy();
    }

    public String generateReport() {
        return studentList.generateReport();
    }
}
