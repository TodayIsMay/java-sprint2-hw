package server;

import com.google.gson.*;
import tasks.Epic;
import tasks.Subtask;

import java.lang.reflect.Type;

public class EpicSerializer implements JsonSerializer<Epic> {
    @Override
    public JsonElement serialize(Epic epic, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("name", epic.getName());
        result.addProperty("id", epic.getId());
        result.addProperty("description", epic.getDescription());
        result.addProperty("status", epic.getStatus().toString());
        JsonArray subtasks = new JsonArray();
        result.add("subtasks", subtasks);
        for (Subtask subtask : epic.getSubtasks()) {
            subtasks.add(jsonSerializationContext.serialize(subtask));
        }
        if (subtasks.size() != 0) {
            result.add("duration", jsonSerializationContext.serialize(epic.getDuration()));
            result.add("start_time", jsonSerializationContext.serialize(epic.getStartTime()));
            result.add("end_time", jsonSerializationContext.serialize(epic.getEndTime()));
        }
        return result;
    }
}
