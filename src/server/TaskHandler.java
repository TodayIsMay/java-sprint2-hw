package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.TaskManager;
import tasks.Subtask;
import tasks.Task;
import tasks.Type;
import utilities.DurationAdapter;
import utilities.LocalDateTimeAdapter;
import utilities.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Handles <b>"/tasks/task/"</b> endpoint:
 * <p>- GET /tasks/task/ -- shows all simple tasks from manager</p>
 * <p>- GET /tasks/task/{id} -- shows simple task by id</p>
 * <p>- POST /tasks/task/ -- adds new simple task which was sent in a request body</p>
 * <p>- POST /tasks/task/{id} -- updates simple task by id, using information from a request body</p>
 * <p>- DELETE /tasks/task/{id} -- deletes simple task by id</p>
 */
public class TaskHandler<T extends Task> implements HttpHandler{
    private TaskManager manager;
    private Class<T> tClass;

    public TaskHandler(TaskManager manager, Class<T> tClass) {
        this.manager = manager;
        this.tClass = tClass;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "";
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter());
        Gson gson = gsonBuilder.create();
        String method = exchange.getRequestMethod();
        String requestUri = exchange.getRequestURI().toString();
        int id = -1;
        boolean hasParams = hasParams(requestUri);
        if(hasParams) {
            id = extractId(requestUri);
        }
        switch (method) {
            case "GET":
                System.out.println(requestUri);
                if (hasParams) {
                    Task task = getTaskById(id);
                    if(task != null) {
                        response = gson.toJson(task);
                    } else {
                        response = "Задача с таким id не найдена!";
                    }
                } else {
                    StringBuilder tasksList = new StringBuilder();
                    for (Task task : getTasks()) {
                        tasksList.append(gson.toJson(task)).append("\n");
                    }
                    response = tasksList.toString();
                }
                exchange.sendResponseHeaders(200, 0);
                break;
            case "POST":
                System.out.println(requestUri);
                if (hasParams) {
                    String body;
                    try (InputStream is = exchange.getRequestBody()) {
                        byte[] array = is.readAllBytes();
                        body = new String(array, StandardCharsets.UTF_8);
                    }
                    Task task = gson.fromJson(body, tClass);
                    updateTask(task, id);
                    exchange.sendResponseHeaders(200, 0);
                } else {
                    String body;
                    try (InputStream is = exchange.getRequestBody()) {
                        byte[] array = is.readAllBytes();
                        body = new String(array, StandardCharsets.UTF_8);
                    }
                    System.out.println(body);
                    Task task = gson.fromJson(body, tClass);
                    addTask(task);
                    exchange.sendResponseHeaders(200, 0);
                }
                break;
            case "DELETE":
                if (hasParams) {
                    Response methodResponse = deleteTask(id);
                    if(methodResponse.equals(Response.SUCCESS)) {
                        response = "Задача удалена!";
                        exchange.sendResponseHeaders(200, 0);
                    } else if (methodResponse.equals(Response.NOT_FOUND)) {
                        response = "Задача с таким id не найдена!";
                        exchange.sendResponseHeaders(404, 0);
                    }
                } else {
                    response = "Не был введён id";
                    exchange.sendResponseHeaders(400, 0);
                }
                break;
            default:
                System.out.println(exchange.getRequestMethod() + "-запрос не поддерживается");
                exchange.sendResponseHeaders(405, 0);
        }
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    /**
     * @param uri String address from the HttpRequest
     * @return true if there are any path parameters
     */
    private boolean hasParams(String uri) {
        return uri.split("/").length > 3;
    }

    /**
     * @param uri String address from the HttpRequest
     * @return int id, extracted from the uri
     */
    private int extractId(String uri) {
        return Integer.parseInt(uri.split("/")[3]);
    }

    Task getTaskById(int id) {
        return manager.getTaskById(id);
    }

    List<Task> getTasks() {
        return  manager.getTasks();
    }

    void updateTask(Task task, int id) {
        manager.updateTask(task, id);
    }

    void addTask(Task task) {
        manager.addTask(task);
    }

    Response deleteTask(int id) {
        return manager.deleteTask(id);
    }
}