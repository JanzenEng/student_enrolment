# Student Enrolment Program

A Java Swing application for managing student enrolment records using a Doubly Linked List (DLL).

## Purpose
This project is prepared as the initial structure for PRG3205E Coursework #1. The system stores student enrolment records inside a custom Doubly Linked List, not a database.

## Features
- Add new student enrolment record
- Delete student record
- Search student record by name or course
- View all student records
- View selected student details
- Display reverse copy of the list
- Generate simple enrolment report

## Project Structure
```text
src/com/coursework/enrolment/
├── model/
│   └── Student.java
├── structure/
│   └── DoublyLinkedList.java
├── service/
│   └── StudentEnrolmentService.java
└── ui/
    └── StudentEnrolmentApp.java
```

## How to Run
From the project root:

```bash
javac -d out $(find src -name "*.java")
java -cp out com.coursework.enrolment.ui.StudentEnrolmentApp
```

## GitHub Setup Commands
```bash
git init
git add .
git commit -m "Initial Java structure for Student Enrolment DLL application"
git branch -M main
git remote add origin https://github.com/<your-username>/<your-repo-name>.git
git push -u origin main
```

## Notes for Group Members
The main data storage is the custom `DoublyLinkedList`. The UI table only displays data from the DLL.

Suggested task ownership:
- Member 1: Initial structure, ADT design, GitHub repo, UI layout
- Member 2: Add and delete record functions
- Member 3: Search, view all, and reverse copy functions
- Member 4: Report, testing, screenshots, and documentation
