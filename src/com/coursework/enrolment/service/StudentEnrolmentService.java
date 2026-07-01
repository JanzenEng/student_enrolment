package com.coursework.enrolment.service;

import com.coursework.enrolment.model.Student;
import com.coursework.enrolment.structure.DoublyLinkedList;

/**
 * Service layer between the UI and the Doubly Linked List.
 *
 * UI classes should call this service instead of directly changing the DLL.
 * This makes the code easier for group members to maintain.
 */
public class StudentEnrolmentService {
    private final DoublyLinkedList studentList;
    private int nextStudentId;

    public StudentEnrolmentService() {
        this.studentList = new DoublyLinkedList();
        this.nextStudentId = 1;
    }

    public void addStudent(String name, String email, String course) {
        Student student = new Student(nextStudentId, clean(name), clean(email), clean(course));
        studentList.addStudent(student);
        nextStudentId++;
    }

    public boolean deleteStudentById(int studentId) {
        return studentList.deleteStudentById(studentId);
    }

    public boolean deleteStudentByName(String name) {
        return studentList.deleteStudentByName(name);
    }

    public Student findStudentById(int studentId) {
        return studentList.findStudentById(studentId);
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

    public void seedSampleData() {
        addStudent("Alice Smith", "alice@univ.edu", "Java Programming");
        addStudent("Bob Jones", "bob@univ.edu", "Web Development");
    }

    private String clean(String value) {
        return value == null ? "" : value.trim();
    }
}
