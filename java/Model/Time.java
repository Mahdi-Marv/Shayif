package Model;

public class Time {
    private String season;
    private String month;
    private int dayOfMonth;
    private String day;
    private int hour;
    private int year;

    public Time(String season, int year, String day, int hour) {
        this.season = season;
        this.year = year;
        this.day = day;
        this.hour = hour;
    }

    public Time(String month, int dayOfMonth, int hour) {
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.hour = hour;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }
}
