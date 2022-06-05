package Controllers;

import Model.Course;
import Model.Student;
import com.google.gson.GsonBuilder;
import extra.GsonHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Grade {
    @FXML
    TextField errorMessage;
    @FXML
    Button enterBtn;
    @FXML
    TextField gradeText;
    @FXML
    TextField studentNameText;
    @FXML
    TextField nationalCodeText;

    Student student;
    Course course;

    public void enterGrade(ActionEvent actionEvent) {
        try {
            double grade = Double.parseDouble(gradeText.getText());
            if (grade < 0 || grade >20){
                errorMessage.setText("Invalid Input");
                return;
            }
            grade = grade*4;
            grade = Math.ceil(grade);
            grade = grade/4;
            course.getTemporaryGrades().put(student.getStudentNumber(), grade);

            new GsonHandler().saveCourse(course);
        }catch (NumberFormatException | IOException e){
            errorMessage.setText("Invalid Input");
        }

    }

    public void updateFields(){
        studentNameText.setText(student.getName());
        nationalCodeText.setText(student.getNationalCode());
        gradeText.setText(String.valueOf(course.getTemporaryGrades().get(student.getStudentNumber())));

    }
}
