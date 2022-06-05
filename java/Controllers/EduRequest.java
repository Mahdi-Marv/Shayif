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

public class EduRequest {

    @FXML
    Button exitBtn;
    @FXML
    TextField result;
    @FXML
    Button eduRequest;




    public void sendRequest(ActionEvent actionEvent) {
        Student student = (Student) EDU.getInstance().getCurrentUser();
        LocalDate date = LocalDate.now().plusYears(3);
        result.setText(date.toString());
        eduRequest.setVisible(false);
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

}
