package Controllers;




import Model.Student;

import extra.Saver;
import extra.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import website.EDU;


public class PhdRequests {
    @FXML
    Button exitBtn;


    public void eduRequest(ActionEvent actionEvent) {
        SceneChanger.changeScene("EduRequest", exitBtn);
    }

    public void cancelRequest(ActionEvent actionEvent) {
        SceneChanger.changeScene("CancelRequest", exitBtn);
    }

    public void defendRequest(ActionEvent actionEvent) {
        SceneChanger.changeScene("DefendRequest", exitBtn);
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
