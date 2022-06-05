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

public class DormRequest implements Initializable {
    @FXML
    Button exitBtn;
    @FXML
    Button requestBtn;
    @FXML
    TextField result;

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

    public void requestDorm(ActionEvent actionEvent) throws FileNotFoundException {
        Student student = (Student) EDU.getInstance().getCurrentUser();
        Department department = new GsonHandler().readDepartment(student.getDepartment());
        Lecturer lecturer = department.getHeadOfEducation();
        Request dormRequest = new Request(student, lecturer, "dorm request", RequestType.dorm);
        Request.send(dormRequest);
        result.setText("request sent");
        requestBtn.setVisible(false);
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Student student = (Student) EDU.getInstance().getCurrentUser();
        for (int id : student.getSentRequest()){
            try {
                Request request = new GsonHandler().readRequest(id);
                if (request.getRequestType() == RequestType.dorm){
                    if (request.isResponded()) {
                        result.setText(request.getResponse());
                    }else{
                        result.setText("Request is sent");
                    }
                    requestBtn.setVisible(false);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }


}
