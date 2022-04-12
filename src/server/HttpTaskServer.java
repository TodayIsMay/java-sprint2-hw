package server;

import com.sun.net.httpserver.HttpServer;
import managers.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import utilities.Managers;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private TaskManager manager = Managers.getDefault();
    private HttpServer server;
    public HttpTaskServer() {
        try {
            server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            server.createContext("/tasks/task/", new TaskHandler<>(manager, Task.class));
            server.createContext("/tasks/", new MainTaskHandler(manager));
            server.createContext("/tasks/subtask/", new SubtaskHandler(manager, Subtask.class));
            server.createContext("/tasks/epic/", new EpicHandler<>(manager, Epic.class));
            server.createContext("/tasks/subtask/epic/", new SubtasksFromEpicHandler(manager));
            server.createContext("/tasks/history", new HistoryHandler(manager));
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        server.stop(1);
    }

    public TaskManager getManager() {
        return manager;
    }

    public HttpServer getServer() {
        return server;
    }

    public void setServer(HttpServer server) {
        this.server = server;
    }
}
