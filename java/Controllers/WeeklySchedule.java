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
import java.util.LinkedList;
import java.util.ResourceBundle;

public class WeeklySchedule implements Initializable {

    @FXML
    TextArea weeklyTable;
    @FXML
    Button exitBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StringBuilder text = new StringBuilder();
        if (EDU.getInstance().getCurrentUser() instanceof Student) {
            Student student = (Student) EDU.getInstance().getCurrentUser();
            try {
                for (int id : student.getCoursesEnrolled()) {

                    Course course = new GsonHandler().readCourse(id);
                    text.append(course.getName()).append(" ").append(course.getTime().getDay()).append(" ").append(course.getTime().getHour()).append("\n");
                    text.append("\n");
                }
            } catch (NullPointerException exception) {
                weeklyTable.setText("no courses enrolled");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            Lecturer lecturer = (Lecturer) EDU.getInstance().getCurrentUser();
            LinkedList<Course> courses = new LinkedList<>();
            File dir = new File("src\\main\\resources\\model\\courses");
            File[] directoryListing = dir.listFiles();

            assert directoryListing != null;
            for (File child : directoryListing){
                String fileNameWithOutExt = FilenameUtils.removeExtension(child.getName());
                try {
                    Course course = new GsonHandler().readCourse(Integer.parseInt(fileNameWithOutExt));
                    if (course.getLecturerNationalCode().equals(lecturer.getNationalCode()))
                        courses.add(course);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (courses.size() == 0)
                text.append("No courses");
            else {
                for (Course course : courses) {
                    text.append(course.getName()).append(" ").append(course.getTime().getDay()).append(" ").append(course.getTime().getHour()).append("\n");
                    text.append("\n");
                }
            }
        }
        weeklyTable.setText(text.toString());


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
