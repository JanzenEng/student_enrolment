package com.coursework.enrolment.model;

/**
 * Represents one student enrolment record.
 *
 * This class only stores data. The records are managed by the custom
 * DoublyLinkedList class, not by a database or Java built-in list.
 */
public class Student {
    private final int id;
    private final String name;
    private final String email;
    private final String course;

    public Student(int id, String name, String email, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return id + " - " + name + " (" + course + ")";
    }
}
