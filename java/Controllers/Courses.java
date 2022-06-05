package Controllers;

import Model.*;
import extra.GsonHandler;
import extra.Saver;
import extra.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.apache.commons.io.FilenameUtils;
import website.EDU;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Courses implements Initializable {
    @FXML
    Button addBtn;
    @FXML
    Button deleteBtn;
    @FXML
    Button editBtn;
    @FXML
    public TextField errorMessage;
    @FXML
    TextField courseCodeText;
    @FXML
    TextArea courseList;
    @FXML
    ChoiceBox<String> departmentPicker;
    @FXML
    ChoiceBox<Integer> creditPicker;
    @FXML
    ChoiceBox<String> lecturerPicker;
    @FXML
    Button exitBtn;

    VBox box;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LinkedList<String> list = new LinkedList<>();

        File dir = new File("src\\main\\resources\\model\\departments");
        File[] directoryListing = dir.listFiles();

        assert directoryListing != null;
        for (File child : directoryListing) {
            GsonHandler gsonHandler = new GsonHandler();
            try {
                String fileNameWithOutExt = FilenameUtils.removeExtension(child.getName());
                Department department = gsonHandler.readDepartment(Integer.parseInt(fileNameWithOutExt));
                list.add(department.getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        LinkedList<Integer> list1 = new LinkedList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(4);
        list1.add(0);
        list.add("none");

        creditPicker.getItems().addAll(list1);
        departmentPicker.getItems().addAll(list);


        LinkedList<String> list2 = new LinkedList<>();
        dir = new File("src\\main\\resources\\model\\lecturers");
        directoryListing = dir.listFiles();

        assert directoryListing != null;
        for (File child : directoryListing) {
            GsonHandler gsonHandler = new GsonHandler();
            try {
                String fileNameWithOutExt = FilenameUtils.removeExtension(child.getName());
                Lecturer lecturer = gsonHandler.readLecturer(fileNameWithOutExt);
                list2.add(lecturer.getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        list2.add("none");
        lecturerPicker.getItems().addAll(list2);
        ////////////////////////////////////////////////////////////////////////////
        if (EDU.getInstance().getCurrentUser() instanceof Student)
            hideFields();
        else{
            Lecturer currentLecturer = (Lecturer) EDU.getInstance().getCurrentUser();
            if (currentLecturer.getLecturerType() != LecturerType.headOfEducation){
                hideFields();
            }
        }


        /////////////////////
//        box.getChildren().add(FXMLLoader.load())

    }

    public void displayCourse(Course course){
        String text = "Name: " + course.getName() + ", code: " + course.getCode()
        + ", department: " + course.getDepartmentId() + ", lecturer: " + course.getLecturerNationalCode()
        + ", capacity: " + course.getCapacity() + ", credits: " + course.getCredit();
        courseList.setText(courseList.getText() + "\n" + text);
    }


    public void showCourses(ActionEvent actionEvent) throws FileNotFoundException {
        courseList.setText("");

        String department = departmentPicker.getValue();
        String lecturer = lecturerPicker.getValue();
        int credit = creditPicker.getValue();


        File dir = new File("src\\main\\resources\\model\\courses");
        File[] directoryListing = dir.listFiles();

        assert directoryListing != null;
        LinkedList<Course> finalCourse = new LinkedList<>();
        LinkedList<Course> deleteCourse = new LinkedList<>();
        for (File child : directoryListing) {
            GsonHandler gsonHandler = new GsonHandler();
            try {
                String fileNameWithOutExt = FilenameUtils.removeExtension(child.getName());
                Course course = gsonHandler.readCourse(Integer.parseInt(fileNameWithOutExt));
                finalCourse.add(course);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        for (Course course: finalCourse){
            Department department1 = new GsonHandler().readDepartment(course.getDepartmentId());
            Lecturer lecturer1 = new GsonHandler().readLecturer(course.getLecturerNationalCode());
            if (creditPicker.getValue()!=0){
                if (course.getCredit() != creditPicker.getValue()){
                    deleteCourse.add(course);
                    continue;
                }
            }
            if (!lecturerPicker.getValue().equals("none")){
                if (!lecturer1.getName().equals(lecturerPicker.getValue())){
                    deleteCourse.add(course);

                    continue;
                }
            }
            if (!departmentPicker.getValue().equals("none")){
                if (!department1.getName().equals(departmentPicker.getValue()))
                    deleteCourse.add(course);
            }
        }
        finalCourse.removeAll(deleteCourse);
        for (Course course: finalCourse){
            displayCourse(course);
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

    private void hideFields(){
        addBtn.setVisible(false);
        editBtn.setVisible(false);
        deleteBtn.setVisible(false);
        courseCodeText.setVisible(false);
        errorMessage.setVisible(false);
    }

    public void addCourse(ActionEvent actionEvent) {
        SceneChanger.changeScene("AddAndEditCourses", exitBtn);
    }

    public void editCourse(ActionEvent actionEvent) {
        SceneChanger.changeScene("AddAndEditCourses", editBtn);
    }

    public void deleteCourse(ActionEvent actionEvent) {
        try {
            GsonHandler gsonHandler = new GsonHandler();
            String courseCodeString = courseCodeText.toString();
            int courseCodeInt = Integer.parseInt(courseCodeText.getText());

            if(courseExists(courseCodeString)){
                Course course = gsonHandler.readCourse(courseCodeInt);
                course.setDeleted(true);
                errorMessage.setText("Course deleted");
            }else{
                errorMessage.setText("course not found");
            }




        } catch (NumberFormatException | FileNotFoundException e){
            errorMessage.setText("Invalid Input");
        }


    }

    private boolean courseExists(String code){
        File dir = new File("src\\main\\resources\\model\\courses");
        File[] directoryListing = dir.listFiles();

        assert directoryListing != null;
        for (File child: directoryListing){
            String fileNameWithOutExt = FilenameUtils.removeExtension(child.getName());
            if (fileNameWithOutExt.equals(code))
                return true;
        }
        return false;
    }
}
