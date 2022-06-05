package Controllers;

import Model.Course;
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

public class EducationStats implements Initializable {
    @FXML
    Button exitBtn;
    @FXML
    TextArea courseText;
    @FXML
    TextField nationalCodeText;
    @FXML
    TextField errorMessage;
    @FXML
    Button displayBtn;

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

       if (EDU.getInstance().getCurrentUser() instanceof Student) {
           hideFields();
           Student student = (Student) EDU.getInstance().getCurrentUser();
           showStats(student);
       }

    }

    private void hideFields() {
        errorMessage.setVisible(false);
        nationalCodeText.setVisible(false);
        displayBtn.setVisible(false);
    }

    public void displayStudent(ActionEvent actionEvent) {
        if (nationalCodeText.getText().equals("")) {
            errorMessage.setText("Enter a student national code");
            return;
        }
        String nationalCode = nationalCodeText.getText();
        if (studentExists(nationalCode)){
            try {
                Student student = new GsonHandler().readStudent(nationalCode);
                showStats(student);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            errorMessage.setText("Student doesn't exist");
        }
    }
    private boolean studentExists(String nationalCode){
        File dir = new File("src\\main\\resources\\model\\students");
        File[] directoryListing = dir.listFiles();

        assert directoryListing != null;
        for (File child: directoryListing){
            String fileNameWithOutExt = FilenameUtils.removeExtension(child.getName());
            if (fileNameWithOutExt.equals(nationalCode))
                return true;
        }
        return false;

    }

    private void showStats(Student student){

        GsonHandler gsonHandler = new GsonHandler();
        StringBuilder text = new StringBuilder("Courses passed: \n");
        if (student.getCoursesPassed().size() == 0){
            text.append("no courses passed").append("\n");
        }else {
            for (int id : student.getCoursesPassed()) {
                try {
                    Course course = gsonHandler.readCourse(id);
                    text.append("name: ").append(course.getName()).append(", course credit: ").
                            append(course.getCredit()).append(", score: ").
                            append(course.getFinalGrades().get(student.getStudentNumber())).append("\n");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        text.append("Courses failed:").append("\n");
        if (student.getCoursesFailed().size() == 0){
            text.append("no courses failed").append("\n");
        }else {
            for (int id : student.getCoursesFailed()) {
                try {
                    Course course = gsonHandler.readCourse(id);
                    text.append("name: ").append(course.getName()).append(", course credit: ").
                            append(course.getCredit()).append(", score: ").
                            append(course.getFinalGrades().get(student.getStudentNumber())).append("\n");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        text.append("temporary grades: " + "\n");
        if (student.getCoursesEnrolled().size() == 0){
            text.append("no temporary grades").append("\n");
        }else {
            for (int id : student.getCoursesEnrolled()) {
                try {
                    Course course = gsonHandler.readCourse(id);
                    text.append("name: ").append(course.getName()).append(", course credit: ").
                            append(course.getCredit()).append(", score: ").
                            append(course.getTemporaryGrades().get(student.getStudentNumber())).append("\n");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        courseText.setText(text.toString() + "\n" + "gpa: " + student.getGpa());
    }
}
