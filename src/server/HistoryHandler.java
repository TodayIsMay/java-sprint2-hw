package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.TaskManager;
import tasks.Task;

import java.io.IOException;
import java.io.OutputStream;

public class HistoryHandler implements HttpHandler {
    TaskManager manager;

    public HistoryHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "";
        String method = exchange.getRequestMethod();
        switch (method) {
            case "GET":
                StringBuilder builder = new StringBuilder();
                for (Task task : manager.showHistory()) {
                    builder.append(task.toString());
                }
                response = builder.toString();
                exchange.sendResponseHeaders(200, 0);
                break;
            default:
                System.out.println("/tasks/history/ ожидает GET-метод, а получил " + exchange.getRequestMethod());
                exchange.sendResponseHeaders(401, 0);
        }
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
