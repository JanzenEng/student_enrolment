package com.coursework.enrolment.model;

/**
 * Model class for one student enrolment record.
 * This class only stores data. DLL logic should not be placed here.
 */
public class Student {
    private int id;
    private String name;
    private String icNumber;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
    private String course;

    public Student(int id, String name, String icNumber, String email, String phoneNumber, String dateOfBirth, String course) {
        this.id = id;
        this.name = name;
        this.icNumber = icNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcNumber() {
        return icNumber;
    }

    public void setIcNumber(String icNumber) {
        this.icNumber = icNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
