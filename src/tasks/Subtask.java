package tasks;

public class Subtask extends Task {

    public Subtask(int belonging, String name, int id, Status status) {
        super(name, id, status, belonging);
    }

    public void update(Subtask subtask) {
        this.setName(subtask.getName());
        this.setStatus(subtask.getStatus());
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }
}