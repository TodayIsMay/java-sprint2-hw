package tasks;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private String description;
    private List<Subtask> subtasks;

    public Epic(String name, String description, int id, Status status) {
        super(name, id, status);
        this.description = description;
        this.subtasks = new ArrayList<>();
    }

    public ArrayList<Subtask> getSubtasks() {
        ArrayList<Subtask> clone = new ArrayList<>(subtasks);
        return clone;
    }

    public void addSub(Subtask sub) {
        subtasks.add(sub);
    }

    public void deleteSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    @Override
    public void setStatus(Status status) {
        System.out.println("Нельзя изменить статус эпика.");
    }

    public void update(Epic epic) {
        setDescription(epic.getDescription());
        this.setName(epic.getName());
    }

    @Override
    public Status getStatus() {
        Status status;
        boolean isDoneOrInProgress = false;
        ArrayList<Subtask> checkedSubtasks = new ArrayList<>();
        if (subtasks.isEmpty()) {
            status = Status.NEW;
            return status;
        }
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus().equals(Status.DONE) || subtask.getStatus().equals(Status.IN_PROGRESS)) {
                isDoneOrInProgress = true;
                break;
            }
        }
        if (!isDoneOrInProgress) {
            status = Status.NEW;
        } else {
            for (Subtask subtask : subtasks) {
                if (subtask.getStatus().equals(Status.DONE)) {
                    checkedSubtasks.add(subtask);
                }
            }
            if (checkedSubtasks.size() == subtasks.size()) {
                status = Status.DONE;
            } else {
                status = Status.IN_PROGRESS;
            }
        }
        return status;
    }

    @Override
    public String toString() {
        return this.getId() + "," + Type.EPIC + "," +
                this.getName() + "," + this.getStatus() + "," + this.getDescription() + "\n";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}