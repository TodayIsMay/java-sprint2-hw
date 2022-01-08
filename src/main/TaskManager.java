package main;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    List<Task> getTasks();

    List<Epic> getEpics();

    void addEpic(Epic epic);

    void addSubtask(Subtask subtask);

    void addTask(Task task);

    List<ArrayList<Subtask>> showAllSubtasksList();

    List<Subtask> showSubtasksFromEpic(int Id);

    Epic getEpicById(int Id);

    Subtask getSubtaskById(int Id);

    Task getTaskById(int Id);

    void updateEpic(Epic epic, int id);

    void updateSubtask(Subtask subtask, int id);

    void updateTask(Task task, int id);

    void deleteAllTasks();

    void deleteSubtask(int Id);

    void deleteEpic(int Id);

    void deleteTask(int Id);

    List<Task> showHistory();
}
