package website;

import Model.*;
import extra.GsonHandler;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class EDU {
    private static EDU edu;
    private User currentUser;
    private Time time;


    public static EDU getInstance(){
        if (edu == null)
            edu = new EDU();
        return edu;
    }

    private EDU(){
        time = new Time("Spring", 2022, "12", 32);
    }

    public Department getDepartment(int id) {
        GsonHandler gsonHandler = new GsonHandler();
        try {
            return gsonHandler.readDepartment(id);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Student getStudent(String nationalCode) {
        GsonHandler gsonHandler = new GsonHandler();
        try {
            return gsonHandler.readStudent(nationalCode);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Lecturer getLecturer(String id) {
        try {
            return new GsonHandler().readLecturer(id);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setEdu(EDU edu) { EDU.edu = edu;}

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
