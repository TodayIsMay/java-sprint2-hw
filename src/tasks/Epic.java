package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private String epicDescription;
    private ArrayList<Subtask> subtasks;

    public Epic(String name, String epicDescription, int ID, Status status) {
        super(name, ID, status);
        this.epicDescription = epicDescription;
        this.subtasks = new ArrayList<>();
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setEpicDescription(String epicDescription) {
        this.epicDescription = epicDescription;
    }

    public String getEpicDescription() {
        return epicDescription;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public void addSub(Subtask sub) {
        subtasks.add(sub);
    }

    public Status getStatus(ArrayList<Epic> epics, ArrayList<Subtask> subtasks) {
        Status status;
        ArrayList<Subtask> tracking = new ArrayList<>();
        ArrayList<Subtask> tracking1 = new ArrayList<>();
        if (epics.isEmpty()) {
            status = Status.NEW;
        }
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus().equals(Status.DONE) || subtask.getStatus().equals(Status.IN_PROGRESS)) {
                tracking.add(subtask);
            }
        }
        if (tracking.size() == 0) {
            status = Status.NEW;
        } else {
            for (Subtask subtask : tracking) {
                if (subtask.getStatus().equals(Status.DONE)) {
                    tracking1.add(subtask);
                }
            }
            if (tracking1.size() == subtasks.size()) {
                status = Status.DONE;
            } else {
                status = Status.IN_PROGRESS;
            }
        }
        tracking1.clear();
        tracking.clear();
        return status;
    }
}