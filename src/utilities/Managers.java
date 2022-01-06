package utilities;

import main.InMemoryTaskManager;
import main.TaskManager;

public final class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
}
