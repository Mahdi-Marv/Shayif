package Controllers;

import Model.Student;
import extra.Saver;
import extra.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import website.EDU;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DefendRequest implements Initializable {

    @FXML
    Button exitBtn;
    @FXML
    TextField text;
    @FXML
    Button defendBtn;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Student student = (Student) EDU.getInstance().getCurrentUser();
        if (student.getDefendTime() != null){
            displayDefendDate();
        }

    }

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
    public void setDefendTime(ActionEvent actionEvent) {
        LocalDate defendDate = LocalDate.now().plusMonths(3);
        Student student = (Student) EDU.getInstance().getCurrentUser();
        student.setDefendTime(defendDate);
        displayDefendDate();

    }
    public void displayDefendDate(){
        Student student = (Student) EDU.getInstance().getCurrentUser();
        text.setText("Your defend time: " + student.getDefendTime().toString());
        defendBtn.setVisible(false);
    }
}
