package server;

import managers.TaskManager;
import tasks.Subtask;
import tasks.Task;
import utilities.Response;

import java.util.List;

/**
 * Handles <b>/tasks/subtask/</b> endpoint:
 * <p>GET /tasks/subtask/ -- shows all subtasks</p>
 * <p>GET /tasks/subtask/{id} -- shows subtask by id</p>
 * <p>POST /tasks/subtask/ -- creates new subtask which was sent in a request body</p>
 * <p>POST /tasks/subtask/{id} -- updates subtask by id}</p>
 * <p>DELETE /tasks/subtask/{id} -- deletes task by id</p>
 */
public class SubtaskHandler extends TaskHandler<Subtask> {
    private TaskManager manager;

    public SubtaskHandler(TaskManager manager, Class<Subtask> tClass) {
        super(manager, tClass);
        this.manager = manager;
    }

    @Override
    Task getTaskById(int id) {
        return manager.getSubtaskById(id);
    }

    @Override
    List<Task> getTasks() {
        return manager.showAllSubtasksList();
    }

    @Override
    void updateTask(Task task, int id) {
        manager.updateSubtask((Subtask) task, id);
    }

    @Override
    void addTask(Task task) {
        manager.addSubtask((Subtask) task);
    }

    @Override
    Response deleteTask(int id) {
        return manager.deleteSubtask(id);
    }
}