package Controllers;

import Model.*;
import extra.GsonHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import website.EDU;

import java.io.FileNotFoundException;
import java.io.IOException;

public class RequestDisplay {
    @FXML
    Button respondBtn;
    @FXML
    AnchorPane pane;
    @FXML
    TextField responseText;
    @FXML
    TextField requestType;
    @FXML
    TextField requestIdText;
    @FXML
    TextField nameAndNationalCodeText;
    @FXML
    TextField requestText;

    public void respond(ActionEvent actionEvent) throws IOException {
        GsonHandler gsonHandler = new GsonHandler();
        Lecturer onlineLecturer = (Lecturer) EDU.getInstance().getCurrentUser();
        int id = Integer.parseInt(requestIdText.getText());
        Request request = gsonHandler.readRequest(id);






        Student student = request.getSender();
        request.setResponded(true);
        request.setResponse(responseText.getText());
        student.getRespondedRequest().add(id);

        if (request.getRequestType() == RequestType.cancel && responseText.getText().equals("Accepted")){
            deleteStudent(student);
        }



        onlineLecturer.getRequestsResponded().add(id);
        onlineLecturer.getRequestsNotResponded().remove(Integer.valueOf(id));
        gsonHandler.saveLecturer(onlineLecturer);
        request.setReceiver(onlineLecturer);
        request.setSender(student);
        gsonHandler.saveRequest(request);
        gsonHandler.saveStudent(student);
        respondBtn.setVisible(false);

    }

    private void deleteStudent(Student student) {
        student.setDeleted(true);
        student.setEducationState(EducationState.droppedOut);
    }

    public void updateFields(Request request){
        requestType.setText(request.getRequestType().toString());
        requestIdText.setText(String.valueOf(request.getId()));
        requestText.setText(request.getText());
        nameAndNationalCodeText.setText(request.getSender().getName() + " " + request.getSender().getNationalCode());
    }
}
