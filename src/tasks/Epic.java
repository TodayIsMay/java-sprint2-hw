package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private String epicDescription;
    private ArrayList<Subtask> subtasks;

    public Epic(String name, String epicDescription, int ID, String status) {
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

    public String getStatus(ArrayList<Epic> epics, ArrayList<Subtask> subtasks) {
        String status = "";
        ArrayList<Subtask> tracking = new ArrayList<>();
        ArrayList<Subtask> tracking1 = new ArrayList<>();
        if (epics.isEmpty()) {
            status = "NEW";
        }
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus().equals("DONE") || subtask.getStatus().equals("IN_PROGRESS")) {
                tracking.add(subtask);
            }
        }
        if (tracking.size() == 0) {
            status = "NEW";
        } else {
            for (Subtask subtask : tracking) {
                if (subtask.getStatus().equals("DONE")) {
                    tracking1.add(subtask);
                }
            }
            if (tracking1.size() == subtasks.size()) {
                status = "DONE";
            } else {
                status = "IN_PROGRESS";
            }
        }
        tracking1.clear();
        tracking.clear();
        return status;
    }
}