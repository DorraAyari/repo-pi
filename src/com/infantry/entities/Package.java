package entities;

public class Package {
    private int id;
    private int userId;
    private String choices;

    public Package(int id, int userId, String choices) {
        this.id = id;
        this.userId = userId;
        this.choices = choices;
    }
    public Package(String choices){
        this.choices=choices;
    }
    
    
      public Package() {
     

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

    public String getChoices() {
        return choices;
    }

    public void setChoices(String choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "Package{" + "id=" + id + ", userId=" + userId + ", choices=" + choices + '}';
    }
    
    
}