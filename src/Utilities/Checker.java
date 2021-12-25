package Utilities;

import Tasks.Epic;
import Tasks.Subtask;

import java.util.ArrayList;
import java.util.HashMap;

public class Checker {
    HashMap<Epic, ArrayList<Subtask>> map;
    public Checker(HashMap<Epic, ArrayList<Subtask>> map) {
        this.map = map;
    }
    public void check() {

        for (Epic epic : map.keySet()) {
            ArrayList<Subtask> tracking = new ArrayList<>();
            ArrayList<Subtask> tracking1 = new ArrayList<>();
            if (map.get(epic) == null) {
                epic.setStatus("NEW");
            }
            ArrayList<Subtask> list = map.get(epic);
            for (Subtask subtask : list) {
                if (subtask.getStatus().equals("DONE") | subtask.getStatus().equals("IN_PROGRESS")) {
                    tracking.add(subtask);
                }
            }
            if (tracking.size() == 0) {
                epic.setStatus("NEW");
            } else {
                for (Subtask subtask : tracking) {
                    if (subtask.getStatus().equals("DONE")) {
                        tracking1.add(subtask);
                    }
                }
                if (tracking1.size() == list.size()) {
                    epic.setStatus("DONE");
                } else {
                    epic.setStatus("IN_PROGRESS");
                }
            }
            tracking1.clear();
            tracking.clear();
        }
    }
}
