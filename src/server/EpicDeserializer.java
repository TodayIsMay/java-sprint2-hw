package server;

import com.google.gson.*;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;

import java.lang.reflect.Type;

public class EpicDeserializer implements JsonDeserializer<Epic> {

    @Override
    public Epic deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        String description = jsonObject.get("description").getAsString();
        Status status = setStatus(jsonObject.get("status").getAsString());
        int id = jsonObject.get("id").getAsInt();
        Epic epic = new Epic(name, description, id, status);
        JsonArray subtasks = jsonObject.getAsJsonArray("subtasks");
        for(JsonElement subtask : subtasks) {
            epic.addSub((Subtask) jsonDeserializationContext.deserialize(subtask, Subtask.class));
        }
        return epic;
    }

    private Status setStatus(String value) {
        if(value.equals("NEW")) {
            return Status.NEW;
        } else if (value.equals("IN_PROGRESS")) {
            return Status.IN_PROGRESS;
        }
        return Status.DONE;
    }
}
