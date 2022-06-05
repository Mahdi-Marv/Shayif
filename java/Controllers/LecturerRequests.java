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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import website.EDU;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Objects;
import java.util.ResourceBundle;

public class LecturerRequests implements Initializable {
    @FXML
    Button exitBtn;
    @FXML
    VBox vBox;

    public static boolean objection = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Lecturer lecturer = (Lecturer) EDU.getInstance().getCurrentUser();
        LinkedList<Integer> req = lecturer.getRequestsNotResponded();
        GsonHandler gsonHandler = new GsonHandler();
        for (int id : req) {
            try {
                Request request = gsonHandler.readRequest(id);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(LecturerRequests.class.getClassLoader().getResource("fxmls\\Request.fxml"));
                AnchorPane pane = loader.load();
                RequestDisplay requestDisplay = loader.getController();
                requestDisplay.updateFields(request);
                if (!objection) {
                    if (request.getRequestType() != RequestType.objection) {
                        vBox.getChildren().add(pane);
                    }
                }else{
                    if (request.getRequestType() == RequestType.objection){
                        vBox.getChildren().add(pane);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public void exit(ActionEvent actionEvent) {
        objection = false;
        Saver.save();
        System.exit(0);
    }

    public void returnHome(ActionEvent actionEvent) {
        objection = false;
        if (EDU.getInstance().getCurrentUser() instanceof Student){
            SceneChanger.changeScene("StudentHomePage", exitBtn);
        }else
            SceneChanger.changeScene("LecturerHomePage", exitBtn);
    }
}
