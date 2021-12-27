package main;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;

public class Manager {
    private ArrayList<Task> tasks;
    private ArrayList<Epic> epics;

    public Manager() {
        tasks = new ArrayList<>();
        epics = new ArrayList<>();
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
            if (epic.getID() == subtask.getBelonging()) {
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

    public ArrayList<Subtask> showSubtasksFromEpic(int ID) {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (Epic epic : epics) {
            if (epic.getID() == ID) {
                subtasks = epic.getSubtasks();
            }
        }
        return subtasks;
    }

    public Epic getEpicByID(int ID) {
        Epic target = null;
        for (Epic epic : epics) {
            if (epic.getID() == ID) {
                target = epic;
            }
        }
        return target;
    }

    public Subtask getSubtaskByID(int ID) {
        Subtask target = null;
        for (Epic epic : epics) {
            ArrayList<Subtask> list = epic.getSubtasks();
            for (Subtask subtask : list) {
                if (subtask.getID() == ID) ;
                target = subtask;
            }
        }
        return target;
    }

    public Task getTaskByID(int ID) {
        Task target = null;
        for (Task task : tasks) {
            if (task.getID() == ID) {
                target = task;
            }
        }
        return target;
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