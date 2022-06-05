package Controllers;

import Model.Lecturer;
import Model.LecturerRank;
import Model.LecturerType;
import Model.Student;
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

public class EditLecturer {
    @FXML
    public Button exitBtn;
    @FXML
    public TextField nameText;
    @FXML
    public TextField phoneNumberText;
    @FXML
    public TextField typeText;
    @FXML
    public TextField lecturerNumberText;
    @FXML
    public TextField nationalCodeText;
    @FXML
    public TextField emailText;
    @FXML
    public TextField rankText;
    @FXML
    public TextField officeNumberText;
    @FXML
    TextField errorMessage;

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

    public void editLecturer(ActionEvent actionEvent) {
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
                GsonHandler gsonHandler = new GsonHandler();
                Lecturer lecturer = gsonHandler.readLecturer(nationalCode);
                lecturer.setName(name);
                lecturer.setPhoneNumber(phoneNumber);
                lecturer.setEmail(email);
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



                switch (type){
                    case "normal":
                        lecturer.setLecturerType(LecturerType.normal);
                        break;
                    case "headOfEducation":
                        changeRole(lecturer.getDepartment());
                        lecturer.setLecturerType(LecturerType.headOfEducation);
                        break;
                    default:
                        errorMessage.setText("Invalid input");

                }
                lecturer.setPhoneNumber(phoneNumberText.getText());
                lecturer.setOfficeNumber(Integer.parseInt(officeNumberText.getText()));
                lecturer.setLecturerNumber(Integer.parseInt(lecturerNumberText.getText()));
                lecturer.setEmail(email);
                errorMessage.setText("Lecturer edited");
                gsonHandler.saveLecturer(lecturer);
            }



        }catch (NumberFormatException | FileNotFoundException e){
            errorMessage.setText("Invalid Input");
        } catch (IOException e) {
            e.printStackTrace();
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
                    gsonHandler.saveLecturer(lecturer);
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean lecturerExistNot(String nationalCode) throws FileNotFoundException {
        Lecturer lecturer = (Lecturer) EDU.getInstance().getCurrentUser();
        File dir = new File("src\\main\\resources\\model\\lecturers");
        File[] directoryListing = dir.listFiles();

        assert directoryListing != null;
        for (File child : directoryListing){
            String fileNameWithOutExt = FilenameUtils.removeExtension(child.getName());
            if (!nationalCode.equals(fileNameWithOutExt))
                continue;
            Lecturer lecturer1 = new GsonHandler().readLecturer(nationalCode);
            if (lecturer.getDepartment() == lecturer1.getDepartment())
                return false;
        }

        return true;
    }

    public void showLecturer(ActionEvent actionEvent) throws FileNotFoundException {
        String nationalCode = nationalCodeText.getText();
        if(lecturerExistNot(nationalCode)){
            errorMessage.setText("Lecturer doesn't exist");
        }else{
            Lecturer lecturer = new GsonHandler().readLecturer(nationalCode);
            nameText.setText(lecturer.getName());
            nationalCodeText.setText(lecturer.getNationalCode());
            rankText.setText(lecturer.getLecturerRank().toString());
            typeText.setText(lecturer.getLecturerType().toString());
            phoneNumberText.setText(lecturer.getPhoneNumber());
            emailText.setText(lecturer.getEmail());
            officeNumberText.setText(String.valueOf(lecturer.getOfficeNumber()));
            lecturerNumberText.setText(String.valueOf(lecturer.getLecturerNumber()));
        }
    }
}
