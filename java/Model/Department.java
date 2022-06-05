package Model;

import extra.GsonHandler;
import website.EDU;

import java.io.IOException;
import java.util.ArrayList;

public class Department {
    private static int ID = 1;
    private final int id;
    private final String name;
    private  ArrayList<String> lecturerIds;
    private  ArrayList<String> studentIds;
    private  ArrayList<Integer> courseIds;
    private  Lecturer headOfDepartment;
    private  Lecturer headOfEducation;


    public Department(String name) {
        id = ID;
        ID++;
        this.name = name;
        lecturerIds = new ArrayList<>();
        studentIds = new ArrayList<>();
        courseIds = new ArrayList<>();
        try {
            new GsonHandler().saveDepartment(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getLecturerIds() {
        return lecturerIds;
    }

    public Lecturer getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setHeadOfDepartment(Lecturer headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }

    public Lecturer getHeadOfEducation() {
        return headOfEducation;
    }

    public void setHeadOfEducation(Lecturer headOfEducation) {
        this.headOfEducation = headOfEducation;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getStudentIds() {
        return studentIds;
    }

    public ArrayList<Integer> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(ArrayList<Integer> courseIds) {
        this.courseIds = courseIds;
    }

    public void setLecturerIds(ArrayList<String> lecturerIds) {
        this.lecturerIds = lecturerIds;
    }

    public void setStudentIds(ArrayList<String> studentIds) {
        this.studentIds = studentIds;
    }

    public int getId() {
        return id;
    }

    public static void setID(int ID) {
        Department.ID = ID;
    }
}
