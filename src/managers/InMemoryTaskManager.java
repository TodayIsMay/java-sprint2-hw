package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import utilities.HistoryManager;
import utilities.Managers;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
    private List<Task> tasks;
    private List<Epic> epics;

    public InMemoryTaskManager() {
        tasks = new ArrayList<>();
        epics = new ArrayList<>();
    }

    static public InMemoryTaskManager setLists(List<Task> tasks, List<Epic> epics){
        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.setTasks(tasks);
        manager.setEpics(epics);
        return manager;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setEpics(List<Epic> epics) {
        this.epics = epics;
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
    public List<Subtask> showAllSubtasksList() {
        List<Subtask> subtasks = new ArrayList<>();
        for (Epic epic : epics) {
            subtasks.addAll(epic.getSubtasks());
        }
        return subtasks;
    }

    @Override
    public List<Subtask> showSubtasksFromEpic(int id) {
        List<Subtask> subtasks = new ArrayList<>();
        for (Epic epic : epics) {
            if (epic.getId() == id) {
                subtasks = epic.getSubtasks();
                break;
            }
        }
        return subtasks;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic target = null;
        for (Epic epic : epics) {
            if (epic.getId() == id) {
                target = epic;
                break;
            }
        }
        if(target != null) {
            inMemoryHistoryManager.add(target);
        }
        return target;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask target = null;
        for (Epic epic : epics) {
            List<Subtask> list = epic.getSubtasks();
            for (Subtask subtask : list) {
                if (subtask.getId() == id) {
                    target = subtask;
                    break;
                }
            }
        }
        if(target != null) {
            inMemoryHistoryManager.add(target);
        }
        return target;
    }

    @Override
    public Task getTaskById(int id) {
        Task target = null;
        for (Task task : tasks) {
            if (task.getId() == id) {
                target = task;
                break;
            }
        }
        if(target != null) {
            inMemoryHistoryManager.add(target);
        }
        return target;
    }

    @Override
    public void updateEpic(Epic epic, int id) {
        Epic target = null;
        for (Epic oldEpic : epics) {
            if (oldEpic.getId() == id) {
                target = oldEpic;
                break;
            }
        }
        if (target != null) {
            target.update(epic);
        } else {
            System.out.println("Эпик с таким ID не найден!");
        }
    }

    @Override
    public void updateSubtask(Subtask subtask, int id) {
        Subtask target = null;
        for (Epic epic : epics) {
            List<Subtask> list = epic.getSubtasks();
            for (Subtask sub : list) {
                if (sub.getId() == id) {
                    target = sub;
                    break;
                }
            }
        }
        if (target != null) {
            target.update(subtask);
        } else {
            System.out.println("Подзадача с таким ID не найдена!");
        }
    }

    @Override
    public void updateTask(Task task, int id) {
        Task target = null;
        for (Task oldTask : tasks) {
            if (oldTask.getId() == id) {
                target = oldTask;
                break;
            }
        }
        if (target != null) {
            target.update(task);
        } else {
            System.out.println("Задача с таким ID не найдена!");
        }
    }

    @Override
    public void deleteAllTasks() {
        epics.clear();
        tasks.clear();
        inMemoryHistoryManager.clearHistory();
    }

    @Override
    public void deleteSubtask(int id) {
        Subtask target = null;
        Epic targetEpic = null;
        for (Epic epic : epics) {
            for (Subtask subtask : epic.getSubtasks()) {
                if (subtask.getId() == id) {
                    target = subtask;
                    targetEpic = epic;
                    break;
                }
            }
        }
            if (target != null) {
                targetEpic.deleteSubtask(target);
                System.out.println("Задача удалена!");
            } else {
                System.out.println("Подзадача с таким ID не найдена!");
            }
        inMemoryHistoryManager.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        Epic target = null;
        for (Epic epic : epics) {
            if (epic.getId() == id) {
                target = epic;
                break;
            }
        }
        if (target != null) {
            epics.remove(target);
            inMemoryHistoryManager.remove(id);
            for (Subtask subtask : target.getSubtasks()) {
                inMemoryHistoryManager.remove(subtask.getId());
            }
            System.out.println("Задача удалена!");
        } else {
            System.out.println("Подзадача с таким ID не найдена!");
        }
    }

    @Override
    public void deleteTask(int id) {
        Task target = null;
        for (Task task : tasks) {
            if (task.getId() == id) {
                target = task;
                break;
            }
        }
        if (target != null) {
            tasks.remove(target);
            inMemoryHistoryManager.remove(id);
            System.out.println("Задача удалена!");
        } else {
            System.out.println("Подзадача с таким ID не найдена!");
        }
    }

    @Override
    public List<Task> showHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    public HistoryManager getHistoryManager() {
        return inMemoryHistoryManager;
    }
}