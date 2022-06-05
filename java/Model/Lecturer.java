package Model;

import extra.GsonHandler;
import website.EDU;

import java.io.IOException;
import java.util.LinkedList;

public class Lecturer extends User{

    private LecturerType lecturerType;
    private LecturerRank lecturerRank;
    private final LinkedList<Integer> requestsNotResponded;
    private final LinkedList<Integer> requestsResponded;
    private int lecturerNumber;
    private int  officeNumber;



    public Lecturer(String name, String nationalCode, int department, String phoneNumber,String email,
                    LecturerType lecturerType, LecturerRank lecturerRank, int lecturerNumber, int officeNumber) {
        super(name, nationalCode, department, phoneNumber, email);
        this.lecturerType = lecturerType;
        this.lecturerRank = lecturerRank;
        requestsNotResponded = new LinkedList<>();
        requestsResponded = new LinkedList<>();
        this.lecturerNumber = lecturerNumber;
        this.officeNumber = officeNumber;
        Department department1 = EDU.getInstance().getDepartment(this.getDepartment());

        if (lecturerType == LecturerType.headOfDepartment)
            department1.setHeadOfDepartment(this);

        if (lecturerType == LecturerType.headOfEducation)
            department1.setHeadOfEducation(this);


        department1.getLecturerIds().add(nationalCode);

        try {
            new GsonHandler().saveLecturer(this);
            new GsonHandler().saveDepartment(department1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LecturerType getLecturerType() {
        return lecturerType;
    }

    public void setLecturerType(LecturerType lecturerType) {
        this.lecturerType = lecturerType;
    }

    public LecturerRank getLecturerRank() {
        return lecturerRank;
    }

    public void setLecturerRank(LecturerRank lecturerRank) {
        this.lecturerRank = lecturerRank;
    }

    public LinkedList<Integer> getRequestsNotResponded() {
        return requestsNotResponded;
    }

    public LinkedList<Integer> getRequestsResponded() {
        return requestsResponded;
    }

    public int getLecturerNumber() {
        return lecturerNumber;
    }

    public int getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(int officeNumber) {
        this.officeNumber = officeNumber;
    }
    public void setLecturerNumber(int lecturerNumber) {
        this.lecturerNumber = lecturerNumber;
    }
}
