package com.coursework.enrolment.structure;

import com.coursework.enrolment.model.Student;

/**
 * Node class for the Doubly Linked List.
 * Each node stores one Student record and links to previous/next nodes.
 */
public class Node {
    private Student data;
    private Node previous;
    private Node next;

    public Node(Student data) {
        this.data = data;
        this.previous = null;
        this.next = null;
    }

    public Student getData() {
        return data;
    }

    public void setData(Student data) {
        this.data = data;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
