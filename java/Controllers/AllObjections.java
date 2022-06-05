package Controllers;

import Model.Lecturer;
import Model.Request;
import Model.RequestType;
import Model.Student;
import extra.GsonHandler;
import extra.Saver;
import extra.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import website.EDU;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class AllObjections implements Initializable {
    @FXML
    Button exitBtn;
    @FXML
    TextArea reqText;

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
        Lecturer onlineLecturer = (Lecturer) EDU.getInstance().getCurrentUser();
        GsonHandler gsonHandler = new GsonHandler() ;
        String text = "";
        for (int requestId = 1; requestId <= Request.getID(); requestId++) {
            try {
                Request request = gsonHandler.readRequest(requestId);
                Student student = request.getSender();
                Lecturer lecturer = request.getReceiver();
                String response = request.getResponse() == null?"no response":request.getResponse();
                if (request.getRequestType() == RequestType.objection
                && student.getDepartment() == onlineLecturer.getDepartment()){
                    text += "from " + student.getName() + " to " + lecturer.getName()
                            + ". request text: " + request.getText() + ". request response: " +
                            response + "\n";
                }



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            reqText.setText(text);
        }


    }
}
