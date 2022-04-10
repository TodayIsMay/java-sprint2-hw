package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.TaskManager;
import tasks.Subtask;
import utilities.DurationAdapter;
import utilities.LocalDateTimeAdapter;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Handles <b>/tasks/subtask/epic/{id}</b> endpoint:
 * <p>- GET /tasks/subtask/epic/{id} -- shows all subtasks from a particular epic</p>
 */
public class SubtasksFromEpicHandler implements HttpHandler {
    TaskManager manager;

    public SubtasksFromEpicHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter());
        Gson json = gsonBuilder.create();

        String response = "";
        String method = exchange.getRequestMethod();
        switch (method) {
            case "GET":
                if(hasParams(exchange.getRequestURI().toString())) {
                    int id = extractId(exchange.getRequestURI().toString());
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Subtask subtask : manager.showSubtasksFromEpic(id)) {
                        stringBuilder.append(json.toJson(subtask));
                    }
                    if (stringBuilder.toString().equals("")) {
                        response = "Список пуст или эпик не найден";
                    } else {
                        response = stringBuilder.toString();
                    }
                    exchange.sendResponseHeaders(200, 0);
                } else {
                    response = "Необходимо ввести id эпика!";
                    exchange.sendResponseHeaders(401, 0);
                }
                break;
            default:
                System.out.println("/tasks/subtask/epic/{id} ожидает GET-запрос, а получил " +
                        exchange.getRequestMethod());
                exchange.sendResponseHeaders(401, 0);
        }
        try(OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private boolean hasParams(String uri) {
        return uri.split("/").length > 4;
    }

    private int extractId(String uri) {
        return Integer.parseInt(uri.split("/")[4]);
    }
}
