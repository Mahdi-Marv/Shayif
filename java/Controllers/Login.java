package Controllers;


import Model.Lecturer;
import Model.Student;
import extra.GsonHandler;
import extra.Hash;
import extra.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import website.EDU;


import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.ResourceBundle;

public class Login implements Initializable {
    static HashMap<Image, Integer> hashMap = new HashMap<>();
    static LinkedList<Image> captchaList = new LinkedList<>();

    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    ImageView captcha;
    @FXML
    Button change;
    @FXML
    Button enterSystem;
    @FXML
    TextField captchaText;
    @FXML
    TextField time;
    @FXML
    TextField errorMessage;


    public void changeCaptcha(ActionEvent actionEvent) {
        Random random = new Random();
        Image image = captchaList.get(random.nextInt(captchaList.size()));
        captcha.setImage(image);

    }


    public void enterSystem(ActionEvent actionEvent) {
        if (!isValidCaptcha()) {
            errorMessage.setText("Invalid captcha");
            changeCaptcha(actionEvent);
            return;
        }
        String user = username.getText();
        String pass = Hash.getInstance().hash(password.getText());
        File file = new File("src\\main\\" +
                "resources\\model\\students\\" + user + ".json");

        File file2 = new File("src\\main\\" +
                "resources\\model\\lecturers\\" + user + ".json");

        GsonHandler gsonHandler = new GsonHandler();
        if (file.exists()) {

            try {
                Student student = gsonHandler.readStudent(user);
                if (student.isDeleted()) {
                    errorMessage.setText("This User Has Been Deleted");

                } else {
                    if (student.getHashedPassword().equals(pass)) {
                        EDU.getInstance().setCurrentUser(student);
                        SceneChanger.changeScene("StudentHomePage", change);
                    } else {
                        changeCaptcha(actionEvent);
                        errorMessage.setText("Wrong password");
                    }

                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (file2.exists()) {

            try {
                Lecturer lecturer = gsonHandler.readLecturer(user);
                if (lecturer.isDeleted()) {
                    errorMessage.setText("This User Has Been Deleted");
                } else {
                    EDU.getInstance().setCurrentUser(lecturer);
                    if (lecturer.getHashedPassword().equals(pass)) {
                        SceneChanger.changeScene("LecturerHomepage", change);

                    } else {
                        errorMessage.setText("Wrong password");
                        changeCaptcha(actionEvent);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            errorMessage.setText("user doesn't exist");
            changeCaptcha(actionEvent);
        }

    }

    public boolean isValidCaptcha() {
        try {
            if (Integer.parseInt(captchaText.getText()) == hashMap.get(captcha.getImage())) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Image image1 = new Image("/captcha/1499.jpg");
        Image image2 = new Image("/captcha/3649.jpg");
        Image image3 = new Image("/captcha/5314.jpg");
        Image image4 = new Image("/captcha/6519.jpg");
        Image image5 = new Image("/captcha/8511.jpg");
        Image image6 = new Image("/captcha/8525.jpg");
        Image image7 = new Image("/captcha/9044.jpg");

        hashMap.put(image1, 1499);
        hashMap.put(image2, 3649);
        hashMap.put(image3, 5314);
        hashMap.put(image4, 6519);
        hashMap.put(image5, 8511);
        hashMap.put(image6, 8525);
        hashMap.put(image7, 9044);

        captchaList.add(image1);
        captchaList.add(image2);
        captchaList.add(image3);
        captchaList.add(image4);
        captchaList.add(image5);
        captchaList.add(image6);
        captchaList.add(image7);


        changeCaptcha(new ActionEvent());


    }


}
