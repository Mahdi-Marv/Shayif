package Model;

import extra.GsonHandler;

import java.io.IOException;

public class Request {

    private Student sender;
    private Lecturer receiver;
    private final String text;
    private String response;
    private final RequestType requestType;
    private final int id;
    private static int ID = 1;
    private boolean responded;




    public Request(Student sender, Lecturer receiver, String text, RequestType requestType) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.requestType = requestType;
        id = ID;
        ID++;
        try {
            new GsonHandler().saveRequest(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void send(Request request){
        request.receiver.getRequestsNotResponded().add(request.getId());
        request.sender.getSentRequest().add(request.getId());

        GsonHandler gsonHandler = new GsonHandler();
        try {
            gsonHandler.saveStudent(request.sender);
            gsonHandler.saveLecturer(request.receiver);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public Student getSender() {
        return sender;
    }

    public Lecturer getReceiver() {
        return receiver;
    }

    public String getText() {
        return text;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public int getId() {
        return id;
    }

    public static void setID(int ID) {
        Request.ID = ID;
    }

    public boolean isResponded() {
        return responded;
    }

    public void setResponded(boolean responded) {
        this.responded = responded;
    }


    public void setSender(Student sender) {
        this.sender = sender;
    }

    public void setReceiver(Lecturer receiver) {
        this.receiver = receiver;
    }



    public static int getID() {
        return ID;
    }
}
