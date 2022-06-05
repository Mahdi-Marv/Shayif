package Controllers;

import Model.EducationState;
import Model.Lecturer;
import Model.Student;
import extra.GsonHandler;
import extra.Hash;
import extra.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import website.EDU;

import java.io.IOException;
import java.util.Objects;

public class ChangePassword {
    @FXML
    public TextField oldPasswordText;
    @FXML
    public TextField newPasswordText;
    @FXML
    public TextField newPasswordText2;
    @FXML
    public Button changeBtn;
    @FXML
    public TextField errorText;

    public void change(ActionEvent actionEvent) throws IOException {
        if (oldPasswordText.getText().equals(""))
            errorText.setText("Enter password");
        else if (newPasswordText.getText().equals(""))
            errorText.setText("password should be more than 4 characters");
        else{
            if (Hash.getInstance().hash(oldPasswordText.getText()).equals(EDU.getInstance().getCurrentUser().getHashedPassword())){
            if (newPasswordText.getText().length()>=4 && newPasswordText.getText().equals(newPasswordText2.getText())){
                EDU.getInstance().getCurrentUser().setHashedPassword(Hash.getInstance().hash(newPasswordText.getText()));
                if (EDU.getInstance().getCurrentUser() instanceof Student){
                    Student student = (Student) EDU.getInstance().getCurrentUser();
                    new GsonHandler().saveStudent(student);
                }else{
                    Lecturer lecturer = (Lecturer) EDU.getInstance().getCurrentUser();
                    new GsonHandler().saveLecturer(lecturer);
                }
                goToLogin();
            }
        }else{
                errorText.setText("Incorrect Password");
            }
        }

    }
    public void goToLogin(){
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(SceneChanger.class.getClassLoader().getResource("fxmls\\ChangePassword.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) changeBtn.getScene().getWindow();
        assert root != null;
        stage.setScene(new Scene(root));
    }
}
