package Controllers;

import Model.Student;
import Model.StudentType;
import extra.Saver;
import extra.SceneChanger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import website.EDU;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class StudentHomePage implements Initializable {
    @FXML
    public TextField lastOnline;
    @FXML
    public TextField educationStateText;
    @FXML
    public TextField mentorName;
    @FXML
    public TextField registerTimeText;
    @FXML
    public TextField mentorText;
    @FXML
    public TextField registerTimeText2;
    @FXML
    public TextField eligibleText;
    @FXML
    public TextField nameEmail;
    @FXML
    TextField time;
    @FXML
    Button exitButton;
    @FXML
    ImageView profilePic;








    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                time.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        Student student = (Student) EDU.getInstance().getCurrentUser();
        System.out.println(student.getProfilePicAddress());
        Image image = new Image(student.getProfilePicAddress());
        profilePic.setImage(image);
        profilePic.setVisible(true);


        if (student.getLastOnline()!=null){
            lastOnline.setText(EDU.getInstance().getCurrentUser().getLastOnline().toString());
        }else{
            lastOnline.setText("No last Time");
        }
        if (student.getEducationState()!=null)
            educationStateText.setText(student.getEducationState().toString());
        else
            educationStateText.setText("no info");
        mentorName.setText(student.getSupervisor().getName());
        mentorText.setText("Your supervisor name: " + student.getSupervisor().getName());
        eligibleText.setText("you are eligible to register");
        registerTimeText.setText(registerTimeText.getText() + " " + LocalDate.now().plusMonths(3).toString());
        registerTimeText2.setText("You are eligible to register in " + LocalDate.now().plusMonths(3).toString());
        if (student.getEmail() == null)
            nameEmail.setText("name: " + student.getName() + ", email: no info");
        else
            nameEmail.setText("name: " + student.getName() + ", email: " + student.getEmail());


    }

    public void exitProgram(ActionEvent actionEvent){
        Saver.save();
        System.exit(0);
    }

    public void seeCourses(ActionEvent actionEvent) {
        SceneChanger.changeScene("Courses", exitButton);
    }

    public void seeLecturers(ActionEvent actionEvent) {
        SceneChanger.changeScene("Lecturers", exitButton);
    }

    public void seeWeek(ActionEvent actionEvent) {
        SceneChanger.changeScene("WeeklySchedule", exitButton);
    }

    public void seeFinalExams(ActionEvent actionEvent) {
        SceneChanger.changeScene("FinalExamSchedule", exitButton);
    }

    public void requests(ActionEvent actionEvent) {
        Student student = (Student) EDU.getInstance().getCurrentUser();
        if (student.getStudentType() == StudentType.bachelor){
            SceneChanger.changeScene("BachelorRequests", exitButton);
        }else if (student.getStudentType() == StudentType.master){
            SceneChanger.changeScene("MasterRequests", exitButton);
        }else{
            SceneChanger.changeScene("PhdRequests", exitButton);
        }
    }

    public void tempGrades(ActionEvent actionEvent) {
        SceneChanger.changeScene("TemporaryGrades", exitButton);
    }

    public void educationStats(ActionEvent actionEvent) {
        SceneChanger.changeScene("EducationStats", exitButton);
    }

    public void profile(ActionEvent actionEvent) {
        SceneChanger.changeScene("ProfileStudent", exitButton);
    }

    public void homePage(ActionEvent actionEvent) {
        SceneChanger.changeScene("StudentHomePage", exitButton);
    }
}
