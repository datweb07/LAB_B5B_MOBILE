package com.example.lab_5b_mobile;

import java.util.List;

public class Classroom {
    private final String code;
    private final String homeroomTeacher;
    private final List<Student> students;

    public Classroom(String code, String homeroomTeacher, List<Student> students) {
        this.code = code;
        this.homeroomTeacher = homeroomTeacher;
        this.students = students;
    }

    public String getCode() { return code; }
    public String getHomeroomTeacher() { return homeroomTeacher; }
    public List<Student> getStudents() { return students; }
}
