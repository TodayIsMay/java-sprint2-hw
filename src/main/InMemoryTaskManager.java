package main;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

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
    public List<ArrayList<Subtask>> showAllSubtasksList() {
        List<ArrayList<Subtask>> list = new ArrayList<>();
        for (Epic epic : epics) {
            list.add(epic.getSubtasks());
        }
        return list;
    }

    @Override
    public List<Subtask> showSubtasksFromEpic(int Id) {
        List<Subtask> subtasks = new ArrayList<>();
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
        if (history.size() > HISTORY_LENGTH) {
            history.remove(0);
        }
        return target;
    }

    @Override
    public Subtask getSubtaskById(int Id) {
        Subtask target = null;
        for (Epic epic : epics) {
            List<Subtask> list = epic.getSubtasks();
            for (Subtask subtask : list) {
                if (subtask.getId() == Id) {
                    target = subtask;
                    break;
                }
            }
        }
        history.add(target);
        if (history.size() > HISTORY_LENGTH) {
            history.remove(0);
        }
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
        history.add(target);
        if (history.size() > HISTORY_LENGTH) {
            history.remove(0);
        }
        return target;
    }

    @Override
    public void updateEpic(Epic epic, int id) {
        Epic target = null;
        for (Epic oldEpic: epics) {
            if (oldEpic.getId() == id) {
                target = oldEpic;
                break;
            }
        }
        target.update(epic);
    }

    @Override
    public void updateSubtask(Subtask subtask, int id) {
        Subtask target = null;
        for(Epic epic : epics) {
            List<Subtask> list = epic.getSubtasks();
            for(Subtask sub : list) {
                if (sub.getId() == id) {
                    target = sub;
                    break;
                }
            }
        }
        target.update(subtask);
    }

    @Override
    public void updateTask(Task task, int id) {
        Task target = null;
        for (Task oldTask : tasks) {
            if(oldTask.getId() == id) {
                target = oldTask;
                break;
            }
        }
        target.update(task);
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