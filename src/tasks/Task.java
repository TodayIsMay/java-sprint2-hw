package tasks;

public class Task {
    private String name;
    private String description;
    private int id;
    private Status status;
    private int belonging;

    @Override
    public String toString() {
        String type = this.getClass().getName();
        Type eType = null;
        switch (type) {
            case "tasks.Epic":
                eType = Type.EPIC;
                return id + "," + eType + "," +
                        name + "," + this.getStatus() + "," + description + "\n";
            case "tasks.Subtask":
                eType = Type.SUBTASK;
                return id + "," + eType + "," +
                        name + "," + status + "," + belonging + "\n";
            case "tasks.Task":
                eType = Type.TASK;
                break;
        }
        return id + "," + eType + "," +
                name + "," + status + "\n";
    }
    public Task fromString(String value) {
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

    public Task(String name, int id, Status status) {
        this.name = name;
        this.id = id;
        this.status = status;
    }
    public Task(String name, int id, Status status, int belonging) {
        this.name = name;
        this.id = id;
        this.status = status;
        this.belonging = belonging;
    }
    public Task(String name, String description, int id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }
    public Task(){}

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

    public int getBelonging() {
        return belonging;
    }

    public void setBelonging(int belonging) {
        this.belonging = belonging;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}