package Controllers;

import Model.Student;
import extra.GsonHandler;
import extra.Saver;
import extra.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import website.EDU;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileStudent implements Initializable {
    @FXML
    public ImageView profilePic;
    @FXML
    public TextField type;
    @FXML
    public TextField entryYear;
    @FXML
    public TextField educationState;
    @FXML
    public TextField email;
    @FXML
    public TextField phoneNumber;
    @FXML
    public TextField gpa;
    @FXML
    public TextField department;
    @FXML
    public TextField studentNumber;
    @FXML
    public TextField name;
    @FXML
    public TextField nationalCode;
    @FXML
    public TextField supervisor;
    @FXML
    public Button exitBtn;


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Student student = (Student) EDU.getInstance().getCurrentUser();
        profilePic.setImage(new Image(student.getProfilePicAddress()));
        type.setText(student.getStudentType().toString());
        entryYear.setText(String.valueOf(student.getEntryYear()));
        if (student.getEducationState() == null)
            educationState.setText("no state");
        else
            educationState.setText(student.getEducationState().toString());
        email.setText(student.getEmail());
        phoneNumber.setText(student.getPhoneNumber());
        try {
            gpa.setText(String.valueOf(student.getGpa()));
            department.setText(new GsonHandler().readDepartment(student.getDepartment()).getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        studentNumber.setText(student.getStudentNumber());
        name.setText(student.getName());
        nationalCode.setText(student.getNationalCode());
        supervisor.setText(student.getSupervisor().getName());
    }


}
