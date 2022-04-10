package managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.EpicDeserializer;
import server.EpicSerializer;
import server.KVTaskClient;
import tasks.Epic;
import tasks.Task;
import utilities.DurationAdapter;
import utilities.LocalDateTimeAdapter;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class HttpTaskManager extends FileBackedTaskManager{
    String url;
    KVTaskClient kvTaskClient;

    public HttpTaskManager(String url) {
        this.url = url;
        kvTaskClient = new KVTaskClient(URI.create(url));
    }

    @Override
    public void save() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(Epic.class, new EpicSerializer())
                .registerTypeAdapter(Epic.class, new EpicDeserializer());
        Gson gson = gsonBuilder.create();
        StringBuilder stringBuilder = new StringBuilder();
        for(Task task : this.getTasks()) {
            stringBuilder.append(gson.toJson(task)).append("\n");
        }
        for(Task task : this.getEpics()) {
            stringBuilder.append(gson.toJson(task)).append("\n");
        }
        for(Task task : this.showHistory()) {
            stringBuilder.append(task.toString()).append("\n");
        }
        String json = stringBuilder.toString();
        kvTaskClient.put(String.valueOf(hashCode()), json);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HttpTaskManager that = (HttpTaskManager) o;
        return Objects.equals(url, that.url) && Objects.equals(kvTaskClient, that.kvTaskClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), url, kvTaskClient);
    }
}
