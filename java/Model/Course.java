package Model;

import extra.GsonHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Course{

    private String name;
    private int code;
    private int departmentId;
    private LinkedList<Integer> prerequisiteIds;
    private String lecturerNationalCode;
    private  ArrayList<String> studentsIds;
    private int capacity;
    private Time time;
    private Time finalExamTime;
    private int credit;
    private final HashMap<String, Double> temporaryGrades;
    private final HashMap<String, Double> finalGrades;
    private boolean isDeleted;

    public Course(String name, int code, int departmentId,
                  String lecturerId, Time time, int capacity,
                  int credit, Time finalExamTime) {
        this.name = name;
        this.code = code;
        this.departmentId = departmentId;
        this.lecturerNationalCode = lecturerId;
        prerequisiteIds =  new LinkedList<>();
        studentsIds = new ArrayList<>();
        this.time = time;
        this.finalExamTime = finalExamTime;
        this.capacity = capacity;
        this.credit = credit;
        temporaryGrades = new HashMap<>();
        finalGrades = new HashMap<>();

        try {
            GsonHandler gsonHandler = new GsonHandler();
            gsonHandler.saveCourse(this);
            Department department = gsonHandler.readDepartment(departmentId);
            department.getCourseIds().add(code);
            gsonHandler.saveDepartment(department);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public LinkedList<Integer> getPrerequisiteIds() {
        return prerequisiteIds;
    }

    public void setPrerequisiteIds(LinkedList<Integer> prerequisiteIds) {
        this.prerequisiteIds = prerequisiteIds;
    }

    public String getLecturerNationalCode() {
        return lecturerNationalCode;
    }

    public void setLecturerNationalCode(String lecturerNationalCode) {
        this.lecturerNationalCode = lecturerNationalCode;
    }

    public ArrayList<String> getStudentsIds() {
        return studentsIds;
    }

    public void setStudentsIds(ArrayList<String> studentsIds) {
        this.studentsIds = studentsIds;
    }

    public void addToCourse(Student student) {
        temporaryGrades.put(student.getStudentNumber(), -1.0);
        this.studentsIds.add(student.getNationalCode());
        student.getCoursesEnrolled().add(this.code);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public HashMap<String, Double> getTemporaryGrades() {
        return temporaryGrades;
    }

    public HashMap<String, Double> getFinalGrades() {
        return finalGrades;
    }

    public Time getFinalExamTime() {
        return finalExamTime;
    }

    public void setFinalExamTime(Time finalExamTime) {
        this.finalExamTime = finalExamTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int passedStudentNumber() {
        int passed = 0;
        for (double grade : this.finalGrades.values()){
            if (grade>=10)
                passed++;
        }
        return passed;
    }

    public int failedStudentNumber(){
        return finalGrades.size() - passedStudentNumber();
    }

    public double average(){
        double average = 0;
        for (double grade: finalGrades.values()){
            average += grade;
        }
        return finalGrades.size()>0 ? average : 0;
    }
    public double passedAverage(){
        double average = 0;
        for (double grade: finalGrades.values()){
            if (grade>=10)
                average += grade;
        }
        return finalGrades.size()>0? average/passedStudentNumber():0;
    }
}
