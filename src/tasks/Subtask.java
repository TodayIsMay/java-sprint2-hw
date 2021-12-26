package tasks;

public class Subtask extends Task{
    String belonging;

    public Subtask(String belonging, String name, int ID, String status) {
        super(name, ID, status);
        this.belonging = belonging;
    }

    public String getBelonging() {
        return belonging;
    }
}