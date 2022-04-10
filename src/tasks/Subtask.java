package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Subtask extends Task {
    private int belonging;

    public Subtask(String name, int belonging, int id, Status status, Duration duration, LocalDateTime startTime) {
        super(name, id, status, duration, startTime);
        this.belonging = belonging;
    }

    public void update(Subtask subtask) {
        this.setName(subtask.getName());
        this.setStatus(subtask.getStatus());
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return this.getId() + "," + Type.SUBTASK + "," +
                this.getName() + "," + this.getStatus() + "," + this.getBelonging() + "," + this.getDuration() + "," +
                this.getStartTime().format(formatter) + "\n";
    }

    public int getBelonging() {
        return belonging;
    }

    public void setBelonging(int belonging) {
        this.belonging = belonging;
    }
}