package extra;

import Model.Lecturer;
import Model.Student;
import website.EDU;

import java.io.IOException;
import java.time.LocalDate;

public class Saver {
    public static void save(){
        LocalDate localTime = LocalDate.now();
        EDU.getInstance().getCurrentUser().setLastOnline(localTime);
        try {
            if (EDU.getInstance().getCurrentUser() instanceof Student) {
                new GsonHandler().saveStudent((Student) EDU.getInstance().getCurrentUser());
            }else{
                new GsonHandler().saveLecturer((Lecturer) EDU.getInstance().getCurrentUser());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
