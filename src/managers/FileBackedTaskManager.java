package managers;

import exceptions.ManagerSaveException;
import tasks.*;
import utilities.HistoryManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private static int maxIdFromFile = 0;
    private String file;
    private static TaskManager taskManager = new InMemoryTaskManager();

    public FileBackedTaskManager(String file) {
        super();
        this.file = file;
    }

    public static FileBackedTaskManager loadFromFile(String file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);

        Map<Integer, Task> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.ready()) {
                String str = br.readLine();
                if (!str.isEmpty() | !str.isBlank()) {
                    Task task = Task.fromString(str);
                    if (task instanceof Epic) {
                        manager.addEpicUnsafe((Epic) task);
                        map.put(task.getId(), task);
                        if (task.getId() > maxIdFromFile) {
                            maxIdFromFile = task.getId();
                        }
                    } else if (task instanceof Subtask) {
                        manager.addSubtaskUnsafe((Subtask) task);
                        map.put(task.getId(), task);
                        if (task.getId() > maxIdFromFile) {
                            maxIdFromFile = task.getId();
                        }
                    } else if (task instanceof Task) {
                        manager.addTaskUnsafe(task);
                        map.put(task.getId(), task);
                        if (task.getId() > maxIdFromFile) {
                            maxIdFromFile = task.getId();
                        }
                    }
                } else {
                    String history = br.readLine();
                    for (Integer id : fromString(history)) {
                        manager.getHistoryManager().add(map.get(id));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return manager;
    }

    public int getMaxIdFromFile() {
        return maxIdFromFile;
    }

    public void save() throws ManagerSaveException {
        try (FileWriter out = new FileWriter(file, StandardCharsets.UTF_8)) {
            for (Epic epic : getEpics()) {
                out.write(epic.toString());
                for (Subtask subtask : epic.getSubtasks()) {
                    out.write(subtask.toString());
                }
            }
            for (Task task : getTasks()) {
                out.write(task.toString());
            }
            if (toString(super.getHistoryManager()) != null) {
                out.write("\n");
                out.write(toString(super.getHistoryManager()));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка при автосохранении!");
        }
    }

    private void addEpicUnsafe(Epic epic) {
        super.addEpic(epic);
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addSubtaskUnsafe(Subtask subtask) {
        super.addSubtask(subtask);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addTaskUnsafe(Task task) {
        super.addTask(task);
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEpic(Epic epic, int id) {
        super.updateEpic(epic, id);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateSubtask(Subtask subtask, int id) {
        super.updateSubtask(subtask, id);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateTask(Task task, int id) {
        super.updateTask(task, id);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = super.getSubtaskById(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
        return subtask;
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
        return task;
    }

    public static String toString(HistoryManager manager) {
        if (!manager.getHistory().isEmpty()) {
            StringBuilder res = new StringBuilder();
            for (Task task : manager.getHistory()) {
                String id = String.valueOf(task.getId());
                res.append(id).append(", ");
            }
            return res.deleteCharAt(res.length() - 2).toString();
        }
        return null;
    }

    public static List<Integer> fromString(String value) {
        List<Integer> ids = new ArrayList<>();
        String[] idArray = value.split(",");
        for (int i = 0; i < idArray.length; i++) {
            ids.add(Integer.parseInt(idArray[i].strip()));
        }
        return ids;
    }
}