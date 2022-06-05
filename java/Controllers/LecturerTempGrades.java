package Controllers;

import Model.Course;
import Model.Lecturer;
import Model.LecturerType;
import Model.Student;
import extra.GsonHandler;
import extra.Saver;
import extra.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import website.EDU;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LecturerTempGrades implements Initializable {
    static Logger logger = LogManager.getLogger(LecturerTempGrades.class);
    @FXML
    Button finalizeBtn;
    @FXML
    Button statBtn;
    @FXML
    Button exitBtn;
    @FXML
    TextField courseCodeText;
    @FXML
    TextField errorMessage;
    @FXML
    VBox vBox;
    @FXML
    Button objectionBtn;

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


    public void showGrades(ActionEvent actionEvent) throws IOException {
        vBox.getChildren().clear();
        String codeString = courseCodeText.getText();
        Lecturer onlineLecturer = (Lecturer) EDU.getInstance().getCurrentUser();
        GsonHandler gsonHandler = new GsonHandler() ;
        if (codeString.equals("")){
            errorMessage.setText("Enter course code");
            return;
        }
        if (!courseExists(codeString)){
            errorMessage.setText("Course doesn't exist.");
            return;
        }else{
            Course course = gsonHandler.readCourse(Integer.parseInt(codeString));
            if (!course.getLecturerNationalCode().equals(onlineLecturer.getNationalCode())){
                errorMessage.setText("This course is not yours");
                return;
            }
            if (course.getFinalGrades().size()>0){
                errorMessage.setText("Grades have already been finalized");
                return;
            }

            for (String id: course.getStudentsIds()) {
                Student student = gsonHandler.readStudent(id);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(
                        LecturerTempGrades.class.getClassLoader()
                                .getResource("fxmls\\Grade.fxml"));
                AnchorPane pane = loader.load();
                Grade grade = loader.getController();
                grade.course = course;
                grade.student = student;
                grade.updateFields();
                vBox.getChildren().add(pane);
            }
        }
        finalizeBtn.setVisible(true);

    }

    public void finalizeGrades(ActionEvent actionEvent) throws IOException {
        Lecturer lecturer = (Lecturer) EDU.getInstance().getCurrentUser();
        GsonHandler gsonHandler = new GsonHandler();
        if (canFinalize()){
            Course course = gsonHandler.readCourse(Integer.parseInt(courseCodeText.getText()));
            for (String studentId: course.getStudentsIds()){
                Student student = gsonHandler.readStudent(studentId);
                String sdCode = student.getStudentNumber();
                double grade = course.getTemporaryGrades().get(sdCode);
                course.getFinalGrades().put(sdCode, grade);
                if (grade>=10)
                    student.getCoursesPassed().add(course.getCode());
                else
                    student.getCoursesFailed().add(course.getCode());

                student.getCoursesEnrolled().remove(Integer.valueOf(course.getCode()));

                gsonHandler.saveStudent(student);
            }
            course.getTemporaryGrades().clear();
            gsonHandler.saveCourse(course);

            logger.info(lecturer.getName() + " finalized the grades for course: " + course.getName());

        }
        else{
            errorMessage.setText("Enter grade for all students");
        }

    }

    public void seeObjections(ActionEvent actionEvent) {
        LecturerRequests.objection = true;
        SceneChanger.changeScene("LecturerRequests",exitBtn);

    }
    public void seeStats(ActionEvent actionEvent) {
        SceneChanger.changeScene("AllStats", exitBtn);
    }

    private boolean courseExists(String code) throws FileNotFoundException {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        finalizeBtn.setVisible(false);
        Lecturer onlineLecturer = (Lecturer) EDU.getInstance().getCurrentUser();
        if (onlineLecturer.getLecturerType() != LecturerType.headOfEducation) {
            statBtn.setVisible(false);
            objectionBtn.setVisible(false);
        }
    }

    private boolean canFinalize() throws FileNotFoundException {
        GsonHandler gsonHandler = new GsonHandler();
        Course course = gsonHandler.readCourse(Integer.parseInt(courseCodeText.getText()));
        for (double grade: course.getTemporaryGrades().values()){
            if (grade == -1)
                return false;
        }
        return true;
    }


    public void seeAllObjections(ActionEvent actionEvent) {
        SceneChanger.changeScene("AllObjections", exitBtn);
    }
}
