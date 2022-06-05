package Controllers;

import Model.Lecturer;
import Model.LecturerType;
import extra.Saver;
import extra.SceneChanger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import website.EDU;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class LecturerHomePage implements Initializable {
    @FXML
    Menu addUser;
    @FXML
    Button exitBtn;
    @FXML
    TextField time;
    @FXML
    TextField name_email;
    @FXML
    ImageView profilePic;
    @FXML
    MenuItem eduState;
    @FXML
    TextField lastOnline;

    public void goToProfile(ActionEvent actionEvent) {
        SceneChanger.changeScene("ProfileLecturer", exitBtn);
    }


    public void exit(ActionEvent actionEvent) {
        Saver.save();
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                time.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        Lecturer lecturer = (Lecturer) EDU.getInstance().getCurrentUser();

        profilePic.setImage(new Image(lecturer.getProfilePicAddress()));
        name_email.setText("name: " + lecturer.getName() + ", email: " + lecturer.getEmail());
        Lecturer onlineLecturer = (Lecturer) EDU.getInstance().getCurrentUser();
        if (onlineLecturer.getLecturerType() != LecturerType.headOfEducation)
            eduState.setVisible(false);

        if (onlineLecturer.getLastOnline()!=null){
            lastOnline.setText(EDU.getInstance().getCurrentUser().getLastOnline().toString());
        }else{
            lastOnline.setText("No last Time");
        }
        if (onlineLecturer.getLecturerType() != LecturerType.headOfEducation)
            addUser.setVisible(false);
    }

    public void goToCourses(ActionEvent actionEvent) {
        SceneChanger.changeScene("Courses", exitBtn);
    }

    public void goToLecturers(ActionEvent actionEvent) {
        SceneChanger.changeScene("Lecturers",  exitBtn);
    }

    public void seeWeeklyTable(ActionEvent actionEvent) {
        SceneChanger.changeScene("WeeklySchedule", exitBtn);
    }

    public void seeFinalExamSchedule(ActionEvent actionEvent) {
        SceneChanger.changeScene("FinalExamSchedule", exitBtn);
    }

    public void seeRequests(ActionEvent actionEvent) {
        SceneChanger.changeScene("LecturerRequests", exitBtn);
    }

    public void goToTempGrades(ActionEvent actionEvent) {
        SceneChanger.changeScene("LecturerTempGrades", exitBtn);
    }

    public void goToEduState(ActionEvent actionEvent) {
        SceneChanger.changeScene("EducationStats", exitBtn);
    }

    public void goToAddUser(ActionEvent actionEvent) {
        SceneChanger.changeScene("NewStudent", exitBtn);
    }
}
