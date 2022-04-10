package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import utilities.Response;

import java.util.List;
import java.util.Map;

public interface TaskManager {
    List<Task> getTasks();

    List<Task> getEpics();

    void addEpic(Epic epic);

    void addSubtask(Subtask subtask);

    void addTask(Task task);

    List <Task> showAllSubtasksList();

    List<Subtask> showSubtasksFromEpic(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    Task getTaskById(int id);

    String updateEpic(Epic epic, int id);

    String updateSubtask(Subtask subtask, int id);

    String updateTask(Task task, int id);

    void deleteAllTasks();

    Response deleteSubtask(int id);

    Response deleteEpic(int id);

    Response deleteTask(int id);

    List<Task> showHistory();

    boolean doesIntersect(Task targetTask);

    Map<Task, String> getPrioritizedTasks();
}
