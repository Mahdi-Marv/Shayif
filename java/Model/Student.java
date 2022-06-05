package Model;

import extra.GsonHandler;
import website.EDU;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class Student extends User{

    private Lecturer supervisor;
    private String studentNumber;
    private StudentType studentType;
    private LinkedList<Integer> coursesEnrolled;
    private LinkedList<Integer> coursesPassed;
    private LinkedList<Integer> coursesFailed;
    private final LinkedList<Integer> sentRequest;
    private final LinkedList<Integer> respondedRequest;
    private LocalDate defendTime;
    private int entryYear;
    private EducationState educationState;
    private final double gpa;


    public Student(String name, String nationalCode, int department, String phoneNumber, String email,
                   Lecturer supervisor, String studentNumber, StudentType studentType, int entryYear) {
        super(name, nationalCode, department, phoneNumber, email);
        this.supervisor = supervisor;
        this.studentNumber = studentNumber;
        this.studentType = studentType;
        sentRequest = new LinkedList<>();
        respondedRequest = new LinkedList<>();
        this.entryYear = entryYear;
        gpa = 0;

        Department department1 = EDU.getInstance().getDepartment(department);
        department1.getStudentIds().add(this.getNationalCode());
        coursesEnrolled = new LinkedList<>();
        coursesPassed = new LinkedList<>();
        coursesFailed = new LinkedList<>();

        try {
            new GsonHandler().saveDepartment(department1);
            new GsonHandler().saveStudent(this);
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    public Lecturer getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Lecturer supervisor) {
        this.supervisor = supervisor;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public StudentType getStudentType() {
        return studentType;
    }

    public void setStudentType(StudentType studentType) {
        this.studentType = studentType;
    }

    public LinkedList<Integer> getCoursesEnrolled() {
        return coursesEnrolled;
    }

    public void setCoursesEnrolled(LinkedList<Integer> coursesEnrolled) {
        this.coursesEnrolled = coursesEnrolled;
    }

    public LinkedList<Integer> getSentRequest() {
        return sentRequest;
    }

    public LinkedList<Integer> getRespondedRequest() {
        return respondedRequest;
    }

    public LocalDate getDefendTime() {
        return defendTime;
    }

    public void setDefendTime(LocalDate defendTime) {
        this.defendTime = defendTime;
    }

    public LinkedList<Integer> getCoursesPassed() {
        return coursesPassed;
    }

    public void setCoursesPassed(LinkedList<Integer> coursesPassed) {
        this.coursesPassed = coursesPassed;
    }

    public int getEntryYear() {
        return entryYear;
    }

    public void setEntryYear(int entryYear) {
        this.entryYear = entryYear;
    }

    public EducationState getEducationState() {
        return educationState;
    }

    public void setEducationState(EducationState educationState) {
        this.educationState = educationState;
    }

    public double getGpa()  {
        if (getCoursesPassed().size()!=0) {
            double gpa = 0;
            int credits = 0;
            for (int id : coursesPassed) {
                Course course = null;
                try {
                    course = new GsonHandler().readCourse(id);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                assert course != null;
                gpa += course.getFinalGrades().get(studentNumber) * course.getCredit();
                credits += course.getCredit();
            }
            return gpa / credits;
        }else
            return 0.0;
    }

    public LinkedList<Integer> getCoursesFailed() {
        return coursesFailed;
    }

    public void setCoursesFailed(LinkedList<Integer> coursesFailed) {
        this.coursesFailed = coursesFailed;
    }
}

