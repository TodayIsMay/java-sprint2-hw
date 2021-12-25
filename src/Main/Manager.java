package Main;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import Utilities.Creator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Manager {
    Creator creator = new Creator();
    ArrayList<Task> tasks;
    ArrayList<Epic> epics;
    HashMap<Epic, ArrayList<Subtask>> map;
    Scanner newScan = new Scanner(System.in);

    public Manager() {
        tasks = new ArrayList<>();
        epics = new ArrayList<>();
        map = new HashMap<>();
    }

    public HashMap<Epic, ArrayList<Subtask>> getMap() {
        return map;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addEpic(Epic epic, Subtask subtask) {
        epics.add(epic);
        if (map.containsKey(epic)) {
            ArrayList<Subtask> list = map.get(epic);
            list.add(subtask);
        } else {
            ArrayList<Subtask> list = new ArrayList<>();
            list.add(subtask);
            map.put(epic, list);
        }
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void showAllSubtasksList() {
        System.out.println("Список всех подзадач.");
        System.out.println("_____________________");
        for (ArrayList<Subtask> subtask : map.values()) {
            for (Subtask sub : subtask) {
                System.out.println("Название подзадачи: " + sub.getName() + ". Статус: " + sub.getStatus()
                        + ". (ID " + sub.getID() + ")");
            }
        }
        System.out.println("_____________________");
    }

    public void showAllEpicsList() {
        System.out.println("Список всех эпиков.");
        System.out.println("___________________");
        for (Epic epic : map.keySet()) {
            System.out.println("Название эпика: " + epic.getName() + ". Статус: " + epic.getStatus()
                    + ". (ID " + epic.getID() + ")");
        }
        System.out.println("___________________");
    }

    public void showAllTasksList() {
        System.out.println("Список всех простых задач.");
        System.out.println("___________________________");
        for (Task task : tasks) {
            System.out.println("Название простой задачи: " + task.getName() + ". Статус: " + task.getStatus()
                    + ". (ID " + task.getID() + ")");
        }
        System.out.println("___________________________");
    }

    public void showSubtasksFromEpic(String name) {
        System.out.println("Получить все подзадачи эпика " + name + ".");
        System.out.println("__________________________________________");
        Epic target = null;
        for (Epic epic : map.keySet()) {
            if (epic.getName().equals(name)) {
                target = epic;
            }
        }
        ArrayList<Subtask> list = map.get(target);
        for (Subtask subtask : list) {
            System.out.println(subtask.getName());
        }
        System.out.println("__________________________________");
    }

    public void showAnyTaskByID(int ID) {
        System.out.println("Получение задачи по ID.");
        System.out.println("__________________________________");
        for (Epic epic : map.keySet()) {
            if (epic.getID() == ID) {
                System.out.println(epic.getName());
            } else {
                ArrayList<Subtask> list = map.get(epic);
                for (Subtask subtask : list) {
                    if (subtask.getID() == ID) {
                        System.out.println(subtask.getName());
                    }
                }
            }
        }
        for (Task task : tasks) {
            if (task.getID() == ID) {
                System.out.println(task.getName());
            }
        }
        System.out.println("__________________________________");
    }

    public void addSubtaskToEpic(int ID) {
        for (Epic epic : map.keySet()) {
            if (epic.getID() == ID) {
                ArrayList<Subtask> list = map.get(epic);
                list.add(creator.createSubtask());
            }
        }
    }

    public void delete() {
        System.out.println("Удаление ранее добавленных задач.");
        System.out.println("Выберите действие: ");
        System.out.println("1 - удалить все задачи");
        System.out.println("2 - удалить подзадачу по ID");
        System.out.println("3 - удалить эпик по ID");
        System.out.println("4 - удалить простую задачу по ID");
        int command = newScan.nextInt();
        int ID;
        if (command == 1) {
            map.clear();
            tasks.clear();
        } else if (command == 2) {
            System.out.println("Введите ID подзадачи");
            ID = newScan.nextInt();
            Subtask target = null;
            for (ArrayList<Subtask> subtask : map.values()) {
                for (Subtask sub : subtask) {
                    if (sub.getID() == ID) {
                        target = sub;
                    }
                }
                subtask.remove(target);
            }
        } else if (command == 3) {
            System.out.println("Введите ID эпика");
            ID = newScan.nextInt();
            Epic target = null;
            for (Epic epic : map.keySet()) {
                if (epic.getID() == ID) {
                    target = epic;
                }
            }
            map.remove(target);
        } else if (command == 4) {
            System.out.println("Введите ID простой задачи.");
            ID = newScan.nextInt();
            Task target = null;
            for (Task task : tasks) {
                if (task.getID() == ID) {
                    target = task;
                }
            }
            tasks.remove(target);
        } else {
            System.out.println("Такой команды нет.");
        }
    }
}