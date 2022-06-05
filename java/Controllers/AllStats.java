package Controllers;

import Model.Course;
import Model.Student;
import com.sun.javafx.image.impl.IntArgb;
import extra.GsonHandler;
import extra.Saver;
import extra.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.commons.io.FilenameUtils;
import website.EDU;

import java.io.File;
import java.io.FileNotFoundException;

public class AllStats {

    @FXML
    TextField errorMessage;
    @FXML
    Button exitBtn;
    @FXML
    TextField courseCodeText;;
    @FXML
    TextArea statText;

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


    public void showResult(ActionEvent actionEvent) {
        try {
            String codeString = courseCodeText.getText();

            if (codeString.equals("")){
                errorMessage.setText("Enter code");
                return;
            }
            if (!courseExist(codeString)){
                errorMessage.setText("course doesn't exist");
            }
            Course course = new GsonHandler().readCourse(Integer.parseInt(codeString));
            displayCourse(course);

        }catch (NumberFormatException | FileNotFoundException e){
            errorMessage.setText("Invalid Input");
        }
    }
    private boolean courseExist(String code){
        File dir = new File("src\\main\\resources\\model\\courses");
        File[] directoryListing = dir.listFiles();

        assert directoryListing != null;
        for (File child : directoryListing){
            String fileNameWithOutExt = FilenameUtils.removeExtension(child.getName());
            if (fileNameWithOutExt.equals(code))
                return true;
        }

        return false;
    }

    private void displayCourse(Course course) throws FileNotFoundException {
        StringBuilder text = new StringBuilder();
        if (course.getTemporaryGrades().size() == 0){
            text = new StringBuilder("no temporary grades for this course\n");
        }else{
            text = new StringBuilder("temporary grades for this course: \n");
            for (String nationalCode: course.getStudentsIds()){
                Student student = new GsonHandler().readStudent(nationalCode);
                text.append(student.getName()).append(": ").append(course.getTemporaryGrades().get(student.getStudentNumber() + "\n"));
            }
        }
        if (course.getFinalGrades().size() == 0){
            text.append("no final grades has been set for this course\n");
        }else{
            text.append("final grades for this course: \n");
            for (String nationalCode: course.getStudentsIds()){
                Student student = new GsonHandler().readStudent(nationalCode);
                text.append(student.getName()).append(": ").append(course.getTemporaryGrades().get(student.getStudentNumber() + "\n"));
            }
        }
        text.append("number of passed students: ").append(course.passedStudentNumber()).append("\n");
        text.append("number of failed students: ").append(course.failedStudentNumber()).append("\n");
        text.append("course average grade: ").append(course.average()).append("\n");
        text.append("course passed students average grades: ").append(course.passedAverage()).append("\n");
        statText.setText(text.toString());
    }
}
