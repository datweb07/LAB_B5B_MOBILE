package com.example.lab_5b_mobile;

public class Student {
    private final String id;
    private final String fullName;

    public Student(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public String getId() { return id; }
    public String getFullName() { return fullName; }
}
