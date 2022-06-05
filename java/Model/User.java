package Model;

import extra.Hash;

import java.time.LocalDate;
import java.time.LocalTime;

public class User {
    private int departmentId;
    private String name;
    private String nationalCode;
    private String hashedPassword;
    private String email;
    private String profilePicAddress;
    private LocalDate lastOnline;
    private String phoneNumber;
    private LocalTime onlineTime;
    private boolean isDeleted;


    public User(String name, String nationalCode, int departmentId, String phoneNumber, String email){
        this.name = name;
        this.nationalCode = nationalCode;
        this.hashedPassword = Hash.getInstance().hash(nationalCode);
        this.departmentId = departmentId;
        this.phoneNumber = phoneNumber;
        this.email = email;
        profilePicAddress = "Profile pictures/proPic.png";
        this.isDeleted = false;

    }

    public String getNationalCode() {
        return nationalCode;
    }


    public int getDepartment() {
        return departmentId;
    }

    public void setDepartment(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(LocalDate lastOnline) {
        this.lastOnline = lastOnline;
    }

    public String getProfilePicAddress() {
        return profilePicAddress;
    }

    public void setProfilePicAddress(String profilePicAddress) {
        this.profilePicAddress = profilePicAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalTime getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(LocalTime onlineTime) {
        this.onlineTime = onlineTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }
}
