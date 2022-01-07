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

    ArrayList<ArrayList<Subtask>> showAllSubtasksList();

    ArrayList<Subtask> showSubtasksFromEpic(int Id);

    Epic getEpicById(int Id);

    Subtask getSubtaskById(int Id);

    Task getTaskById(int Id);

    void updateStatus(int Id);

    void chooseStatus(Task task);

    void deleteAllTasks();

    void deleteSubtask(int Id);

    void deleteEpic(int Id);

    void deleteTask(int Id);

    List<Task> showHistory();
}
