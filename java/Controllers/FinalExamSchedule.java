package Controllers;

import Model.Course;
import Model.Lecturer;
import Model.Student;
import extra.GsonHandler;
import extra.Saver;
import extra.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.commons.io.FilenameUtils;
import website.EDU;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class FinalExamSchedule implements Initializable {
    @FXML
    public Button exitBtn;
    @FXML
    public TextArea finalExamSchedule;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Course[] courses = new Course[10000];
        try {
            if (EDU.getInstance().getCurrentUser() instanceof Student) {
                Student student = (Student) EDU.getInstance().getCurrentUser();

                for (int id : student.getCoursesEnrolled()) {
                    Course course = new GsonHandler().readCourse(id);
                    courses[course.getFinalExamTime().getDayOfMonth()] = course;
                }

                for (Course course : courses) {
                    if (course != null) {
                        finalExamSchedule.setText(finalExamSchedule.getText() + "course name: " + course.getName() + ", exam day of month: " + course.getFinalExamTime().getDayOfMonth()
                                + ", exam start hour: " +
                                course.getFinalExamTime().getHour() + "\n");
                    }
                }
            }else{
                Lecturer lecturer = (Lecturer) EDU.getInstance().getCurrentUser();
                File dir = new File("src\\main\\resources\\model\\courses");
                File[] directoryListing = dir.listFiles();

                assert directoryListing != null;
                for (File child : directoryListing){
                    String fileNameWithOutExt = FilenameUtils.removeExtension(child.getName());
                    Course course = new GsonHandler().readCourse(Integer.parseInt(fileNameWithOutExt));
                    if (course.getLecturerNationalCode().equals(lecturer.getNationalCode()))
                        courses[course.getFinalExamTime().getDayOfMonth()] = course;

                }
                for (Course course : courses) {
                    if (course != null) {
                        finalExamSchedule.setText(finalExamSchedule.getText() + "course name: " + course.getName() + ", exam day of month: " + course.getFinalExamTime().getDayOfMonth()
                                + ", exam start hour: " +
                                course.getFinalExamTime().getHour() + "\n");
                    }
                }
            }
        } catch (FileNotFoundException e) {
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
}
