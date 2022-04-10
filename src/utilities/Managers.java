package utilities;

import managers.FileBackedTaskManager;
import managers.HttpTaskManager;
import managers.InMemoryTaskManager;
import managers.TaskManager;

import java.net.URI;

public final class Managers {
//    public static TaskManager getDefault() {
//        return new InMemoryTaskManager();
//    }
    public static TaskManager getDefault() {
        //return new HttpTaskManager("tasks.csv", URI.create("http://localhost:8078"));
        return new HttpTaskManager("http://localhost:8078");
    }
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
