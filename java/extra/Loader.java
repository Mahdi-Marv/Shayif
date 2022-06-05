package extra;

import Model.Department;
import Model.Request;

import java.io.File;

public class Loader {


    public static void load(){
        File dir = new File("src\\main\\resources\\model\\departments");
        File[] directoryListing = dir.listFiles();
        assert directoryListing != null;
        Department.setID(directoryListing.length+1);

        dir = new File("src\\main\\resources\\model\\requests");
        directoryListing = dir.listFiles();
        assert directoryListing != null;
        Request.setID(directoryListing.length+1);


    }



}
