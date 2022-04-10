package server;

import com.sun.net.httpserver.HttpServer;
import managers.TaskManager;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;
import utilities.Managers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {
    TaskManager manager = Managers.getDefault();
    HttpServer server;
    public HttpTaskServer() {
        //manager.addEpic(new Epic("Feed the cat", "description", 5, Status.NEW));
        //manager.addSubtask(new Subtask("find the  cat", 5, 6, Status.NEW, Duration.ofMillis(20000), LocalDateTime.of(2022, 5, 2, 0, 0)));
        //manager.addTask(new Task("task", 1, Status.NEW, Duration.ofMillis(7000), LocalDateTime.now()));
        //manager.addTask(new Task("second task", 2, Status.NEW, Duration.ofMillis(20000), LocalDateTime.of(2022, 5, 1, 0, 0)));
        //manager.addTask(new Task("third task", 3, Status.NEW, Duration.ofMillis(20000), LocalDateTime.of(2022, 4, 15, 0, 0)));
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
}
