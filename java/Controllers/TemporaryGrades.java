package Controllers;

import Model.*;
import extra.GsonHandler;
import extra.Saver;
import extra.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import website.EDU;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class TemporaryGrades implements Initializable {

    @FXML
    Button exitBtn;

    @FXML
    TextField lecturerResponse;

    @FXML
    GridPane courseGrid;
    @FXML
    AnchorPane anchorField;
    @FXML
    AnchorPane anchorField1;
    @FXML
    AnchorPane anchorField2;
    @FXML
    AnchorPane anchorField3;
    @FXML
    AnchorPane anchorField4;


    @FXML
    TextField courseName;
    @FXML
    TextField courseName1;
    @FXML
    TextField courseName2;
    @FXML
    TextField courseName3;
    @FXML
    TextField courseName4;

    @FXML
    TextField grade;
    @FXML
    TextField grade1;
    @FXML
    TextField grade2;
    @FXML
    TextField grade3;
    @FXML
    TextField grade4;

    @FXML
    TextField objection;
    @FXML
    TextField objection1;
    @FXML
    TextField objection2;
    @FXML
    TextField objection3;
    @FXML
    TextField objection4;

    @FXML
    Button sendBtn;
    @FXML
    Button sendBtn1;
    @FXML
    Button sendBtn2;
    @FXML
    Button sendBtn3;
    @FXML
    Button sendBtn4;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GsonHandler gsonHandler = new GsonHandler();
        Student student = (Student) EDU.getInstance().getCurrentUser();
        LinkedList<Integer> courseList = student.getCoursesEnrolled();
        if (student.getCoursesEnrolled().size() == 0) {
            courseGrid.setVisible(false);
        } else if (courseList.size() == 1) {
            anchorField1.setVisible(false);
            anchorField2.setVisible(false);
            anchorField3.setVisible(false);
            anchorField4.setVisible(false);
            try {
                Course course = gsonHandler.readCourse(courseList.get(0));
                courseName.setText(course.getName() + " " + course.getCode());
                grade.setText(String.valueOf(course.getTemporaryGrades().get(student.getStudentNumber())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (courseList.size() == 2) {
            anchorField2.setVisible(false);
            anchorField3.setVisible(false);
            anchorField4.setVisible(false);
            try {
                Course course = gsonHandler.readCourse(courseList.get(0));
                Course course1 = gsonHandler.readCourse(courseList.get(1));
                courseName.setText(course.getName() + " " + course.getCode());
                grade.setText(String.valueOf(course.getTemporaryGrades().get(student.getStudentNumber())));
                courseName1.setText(course1.getName() + " " + course1.getCode());
                grade1.setText(String.valueOf(course1.getTemporaryGrades().get(student.getStudentNumber())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (courseList.size() == 3) {
            anchorField3.setVisible(false);
            anchorField4.setVisible(false);
            try {
                Course course = gsonHandler.readCourse(courseList.get(0));
                Course course1 = gsonHandler.readCourse(courseList.get(1));
                Course course2 = gsonHandler.readCourse(courseList.get(2));
                courseName.setText(course.getName() + " " + course.getCode());
                grade.setText(String.valueOf(course.getTemporaryGrades().get(student.getStudentNumber())));
                courseName1.setText(course1.getName() + " " + course1.getCode());
                grade1.setText(String.valueOf(course1.getTemporaryGrades().get(student.getStudentNumber())));
                courseName2.setText(course2.getName() + " " + course2.getCode());
                grade2.setText(String.valueOf(course2.getTemporaryGrades().get(student.getStudentNumber())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (courseList.size() == 4) {
            anchorField4.setVisible(false);
            try {
                Course course = gsonHandler.readCourse(courseList.get(0));
                Course course1 = gsonHandler.readCourse(courseList.get(1));
                Course course2 = gsonHandler.readCourse(courseList.get(2));
                Course course3 = gsonHandler.readCourse(courseList.get(3));
                courseName.setText(course.getName() + " " + course.getCode());
                grade.setText(String.valueOf(course.getTemporaryGrades().get(student.getStudentNumber())));
                courseName1.setText(course1.getName() + " " + course1.getCode());
                grade1.setText(String.valueOf(course1.getTemporaryGrades().get(student.getStudentNumber())));
                courseName2.setText(course2.getName() + " " + course2.getCode());
                grade2.setText(String.valueOf(course2.getTemporaryGrades().get(student.getStudentNumber())));
                courseName3.setText(course3.getName() + " " + course3.getCode());
                grade3.setText(String.valueOf(course3.getTemporaryGrades().get(student.getStudentNumber())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (courseList.size() == 5) {
            try {
                Course course = gsonHandler.readCourse(courseList.get(0));
                Course course1 = gsonHandler.readCourse(courseList.get(1));
                Course course2 = gsonHandler.readCourse(courseList.get(2));
                Course course3 = gsonHandler.readCourse(courseList.get(3));
                Course course4 = gsonHandler.readCourse(courseList.get(4));
                courseName.setText(course.getName() + " " + course.getCode());
                grade.setText(String.valueOf(course.getTemporaryGrades().get(student.getStudentNumber())));
                courseName1.setText(course1.getName() + " " + course1.getCode());
                grade1.setText(String.valueOf(course1.getTemporaryGrades().get(student.getStudentNumber())));
                courseName2.setText(course2.getName() + " " + course2.getCode());
                grade2.setText(String.valueOf(course2.getTemporaryGrades().get(student.getStudentNumber())));
                courseName3.setText(course3.getName() + " " + course3.getCode());
                grade3.setText(String.valueOf(course3.getTemporaryGrades().get(student.getStudentNumber())));
                courseName4.setText(course4.getName() + " " + course4.getCode());
                grade4.setText(String.valueOf(course4.getTemporaryGrades().get(student.getStudentNumber())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        for (int id : student.getSentRequest()) {
            try {
                Request request = gsonHandler.readRequest(id);
                if (request.getRequestType() == RequestType.objection && request.isResponded()) {
                    lecturerResponse.setText(lecturerResponse.getText() + "\n" + request.getResponse());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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

    public void sendObjection(ActionEvent actionEvent) throws FileNotFoundException {
        String[] courseNameAndCode = courseName.getText().split(" ");
        int code = Integer.parseInt(courseNameAndCode[1]);
        Course course = new GsonHandler().readCourse(code);
        Lecturer lecturer = new GsonHandler().readLecturer(course.getLecturerNationalCode());
        Request request = new Request((Student) EDU.getInstance().getCurrentUser(), lecturer, objection.getText(), RequestType.objection);
        Request.send(request);
    }

    public void sendObjection2(ActionEvent actionEvent) throws FileNotFoundException {
        String[] courseNameAndCode = courseName2.getText().split(" ");
        int code = Integer.parseInt(courseNameAndCode[1]);
        Course course = new GsonHandler().readCourse(code);
        Lecturer lecturer = new GsonHandler().readLecturer(course.getLecturerNationalCode());
        Request request = new Request((Student) EDU.getInstance().getCurrentUser(), lecturer, objection2.getText(), RequestType.objection);
        Request.send(request);
    }

    public void sendObjection3(ActionEvent actionEvent) throws FileNotFoundException {
        String[] courseNameAndCode = courseName3.getText().split(" ");
        int code = Integer.parseInt(courseNameAndCode[1]);
        Course course = new GsonHandler().readCourse(code);
        Lecturer lecturer = new GsonHandler().readLecturer(course.getLecturerNationalCode());
        Request request = new Request((Student) EDU.getInstance().getCurrentUser(), lecturer, objection3.getText(), RequestType.objection);
        Request.send(request);
    }

    public void sendObjection4(ActionEvent actionEvent) throws FileNotFoundException {
        String[] courseNameAndCode = courseName4.getText().split(" ");
        int code = Integer.parseInt(courseNameAndCode[1]);
        Course course = new GsonHandler().readCourse(code);
        Lecturer lecturer = new GsonHandler().readLecturer(course.getLecturerNationalCode());
        Request request = new Request((Student) EDU.getInstance().getCurrentUser(), lecturer, objection4.getText(), RequestType.objection);
        Request.send(request);
    }

    public void sendObjection1(ActionEvent actionEvent) throws FileNotFoundException {
        String[] courseNameAndCode = courseName1.getText().split(" ");
        int code = Integer.parseInt(courseNameAndCode[1]);
        Course course = new GsonHandler().readCourse(code);
        Lecturer lecturer = new GsonHandler().readLecturer(course.getLecturerNationalCode());
        Request request = new Request((Student) EDU.getInstance().getCurrentUser(), lecturer, objection1.getText(), RequestType.objection);
        Request.send(request);
    }
}
