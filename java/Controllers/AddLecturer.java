package Controllers;

import Model.*;
import extra.GsonHandler;
import extra.Saver;
import extra.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.commons.io.FilenameUtils;
import website.EDU;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.LinkedList;

public class AddLecturer {

    @FXML
    Button exitBtn;
    @FXML
    TextField errorMessage;
    @FXML
    TextField nameText;
    @FXML
    TextField phoneNumberText;
    @FXML
    TextField typeText;
    @FXML
    TextField lecturerNumberText;
    @FXML
    TextField nationalCodeText;
    @FXML
    TextField emailText;
    @FXML
    TextField rankText;
    @FXML
    TextField officeNumberText;


    public void exit(ActionEvent actionEvent) {
        Saver.save();
        System.exit(0);
    }

    public void returnHome(ActionEvent actionEvent) {
        if (EDU.getInstance().getCurrentUser() instanceof Student){
            SceneChanger.changeScene("StudentHomePage", exitBtn);
        }else
            SceneChanger.changeScene("LecturerHomePage", exitBtn);
    }

    public void registerLecturer(ActionEvent actionEvent) {
        try {
            String name = nameText.getText();
            String phoneNumber = phoneNumberText.getText();
            String email = emailText.getText();
            String nationalCode = nationalCodeText.getText();
            String rank = rankText.getText();
            String type = typeText.getText();
            int officeNumber = Integer.parseInt(officeNumberText.getText());
            int lecturerNumber = Integer.parseInt(lecturerNumberText.getText());


            if (name.equals("") || phoneNumber.equals("") || email.equals("")
            || nationalCode.equals("") || rank.equals("") || type.equals(""))
                errorMessage.setText("Invalid Input");
            else{
                if (lecturerExist(nationalCode)){
                    errorMessage.setText("User Already Exists");
                    return;
                }
                Lecturer lecturer = (Lecturer) EDU.getInstance().getCurrentUser();
                LecturerRank lecturerRank;
                switch (rank){
                    case "assistantProfessor":
                        lecturerRank = LecturerRank.assistantProfessor;
                        break;
                    case "associateProfessor":
                        lecturerRank = LecturerRank.associateProfessor;
                        break;
                    case "professor":
                        lecturerRank = LecturerRank.professor;
                        break;
                    default:
                        errorMessage.setText("Invalid input");
                        return;
                }


                LecturerType lecturerType;
                switch (type){
                    case "normal":
                        lecturerType = LecturerType.normal;
                        break;
                    case "headOfEducation":
                        lecturerType = LecturerType.headOfEducation;
                        break;
                    default:
                        errorMessage.setText("Invalid input");
                        return;

                }
                if (lecturerType == LecturerType.headOfEducation)
                    changeRole(lecturer.getDepartment());

                new Lecturer(name, nationalCode, lecturer.getDepartment(),email,
                        phoneNumber, lecturerType, lecturerRank, lecturerNumber, officeNumber);




            }


        }catch (InputMismatchException | NumberFormatException exception){
            errorMessage.setText("Invalid Input");
        }

    }

    private void changeRole(int departmentCode){

        File dir = new File("src\\main\\resources\\model\\lecturers");
        File[] directoryListing = dir.listFiles();

        assert directoryListing != null;

        for (File child : directoryListing) {
            GsonHandler gsonHandler = new GsonHandler();
            try {
                String fileNameWithOutExt = FilenameUtils.removeExtension(child.getName());
                Lecturer lecturer = gsonHandler.readLecturer(fileNameWithOutExt);
                if (lecturer.getDepartment() == departmentCode && lecturer.getLecturerType() == LecturerType.headOfEducation){
                    lecturer.setLecturerType(LecturerType.normal);
                    new GsonHandler().saveLecturer(lecturer);
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean lecturerExist(String nationalCode){
        File dir = new File("src\\main\\resources\\model\\lecturers");
        File[] directoryListing = dir.listFiles();

        assert directoryListing != null;
        for (File child : directoryListing){
            String fileNameWithOutExt = FilenameUtils.removeExtension(child.getName());
            if (fileNameWithOutExt.equals(nationalCode))
                return true;
        }

        return false;
    }

}
