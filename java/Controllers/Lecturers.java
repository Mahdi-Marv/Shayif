package Controllers;

import Model.Department;
import Model.Lecturer;
import Model.LecturerType;
import Model.Student;
import extra.GsonHandler;
import extra.Saver;
import extra.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.commons.io.FilenameUtils;
import website.EDU;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Lecturers implements Initializable {
    @FXML
    TextField lecturerNationalCodeText;
    @FXML
    TextField errorMessage;
    @FXML
    Button addBtn;
    @FXML
    Button editBtn;
    @FXML
    Button deleteBtn;
    @FXML
    ChoiceBox<String> departmentPicker;
    @FXML
    ChoiceBox<String> rankPicker;
    @FXML
    ChoiceBox<String> typePicker;
    @FXML
    TextArea lecturerList;
    @FXML
    Button exitBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (EDU.getInstance().getCurrentUser() instanceof Student)
            hideFields();
        if (EDU.getInstance().getCurrentUser() instanceof Lecturer){
            Lecturer lecturer = (Lecturer) EDU.getInstance().getCurrentUser();
            if (lecturer.getLecturerType() != LecturerType.headOfDepartment)
                hideFields();
        }



        LinkedList<String> departments = new LinkedList<>();

        File dir = new File("src\\main\\resources\\model\\departments");
        File[] directoryListing = dir.listFiles();

        assert directoryListing != null;
        for (File child : directoryListing) {
            GsonHandler gsonHandler = new GsonHandler();
            try {
                String fileNameWithOutExt = FilenameUtils.removeExtension(child.getName());
                Department department = gsonHandler.readDepartment(Integer.parseInt(fileNameWithOutExt));
                departments.add(department.getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        departments.add("none");
        departmentPicker.getItems().addAll(departments);

        String[] ranks = {"assistantProfessor", "associateProfessor", "professor", "none"};
        rankPicker.getItems().addAll(ranks);

        String[] types = {"normal", "headOfDepartment", "headOfEducation", "none"};
        typePicker.getItems().addAll(types);
    }

    public void showLecturers(ActionEvent actionEvent) throws FileNotFoundException {
        lecturerList.setText("");

        String department = departmentPicker.getValue();
        String rank = rankPicker.getValue();
        String type = typePicker.getValue();

        File dir = new File("src\\main\\resources\\model\\lecturers");
        File[] directoryListing = dir.listFiles();

        assert directoryListing != null;

        LinkedList<Lecturer> finalLecturers = new LinkedList<>();
        LinkedList<Lecturer> deletedLecturers = new LinkedList<>();
        GsonHandler gsonHandler = new GsonHandler();


        for (File child : directoryListing) {
            try {
                String fileNameWithOutExt = FilenameUtils.removeExtension(child.getName());
                Lecturer lecturer = gsonHandler.readLecturer(fileNameWithOutExt);
                finalLecturers.add(lecturer);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        for (Lecturer lecturer: finalLecturers) {
            Department department1 = gsonHandler.readDepartment(lecturer.getDepartment());
            if (!department.equals("none")){
                if (!department.equals(department1.getName())){
                    deletedLecturers.add(lecturer);
                    continue;
                }
            }
            if (!rank.equals("none")){
                if (!rank.equals(lecturer.getLecturerRank().toString())){
                    deletedLecturers.add(lecturer);
                    continue;
                }
            }
            if (!type.equals("none")){
                if (!type.equals(lecturer.getLecturerType().toString())){
                    deletedLecturers.add(lecturer);
                }
            }

        }
        finalLecturers.removeAll(deletedLecturers);
        for (Lecturer lecturer : finalLecturers){
            displayLecturer(lecturer);
        }

    }

    public void displayLecturer(Lecturer lecturer) throws FileNotFoundException {
        Department department = new GsonHandler().readDepartment(lecturer.getDepartment());
        String text = "name: " + lecturer.getName() + ", type: " + lecturer.getLecturerType() + ", rank: " +
                lecturer.getLecturerRank() + ", department: " + department.getName();
        lecturerList.setText(lecturerList.getText() + "\n" + text);

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

    public void addLecturer(ActionEvent actionEvent) {
        SceneChanger.changeScene("AddLecturer", exitBtn);

    }

    public void editLecturer(ActionEvent actionEvent) {
        SceneChanger.changeScene("EditLecturer", exitBtn);

    }

    public void deleteLecturer(ActionEvent actionEvent) throws IOException {
        String nationalCode = lecturerNationalCodeText.getText();
        File dir = new File("src\\main\\resources\\model\\lecturers");
        File[] directoryListing = dir.listFiles();
        assert directoryListing != null;
        if (!findLecturer(directoryListing, nationalCode))
            errorMessage.setText("cannot find user");
        else{
            Lecturer lecturer = new GsonHandler().readLecturer(nationalCode);
            if (lecturer.isDeleted()){
                errorMessage.setText("Already deleted");
                return;
            }
            lecturer.setDeleted(true);
            errorMessage.setText("Lecturer has been deleted");
            new GsonHandler().saveLecturer(lecturer);
        }

    }

    private void hideFields(){
        addBtn.setVisible(false);
        deleteBtn.setVisible(false);
        editBtn.setVisible(false);
        errorMessage.setVisible(false);
        lecturerNationalCodeText.setVisible(false);

    }

    private boolean findLecturer(File[] directoryListing, String text){
        for (File child : directoryListing) {
                String fileNameWithOutExt = FilenameUtils.removeExtension(child.getName());
                if (fileNameWithOutExt.equals(text))
                    return true;
        }
        return false;
    }

}
