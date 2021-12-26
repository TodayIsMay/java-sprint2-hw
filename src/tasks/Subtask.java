package tasks;

public class Subtask extends Task{
    private int belonging;

    public Subtask(int belonging, String name, int ID, String status) {
        super(name, ID, status);
        this.belonging = belonging;
    }

    public int getBelonging() {
        return belonging;
    }
}