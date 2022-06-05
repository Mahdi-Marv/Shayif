package Controllers;

import Model.Department;
import Model.EducationState;
import Model.Lecturer;
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

public class ProfileLecturer implements Initializable {
    @FXML
    public TextField lecturerNumber;
    @FXML
    public TextField phoneNumber;
    @FXML
    public TextField email;
    @FXML
    public TextField department;
    @FXML
    public TextField officeNumber;
    @FXML
    public TextField rank;
    @FXML
    public ImageView profilePic;
    @FXML
    public TextField name;
    @FXML
    public Button exitBtn;
    @FXML
    public TextField nationalCode;


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
        Lecturer lecturer = (Lecturer) EDU.getInstance().getCurrentUser();

        lecturerNumber.setText(String.valueOf(lecturer.getLecturerNumber()));
        phoneNumber.setText(lecturer.getPhoneNumber());
        nationalCode.setText(lecturer.getNationalCode());
        name.setText(lecturer.getName());
        profilePic.setImage(new Image(lecturer.getProfilePicAddress()));
        rank.setText(lecturer.getLecturerRank().toString());
        officeNumber.setText(String.valueOf(lecturer.getOfficeNumber()));
        try {
            Department department1 = new GsonHandler().readDepartment(lecturer.getDepartment());
            department.setText(department1.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (lecturer.getEmail()!=null)
            email.setText(lecturer.getEmail());
        else
            email.setText("no email");



        phoneNumber.setText(lecturer.getPhoneNumber());
    }
}
