package main;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InMemoryTaskManager implements TaskManager {
    private static final int HISTORY_LENGTH = 10;
    private List<Task> tasks;
    private List<Epic> epics;
    private List<Task> history;

    public InMemoryTaskManager() {
        tasks = new ArrayList<>();
        epics = new ArrayList<>();
        history = new ArrayList<>();
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public List<Epic> getEpics() {
        return epics;
    }

    @Override
    public void addEpic(Epic epic) {
        epics.add(epic);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        for (Epic epic : epics) {
            if (epic.getId() == subtask.getBelonging()) {
                epic.addSub(subtask);
                return;
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
    public ArrayList<Subtask> showSubtasksFromEpic(int Id) {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (Epic epic : epics) {
            if (epic.getId() == Id) {
                subtasks = epic.getSubtasks();
                break;
            }
        }
        return subtasks;
    }

    @Override
    public Epic getEpicById(int Id) {
        Epic target = null;
        for (Epic epic : epics) {
            if (epic.getId() == Id) {
                target = epic;
                break;
            }
        }
        history.add(target);
        return target;
    }

    @Override
    public Subtask getSubtaskById(int Id) {
        Subtask target = null;
        for (Epic epic : epics) {
            ArrayList<Subtask> list = epic.getSubtasks();
            for (Subtask subtask : list) {
                if (subtask.getId() == Id) {
                    target = subtask;
                    break;
                }
            }
        }
        if (history.size() > HISTORY_LENGTH) {
            history.remove(0);
        }
        history.add(target);
        return target;
    }

    @Override
    public Task getTaskById(int Id) {
        Task target = null;
        for (Task task : tasks) {
            if (task.getId() == Id) {
                target = task;
                break;
            }
        }
        if (history.size() > HISTORY_LENGTH) {
            history.remove(0);
        }
        history.add(target);
        return target;
    }

    @Override
    public void updateStatus(int Id) {
        Task target = null;
        for (Epic epic : epics) {
            for (Subtask sub : epic.getSubtasks()) {
                if (sub.getId() == Id) {
                   target = sub;
                   break;
                } else {
                    for (Task task: tasks) {
                        if (task.getId() == Id) {
                            target = task;
                            break;
                        }
                    }
                }
            }
        }
        chooseStatus(target);
    }

    @Override
    public void chooseStatus(Task task) {
        Scanner newScan = new Scanner(System.in);
        System.out.println("Выберите новый статус: " + "\n" + "1 - NEW"
                + "\n" + "2 - IN_PROGRESS" + "\n" + "3 - DONE");
        int variant = newScan.nextInt();
        switch (variant) {
            case 1:
                task.setStatus(Status.NEW);
                break;
            case 2:
                task.setStatus(Status.IN_PROGRESS);
                break;
            case 3:
                task.setStatus(Status.DONE);
                break;
        }
    }

    @Override
    public void deleteAllTasks() {
        epics.clear();
        tasks.clear();
    }

    @Override
    public void deleteSubtask(int Id) {
        Subtask target = null;
        for (Epic epic : epics) {
            for (Subtask subtask : epic.getSubtasks()) {
                if (subtask.getId() == Id) {
                    target = subtask;
                    break;
                }
            }
            epic.deleteSubtask(target);
        }
    }

    @Override
    public void deleteEpic(int Id) {
        Epic target = null;
        for (Epic epic : epics) {
            if (epic.getId() == Id) {
                target = epic;
                break;
            }
        }
        epics.remove(target);
    }

    @Override
    public void deleteTask(int Id) {
        Task target = null;
        for (Task task : tasks) {
            if (task.getId() == Id) {
                target = task;
                break;
            }
        }
        tasks.remove(target);
    }

    @Override
    public List<Task> showHistory() {
        return history;
    }
}