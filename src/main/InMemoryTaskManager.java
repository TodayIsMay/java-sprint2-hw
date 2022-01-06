package main;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;

public class InMemoryTaskManager implements TaskManager {
    private ArrayList<Task> tasks;
    private ArrayList<Epic> epics;
    private ArrayList<Task> history;

    public InMemoryTaskManager() {
        tasks = new ArrayList<>();
        epics = new ArrayList<>();
        history = new ArrayList<>();
    }

    @Override
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return epics;
    }

    @Override
    public void addEpic(Epic epic) {
        epics.add(epic);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        for (Epic epic : epics) {
            if (epic.getID() == subtask.getBelonging()) {
                epic.addSub(subtask);
            }
        }
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public ArrayList<ArrayList<Subtask>> showAllSubtasksList() {
        ArrayList<ArrayList<Subtask>> list = new ArrayList<>();
        for (Epic epic : epics) {
            list.add(epic.getSubtasks());
        }
        return list;
    }

    @Override
    public ArrayList<Subtask> showSubtasksFromEpic(int ID) {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (Epic epic : epics) {
            if (epic.getID() == ID) {
                subtasks = epic.getSubtasks();
            }
        }
        return subtasks;
    }

    @Override
    public Epic getEpicByID(int ID) {
        Epic target = null;
        for (Epic epic : epics) {
            if (epic.getID() == ID) {
                target = epic;
            }
        }
        history.add(target);
        return target;
    }

    @Override
    public Subtask getSubtaskByID(int ID) {
        Subtask target = null;
        for (Epic epic : epics) {
            ArrayList<Subtask> list = epic.getSubtasks();
            for (Subtask subtask : list) {
                if (subtask.getID() == ID) {
                    target = subtask;
                }
            }
        }
        history.add(target);
        return target;
    }

    @Override
    public Task getTaskByID(int ID) {
        Task target = null;
        for (Task task : tasks) {
            if (task.getID() == ID) {
                target = task;
            }
        }
        history.add(target);
        return target;
    }

    @Override
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

    public ArrayList<Task> history() {
        while (history.size() > 10) {
            history.remove(0);
        }
        return history;
    }
}