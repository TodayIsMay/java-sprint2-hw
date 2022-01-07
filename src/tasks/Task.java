package tasks;

public class Task {
    private String name;
    private int Id;
    private Status status;

    public Task(String name, int Id, Status status) {
        this.name = name;
        this.Id = Id;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int ID) {
        this.Id = Id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}