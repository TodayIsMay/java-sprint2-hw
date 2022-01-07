package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private String epicDescription;
    private ArrayList<Subtask> subtasks;

    public Epic(String name, String epicDescription, int Id, Status status) {
        super(name, Id, status);
        this.epicDescription = epicDescription;
        this.subtasks = new ArrayList<>();
    }

    public ArrayList<Subtask> getSubtasks() {
        return (ArrayList<Subtask>) subtasks.clone();
    }

    public void setEpicDescription(String epicDescription) {
        this.epicDescription = epicDescription;
    }

    public String getEpicDescription() {
        return epicDescription;
    }

    public void addSub(Subtask sub) {
        subtasks.add(sub);
    }

    public void deleteSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    public Status getStatus() {
        Status status;
        boolean isDoneOrInProgress = false;
        ArrayList<Subtask> checkedSubtasks = new ArrayList<>();
        if (subtasks.isEmpty()) {
            status = Status.NEW;
        }
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus().equals(Status.DONE) || subtask.getStatus().equals(Status.IN_PROGRESS)) {
                isDoneOrInProgress = true;
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
}