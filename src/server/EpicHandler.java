package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.TaskManager;
import tasks.Epic;
import tasks.Task;
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

//public class EpicHandler extends TaskHandler<Epic>{
//TaskManager manager;

public class EpicHandler<T extends Task> implements HttpHandler {
    private TaskManager manager;
    private Class<T> tClass;

    public EpicHandler(TaskManager manager, Class<T> tClass) {
        this.manager = manager;
        this.tClass = tClass;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "";
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Epic.class, new EpicSerializer())
                .registerTypeAdapter(Epic.class, new EpicDeserializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter());
        //.registerTypeAdapter(tClass, adapter);
        Gson gson = gsonBuilder.create();
        String method = exchange.getRequestMethod();
        String requestUri = exchange.getRequestURI().toString();
        int id = -1;
        boolean hasParams = hasParams(requestUri);
        if (hasParams) {
            id = extractId(requestUri);
        }
        switch (method) {
            case "GET":
                System.out.println(requestUri);
                if (hasParams) {
                    if (getTaskById(id) != null) {
                        response = gson.toJson(getTaskById(id));
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
                    if (methodResponse.equals(Response.SUCCESS)) {
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
        return manager.getEpicById(id);
    }

    List<Task> getTasks() {
        return manager.getEpics();
    }

    void updateTask(Task task, int id) {
        manager.updateEpic((Epic) task, id);
    }

    void addTask(Task task) {
        manager.addEpic((Epic) task);
    }

    Response deleteTask(int id) {
        return manager.deleteEpic(id);
    }
}
//    public EpicHandler(TaskManager manager, Class<Epic> epicClass) {
//        super(manager, epicClass);
//        this.manager = manager;
//    }
//
//    @Override
//    Task getTaskById(int id) {
//        return manager.getEpicById(id);
//    }
//
//    @Override
//    List<Task> getTasks() {
//        return manager.getEpics();
//    }
//
//    @Override
//    void updateTask(Task task, int id) {
//        manager.updateEpic((Epic) task, id);
//    }
//
//    @Override
//    void addTask(Task task) {
//        manager.addEpic((Epic) task);
//    }
//
//    @Override
//    Response deleteTask(int id) {
//        return manager.deleteEpic(id);
//    }
