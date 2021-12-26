package main;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    ArrayList<Task> tasks;
    ArrayList<Epic> epics;
    HashMap<Epic, ArrayList<Subtask>> map;

    public Manager() {
        tasks = new ArrayList<>();
        epics = new ArrayList<>();
        map = new HashMap<>();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public ArrayList<Epic> getEpics() {
        return epics;
    }

    public void addEpic(Epic epic) {
        epics.add(epic);
    }

    public void addSubtask(Subtask subtask) {
        for (Epic epic : epics) {
            if (epic.getName().equals(subtask.getBelonging())) {
                epic.addSub(subtask);
            }
        }
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public ArrayList<ArrayList<Subtask>> showAllSubtasksList() {
        ArrayList<ArrayList<Subtask>> list = new ArrayList<>();
        for (Epic epic : epics) {
            list.add(epic.getSubtasks());
        }
        return list;
    }

    public ArrayList<Subtask> showSubtasksFromEpic(String name) {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (Epic epic : epics) {
            if (epic.getName().equals(name)) {
                subtasks = epic.getSubtasks();
            }
        }
        return subtasks;
    }

    public void showAnyTaskByID(int ID) {
        for (Epic epic : epics) {
            if (epic.getID() == ID) {
                System.out.println(epic.getName());
            } else {
                ArrayList<Subtask> list = epic.getSubtasks();
                for (Subtask subtask : list) {
                    if (subtask.getID() == ID) {
                        System.out.println(subtask.getName());
                    }
                }
            }
        }
        for (Task task : tasks) {
            if (task.getID() == ID) {
                System.out.println(task.getName());
            }
        }
    }

    public void delete(int ID, int command) {
        if (command == 1) {
            epics.clear();
            tasks.clear();
        } else if (command == 2) {
            Subtask target = null;
            for (Epic epic : epics) {
                for (Subtask subtask : epic.getSubtasks()) {
                    if (subtask.getID() == ID) {
                        target = subtask;
                    }
                }
                epic.getSubtasks().remove(target);
            }
        } else if (command == 3) {
            Epic target = null;
            for (Epic epic : epics) {
                if (epic.getID() == ID) {
                    target = epic;
                }
            }
            epics.remove(target);
        } else if (command == 4) {
            Task target = null;
            for (Task task : tasks) {
                if (task.getID() == ID) {
                    target = task;
                }
            }
            tasks.remove(target);
        }
    }
}