package tasks;

public class Subtask extends Task {
    private int belonging;

    public Subtask(int belonging, String name, int Id, Status status) {
        super(name, Id, status);
        this.belonging = belonging;
    }

    public int getBelonging() {
        return belonging;
    }
}