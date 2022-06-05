package Controllers;

import Model.Department;
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
import javafx.scene.control.TextField;
import website.EDU;

import java.io.FileNotFoundException;

import java.net.URL;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

public class Minor implements Initializable {
    @FXML
    public Button exitBtn;
    @FXML
    public TextField result;
    @FXML
    public TextField departmentCode;
    @FXML
    public TextField errorMessage;
    @FXML
    public Button sendBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      try {
          Student student = (Student) EDU.getInstance().getCurrentUser();
          for (int id : student.getSentRequest()){
              Request request = new GsonHandler().readRequest(id);
              if (request.getRequestType() == RequestType.minor && !request.isResponded()){
                  result.setText("Request Sent");
                  sendBtn.setVisible(false);
                  return;
              }
          }
          int count = 0;
          for (int id : student.getRespondedRequest()){
              Request request = new GsonHandler().readRequest(id);
              if (request.getRequestType() == RequestType.minor) {
                  if (request.getResponse().equals("Accepted")) {

                      count++;
                  } else if (request.getResponse().equals("Rejected")){
                      result.setText("Rejected");
                      return;
                  }
              }
              if (count == 2){
                  result.setText("Accepted");
                  sendBtn.setVisible(false);
              }


          }
      }catch (FileNotFoundException e) {
          e.printStackTrace();
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

    public void sendRequest(ActionEvent actionEvent) {
        Student student = (Student) EDU.getInstance().getCurrentUser();
        if (student.getGpa()<=3){
            errorMessage.setText("Your gpa is too low");
            return;
        }
        if (departmentCode.getText().equals("")){
            errorMessage.setText("enter a value");
        }
        try {
            int departmentCodeInt = Integer.parseInt(departmentCode.getText());
            Department department = new GsonHandler().readDepartment(student.getDepartment());
            Department department1 = new GsonHandler().readDepartment(departmentCodeInt);
            Request request = new Request(student, department.getHeadOfEducation(), "minor", RequestType.minor);
            Request request1 = new Request(student, department1.getHeadOfEducation(), "minor", RequestType.minor);
            Request.send(request);
            Request.send(request1);
            result.setText("Request Sent");
            sendBtn.setVisible(false);
        }catch (InputMismatchException e){
            errorMessage.setText("Invalid Input");
        } catch (FileNotFoundException e) {
            errorMessage.setText("File not found");

        }
    }
}
