package Controllers;

import Model.*;
import extra.GsonHandler;
import extra.Saver;
import extra.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apache.commons.io.FilenameUtils;
import website.EDU;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class TosieNameRequest implements Initializable {

    @FXML
    Button exitBtn;
    @FXML
    Button requestBtn;
    @FXML
    TextField result;
    @FXML
    ChoiceBox<String> lecturerPicker;


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

    public void sendRequest(ActionEvent actionEvent) throws FileNotFoundException {
        String[] choice = lecturerPicker.getValue().split(" ");
        String code = choice[1];
        Student student = (Student) EDU.getInstance().getCurrentUser();
        Lecturer lecturer = new GsonHandler().readLecturer(code);
        Request request = new Request(student, lecturer, "tosieh name", RequestType.education);
        Request.send(request);
        result.setText("Request Sent");
        requestBtn.setVisible(false);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LinkedList<String> lecturers = new LinkedList<>();

        File dir = new File("src\\main\\resources\\model\\lecturers");
        File[] directoryListing = dir.listFiles();

        assert directoryListing != null;
        for (File child : directoryListing) {
            GsonHandler gsonHandler = new GsonHandler();
            try {
                String fileNameWithOutExt = FilenameUtils.removeExtension(child.getName());
                Lecturer lecturer = gsonHandler.readLecturer(fileNameWithOutExt);
                lecturers.add(lecturer.getName() + " " + lecturer.getNationalCode());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        lecturerPicker.getItems().addAll(lecturers);
        Student student = (Student) EDU.getInstance().getCurrentUser();
        for (int id : student.getSentRequest()){
            try {
                Request request = new GsonHandler().readRequest(id);
                if (request.getRequestType() == RequestType.education){
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
