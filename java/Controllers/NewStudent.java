package Controllers;

import Model.Course;
import Model.Lecturer;
import Model.Student;
import Model.StudentType;
import extra.GsonHandler;
import extra.Saver;
import extra.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.commons.io.FilenameUtils;
import website.EDU;

import java.io.File;
import java.io.FileNotFoundException;

public class NewStudent {
    @FXML
    TextField nameText;
    @FXML
    TextField phoneNumberText;
    @FXML
    TextField typeText;
    @FXML
    TextField nationalCodeText;
    @FXML
    TextField studentNumberText;
    @FXML
    TextField emailText;
    @FXML
    TextField supervisorNationalCodeText;
    @FXML
    TextField entryYearText;
    @FXML
    TextField errorMessage;
    @FXML
    Button exitBtn;


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

    public void register(ActionEvent actionEvent) {
        try {
            Lecturer onlineLecturer = (Lecturer) EDU.getInstance().getCurrentUser();
            String name = nameText.getText();
            String nationalCode = nationalCodeText.getText();
            String phoneNumber = phoneNumberText.getText();
            String type = typeText.getText();
            String studentNumber = studentNumberText.getText();
            String email = emailText.getText();
            String supervisorNationalCode = supervisorNationalCodeText.getText();
            int entryYear = Integer.parseInt(entryYearText.getText());

            if (name.equals("") || nationalCode.equals("") || phoneNumber.equals("") || type.equals("") ||
            studentNumber.equals("") || email.equals("") || supervisorNationalCode.equals("")) {
                errorMessage.setText("Invalid Input");
                return;
            }
            if (studentExist(nationalCode)){
                errorMessage.setText("User Already exists");
                return;
            }
            StudentType studentType = null;
            switch (type){
                case "bachelor":
                    studentType = StudentType.bachelor;
                    break;
                case "master":
                    studentType = StudentType.master;
                    break;
                case "phd":
                    studentType = StudentType.phd;
                    break;
                default:
                    errorMessage.setText("Invalid input");
                    return;
            }
            if (!lecturerExist(supervisorNationalCode)){
                errorMessage.setText("lecturer doesn't exist");
                return;
            }

            new Student(name, nationalCode, onlineLecturer.getDepartment(), phoneNumber,
                    email, new GsonHandler().readLecturer(supervisorNationalCode), studentNumber,
                    studentType, entryYear);
            errorMessage.setText("Student created");






        }catch (NumberFormatException e){
            errorMessage.setText("Invalid Input");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
    private boolean studentExist(String nationalCode) throws FileNotFoundException {
        File dir = new File("src\\main\\resources\\model\\students");
        File[] directoryListing = dir.listFiles();

        assert directoryListing != null;
        for (File child: directoryListing){
            String fileNameWithOutExt = FilenameUtils.removeExtension(child.getName());
            if (fileNameWithOutExt.equals(nationalCode)){
                Student student = new GsonHandler().readStudent(nationalCode);
                if (!student.isDeleted() && student.getDepartment() == EDU.getInstance().getCurrentUser().getDepartment())
                    return true;
            }
        }
        return false;
    }

    private boolean lecturerExist(String nationalCode) throws FileNotFoundException {
        File dir = new File("src\\main\\resources\\model\\lecturers");
        File[] directoryListing = dir.listFiles();

        assert directoryListing != null;
        for (File child: directoryListing){
            String fileNameWithOutExt = FilenameUtils.removeExtension(child.getName());
            if (fileNameWithOutExt.equals(nationalCode)){
                Lecturer lecturer = new GsonHandler().readLecturer(nationalCode);
                if (!lecturer.isDeleted() && lecturer.getDepartment() == EDU.getInstance().getCurrentUser().getDepartment())
                    return true;
            }
        }
        return false;
    }
}
