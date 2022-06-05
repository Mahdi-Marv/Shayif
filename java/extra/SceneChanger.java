package extra;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import website.EDU;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.MINUTES;

public class SceneChanger {
    static Logger logger = LogManager.getLogger(SceneChanger.class);


    public static void changeScene(String fxmlName, Button button)  {
        if (!timeExceeded()) {
            Parent root;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(SceneChanger.class.getClassLoader().getResource("fxmls\\" + fxmlName + ".fxml")));
                Stage stage = (Stage) button.getScene().getWindow();
                logger.info("changed scene to " + fxmlName);
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(SceneChanger.class.getClassLoader().getResource("fxmls\\ChangePassword.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) button.getScene().getWindow();
            assert root != null;
            logger.info("changed scene to ChangePassword");
            stage.setScene(new Scene(root));

        }
    }
    private static boolean timeExceeded(){
        LocalTime localTime = EDU.getInstance().getCurrentUser().getOnlineTime();
        LocalTime now = LocalTime.now();
        if (localTime == null)
            return false;
        return localTime.until(now, MINUTES)>=180;
    }
}
