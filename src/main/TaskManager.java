package main;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;

public interface TaskManager {
    ArrayList<Task> getTasks();

    ArrayList<Epic> getEpics();

    void addEpic(Epic epic);

    void addSubtask(Subtask subtask);

    void addTask(Task task);

    ArrayList<ArrayList<Subtask>> showAllSubtasksList();

    ArrayList<Subtask> showSubtasksFromEpic(int ID);

    Epic getEpicByID(int ID);

    Subtask getSubtaskByID(int ID);

    Task getTaskByID(int ID);

    void delete(int ID, int command);

    ArrayList<Task> history();
}
