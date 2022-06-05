package Controllers;

import Model.Course;
import Model.Student;
import Model.Time;
import extra.GsonHandler;
import extra.Saver;
import extra.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.commons.io.FilenameUtils;
import website.EDU;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddAndEditCourses implements Initializable {
    @FXML
    TextField nameText;
    @FXML
    TextField courseCodeText;
    @FXML
    TextField courseTimeText;
    @FXML
    TextField creditText;
    @FXML
    TextField lecturerIdText;
    @FXML
    TextField capacityText;
    @FXML
    TextField examTimeText;
    @FXML
    TextField errorMessage;
    @FXML
    Button showCourseBtn;
    @FXML
    Button addCourseBtn;
    @FXML
    Button editBtn;
    @FXML
    Button chooseEditBtn;
    @FXML
    Button chooseAddBtn;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editBtn.setVisible(false);
        showCourseBtn.setVisible(false);
        addCourseBtn.setVisible(false);

    }

    public void showCourse(ActionEvent actionEvent) throws FileNotFoundException {
        if (courseCodeText.getText().equals("")) {
            errorMessage.setText("Enter course code");
            return;
        }
        if (!courseExist(courseCodeText.getText())) {
            errorMessage.setText("course doesn't exist");
            return;
        }
        Course course = new GsonHandler().readCourse(Integer.parseInt(courseCodeText.getText()));
        nameText.setText(course.getName());
        courseCodeText.setText(String.valueOf(course.getCode()));
        lecturerIdText.setText(course.getLecturerNationalCode());
        Time time = course.getTime();
        courseTimeText.setText(time.getSeason() + "/" + time.getYear() + "/" +
                time.getDay() + "/" + time.getHour());
        capacityText.setText(String.valueOf(course.getCapacity()));
        creditText.setText(String.valueOf(course.getCredit()));
        Time examTime = course.getFinalExamTime();
        examTimeText.setText(examTime.getMonth() + "/" + examTime.getDayOfMonth()
        + "/" + examTime.getHour());


    }

    public void addCourse(ActionEvent actionEvent) {
        try {
            String name = nameText.getText();
            int code = Integer.parseInt(courseCodeText.getText());
            String lecturerId = lecturerIdText.getText();
            int capacity = Integer.parseInt(capacityText.getText());
            int credit = Integer.parseInt(creditText.getText());
            String[] courseTime = courseTimeText.getText().split("/");
            String[] finalExamTime = examTimeText.getText().split("/");

            if (name.equals("") || lecturerId.equals("")) {
                errorMessage.setText("Invalid input");
                return;
            }
            if (courseExist(String.valueOf(code))){
                errorMessage.setText("code already exist");
                return;
            }

            Time time = new Time(courseTime[0], Integer.parseInt(courseTime[1]),
                    courseTime[2], Integer.parseInt(courseTime[3]));
            Time examTime = new Time(finalExamTime[0], Integer.parseInt(courseTime[1]),
                    Integer.parseInt(courseTime[2]));

            new Course(name, code, EDU.getInstance().getCurrentUser().getDepartment(),
                    lecturerId, time, capacity, credit, examTime);
            errorMessage.setText("Course Added");


        } catch (Exception e){
            errorMessage.setText("Invalid Input");
        }


    }

    public void editCourse(ActionEvent actionEvent) throws FileNotFoundException {
        try {
            GsonHandler gsonHandler = new GsonHandler();
            Course course = gsonHandler.readCourse(Integer.parseInt(courseCodeText.getText()));

            String name = nameText.getText();
            int code = Integer.parseInt(courseCodeText.getText());
            String lecturerId = lecturerIdText.getText();
            int capacity = Integer.parseInt(capacityText.getText());
            int credit = Integer.parseInt(creditText.getText());
            String[] courseTime = courseTimeText.getText().split("/");
            String[] finalExamTime = examTimeText.getText().split("/");

            if (name.equals("") || lecturerId.equals("")) {
                errorMessage.setText("Invalid input");
                return;
            }
            course.setName(name);
            course.setLecturerNationalCode(lecturerId);
            course.setCapacity(capacity);
            course.setCredit(credit);
            course.setTime(new Time(courseTime[0], Integer.parseInt(courseTime[1]),
                    courseTime[2], Integer.parseInt(courseTime[3])));
            course.setFinalExamTime(new Time(finalExamTime[0], Integer.parseInt(finalExamTime[1]),
                    Integer.parseInt(finalExamTime[2])));

            errorMessage.setText("Course has been edited");
            gsonHandler.saveCourse(course);


        }catch (Exception e){
            errorMessage.setText("Invalid Input");
        }


    }

    public void chooseEdit(ActionEvent actionEvent) {
        chooseAddBtn.setVisible(false);
        chooseEditBtn.setVisible(false);
        addCourseBtn.setVisible(false);

        editBtn.setVisible(true);
        showCourseBtn.setVisible(true);
    }

    public void chooseAdd(ActionEvent actionEvent) {
        chooseAddBtn.setVisible(false);
        chooseEditBtn.setVisible(false);
        editBtn.setVisible(false);
        showCourseBtn.setVisible(false);

        addCourseBtn.setVisible(true);
    }

    private boolean courseExist(String code) throws FileNotFoundException {
        File dir = new File("src\\main\\resources\\model\\courses");
        File[] directoryListing = dir.listFiles();

        assert directoryListing != null;
        for (File child: directoryListing){
            String fileNameWithOutExt = FilenameUtils.removeExtension(child.getName());
            if (fileNameWithOutExt.equals(code)){
                Course course = new GsonHandler().readCourse(Integer.parseInt(code));
                if (!course.isDeleted())
                    return true;
            }
        }
        return false;
    }
}
