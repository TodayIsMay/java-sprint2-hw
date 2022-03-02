package tasks;

import java.util.Objects;

public class Task {
    private String name;
    private int id;
    private Status status;

    public Task(String name, int id, Status status) {
        this.name = name;
        this.id = id;
        this.status = status;
    }

    @Override
    public String toString() {
        return this.getId() + "," + Type.TASK + "," +
                this.getName() + "," + this.getStatus() + "\n";
    }

    public static Task fromString(String value) {
        Task task = null;
        Status status = null;
        String[] valueArr = value.split(",");
        switch (valueArr[3]) {
            case "NEW":
                status = Status.NEW;
                break;
            case "IN_PROGRESS":
                status = Status.IN_PROGRESS;
                break;
            case "DONE":
                status = Status.DONE;
                break;
        }
        int id = Integer.parseInt(valueArr[0]);
        String name = valueArr[2];
        if (valueArr[1].equals(Type.EPIC.toString())) {
            String description = valueArr[4];
            task = new Epic(name, description, id, status);
        } else if (valueArr[1].equals(Type.SUBTASK.toString())) {
            int belonging = Integer.parseInt(valueArr[4]);
            task = new Subtask(belonging, name, id, status);
        } else if (valueArr[1].equals(Type.TASK.toString())) {
            task = new Task(name, id, status);
        }
        return task;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void update(Task task) {
        this.setName(task.getName());
        this.setStatus(task.getStatus());
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, status);
    }
}