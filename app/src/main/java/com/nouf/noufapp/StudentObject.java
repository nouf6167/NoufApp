package com.nouf.noufapp;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class StudentObject implements Serializable {

    private String id;
    private String studentId;
    private String studentName;
    private String studentSurName;
    private String studentFatherName;
    private String studentNationalId;
    private String studentDateOfBirth;
    private String studentGender;


    public StudentObject() {
    }

    public StudentObject(String id, String studentId, String studentName, String studentSurName, String studentFatherName, String studentNationalId, String studentDateOfBirth, String studentGender) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentSurName = studentSurName;
        this.studentFatherName = studentFatherName;
        this.studentNationalId = studentNationalId;
        this.studentDateOfBirth = studentDateOfBirth;
        this.studentGender = studentGender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSurName() {
        return studentSurName;
    }

    public void setStudentSurName(String studentSurName) {
        this.studentSurName = studentSurName;
    }

    public String getStudentFatherName() {
        return studentFatherName;
    }

    public void setStudentFatherName(String studentFatherName) {
        this.studentFatherName = studentFatherName;
    }

    public String getStudentNationalId() {
        return studentNationalId;
    }

    public void setStudentNationalId(String studentNationalId) {
        this.studentNationalId = studentNationalId;
    }

    public String getStudentDateOfBirth() {
        return studentDateOfBirth;
    }

    public void setStudentDateOfBirth(String studentDateOfBirth) {
        this.studentDateOfBirth = studentDateOfBirth;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

    @Override
    public String toString() {
        return "StudentObject{" +
                "id='" + id + '\'' +
                ", studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", studentSurName='" + studentSurName + '\'' +
                ", studentFatherName='" + studentFatherName + '\'' +
                ", studentNationalId='" + studentNationalId + '\'' +
                ", studentDateOfBirth='" + studentDateOfBirth + '\'' +
                ", studentGender='" + studentGender + '\'' +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("studentId", studentId);
        result.put("studentName", studentName);
        result.put("studentSurName", studentSurName);
        result.put("studentFatherName", studentFatherName);
        result.put("studentNationalId", studentNationalId);
        result.put("studentDateOfBirth", studentDateOfBirth);
        result.put("studentGender", studentGender);

        return result;
    }

}
