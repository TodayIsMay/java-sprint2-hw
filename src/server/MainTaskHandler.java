package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.TaskManager;
import tasks.Task;
import utilities.DurationAdapter;
import utilities.LocalDateTimeAdapter;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Handles <b>"/tasks/"</b> endpoint:
 * <p>GET /tasks/ -- shows prioritized tasks</p>
 * <p>DELETE /tasks/ -- deletes all tasks</p>
 */
public class MainTaskHandler implements HttpHandler {
    private TaskManager manager;

    public MainTaskHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response = "";
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        Gson gson = gsonBuilder.create();
        switch (method) {
            case "GET":
                StringBuilder builder = new StringBuilder();
                for (Task task : manager.getPrioritizedTasks().keySet()) {
                    builder.append(gson.toJson(task)).append("\n");
                }
                response = builder.toString();
                exchange.sendResponseHeaders(200, 0);
                break;
            case "DELETE":
                manager.deleteAllTasks();
                exchange.sendResponseHeaders(200, 0);
                break;
            default:
                System.out.println("/tasks/ ждёт GET-запрос или DELETE-запрос, а получил " + exchange.getRequestMethod());
                exchange.sendResponseHeaders(405, 0);
        }
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    public TaskManager getManager() {
        return manager;
    }

    public void setManager(TaskManager manager) {
        this.manager = manager;
    }
}