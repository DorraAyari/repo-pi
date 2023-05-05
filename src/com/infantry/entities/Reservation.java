package entities;

import java.util.Date;

public class Reservation {
    private int id;
    private int userId;
    private Date start;
    private Date finish;
    private int capacity;
    private Date day;

    public Reservation(int id, int userId, Date start, Date finish, int capacity, Date day) {
        this.id = id;
        this.userId = userId;
        this.start = start;
        this.finish = finish;
        this.capacity = capacity;
        this.day = day;
    }
      public Reservation(Date start, Date finish, int capacity, Date day) {

        this.start = start;
        this.finish = finish;
        this.capacity = capacity;
        this.day = day;
    }
          public Reservation(int id,Date start, Date finish, int capacity, Date day) {
       this.id=id;
        this.start = start;
        this.finish = finish;
        this.capacity = capacity;
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }
}