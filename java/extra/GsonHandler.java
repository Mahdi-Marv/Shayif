package extra;

import Model.*;
import com.google.gson.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import website.EDU;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class GsonHandler {
    static Logger logger = LogManager.getLogger(GsonHandler.class);

    public void saveStudent(Student student) throws IOException {
        logger.info("saved " + student.getName() + " to files.");
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter("src\\main\\" +
                "resources\\model\\students\\" + student.getNationalCode() + ".json");
        writer.write(gson.toJson(student));
        writer.flush();
        writer.close();
    }

    public void saveLecturer(Lecturer lecturer) throws IOException {
        logger.info("saved " + lecturer.getName() + " to files.");
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter("src\\main\\resources\\model\\lecturers\\" + lecturer.getNationalCode() + ".json");
        writer.write(gson.toJson(lecturer));
        writer.flush();
        writer.close();
    }

    public void saveCourse(Course course) throws IOException {
        logger.info("saved " + course.getName() + " to files.");
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter("src\\main\\resources\\model\\courses\\" + course.getCode() + ".json");
        writer.write(gson.toJson(course));
        writer.flush();
        writer.close();
    }

    public void saveDepartment(Department department) throws IOException {
        logger.info("saved " +department.getName() + " to files.");
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        File file = new File("src\\main\\resources\\model\\departments\\" + department.getId() + ".json");
        System.out.println(file.exists());
        FileWriter writer = new FileWriter(file);
        writer.write(gson.toJson(department));
        writer.flush();
        writer.close();
    }

    public void saveRequest(Request request) throws IOException {
        logger.info("saved a request to files. from " + request.getSender().getName() + " to " + request.getReceiver().getName());
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter("src\\main\\resources\\model\\requests\\" + request.getId() + ".json");
        writer.write(gson.toJson(request));
        writer.flush();
        writer.close();
    }


    public Student readStudent(String username) throws FileNotFoundException {
        logger.info("loaded student with national code: " + username);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src\\main\\" +
                "resources\\model\\students\\" + username + ".json"));
        return gson.fromJson(bufferedReader, Student.class);
    }

    public Lecturer readLecturer(String username) throws FileNotFoundException {
        logger.info("loaded lecturer with national code: " + username);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src\\main\\" +
                "resources\\model\\lecturers\\" + username + ".json"));
        return gson.fromJson(bufferedReader, Lecturer.class);
    }

    public Course readCourse(int code) throws FileNotFoundException {
        logger.info("loaded course with code: " + code);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src\\main\\" +
                "resources\\model\\courses\\" + code + ".json"));
        return gson.fromJson(bufferedReader, Course.class);
    }

    public Department readDepartment(int id) throws FileNotFoundException {
        logger.info("loaded department with id: " + id);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src\\main\\" +
                "resources\\model\\departments\\" + id + ".json"));
        return gson.fromJson(bufferedReader, Department.class);
    }

    public Request readRequest(int id) throws FileNotFoundException {
        logger.info("loaded request with id: " + id);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src\\main\\" +
                "resources\\model\\requests\\" + id + ".json"));
        return gson.fromJson(bufferedReader, Request.class);
    }


}
