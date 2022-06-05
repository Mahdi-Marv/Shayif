package Controllers;

import Model.*;
import extra.GsonHandler;
import extra.Saver;
import extra.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import website.EDU;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class CancelRequest implements Initializable {
    @FXML
    public TextField resultText;
    @FXML
    Button exitBtn;
    @FXML
    Button requestBtn;


    public void requestCancel(ActionEvent actionEvent) {
        Student student = (Student) EDU.getInstance().getCurrentUser();
        Department department = EDU.getInstance().getDepartment(student.getDepartment());
        Lecturer lecturer = department.getHeadOfEducation();
        Request request = new Request(student, lecturer, "drop out", RequestType.cancel);
        Request.send(request);
        write();
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


    public void write(){
        resultText.setText("Cancel Request Sent");
        requestBtn.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Student student = (Student) EDU.getInstance().getCurrentUser();
        for (int id : student.getSentRequest()){
            try {
                Request request = new GsonHandler().readRequest(id);
                if (request.getRequestType() == RequestType.cancel){
                    if (request.isResponded()) {
                        resultText.setText(request.getResponse());
                    }else{
                        resultText.setText("Request is sent");
                    }
                    requestBtn.setVisible(false);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
