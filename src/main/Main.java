package main;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;
import utilities.Creator;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static FileBackedTaskManager fileBackedTaskManager = FileBackedTaskManager.loadFromFile("tasks.csv");

    private static Creator creator;

    public static void main(String[] args) {
        //Решила точку входа оставить здесь, раз уж она уже есть. Но если критично - перенесу.
        creator = new Creator(fileBackedTaskManager.getMax());
        loop:
        while (true) {
            printMenu();
            int command = scanner.nextInt();
            switch (command) {
                case 1:
                    addNewTask();
                    break;
                case 2:
                    showAllSubtasks();
                    break;
                case 3:
                    showAllEpics();
                    break;
                case 4:
                    showAllTasks();
                    break;
                case 5:
                    showSubtasksFromEpic();
                    break;
                case 6:
                    showTaskByID();
                    break;
                case 7:
                    updateTask();
                    break;
                case 8:
                    deleteTask();
                    break;
                case 9:
                    showHistory();
                    break;
                case 0:
                    exit();
                    break loop;
                default:
                    System.out.println("Такой команды нет");
            }
        }
    }

    public static void addNewTask() {
        System.out.println("Какого типа будет эта задача?");
        System.out.println("1 - эпик");
        System.out.println("2 - подзадача");
        System.out.println("3 - простая задача");
        int type = scanner.nextInt();
        if (type == 1) {
            fileBackedTaskManager.addEpic(creator.createEpic());
        } else if (type == 2) {
            fileBackedTaskManager.addSubtask(creator.createSubtask());
        } else if (type == 3) {
            fileBackedTaskManager.addTask(creator.createTask());
        } else {
            System.out.println("Такой команды нет.");
        }
    }

    public static void showAllSubtasks() {
        List<Subtask> subtasks = fileBackedTaskManager.showAllSubtasksList();
        System.out.println("Список всех подзадач.");
        System.out.println("_____________________");
        for (Subtask sub : subtasks) {
            System.out.println("Название подзадачи: " + sub.getName() + ". Статус: " + sub.getStatus()
                    + ". (ID " + sub.getId() + ")");
        }
        System.out.println("_____________________");
    }

    public static void showAllEpics() {
        List<Epic> epics = fileBackedTaskManager.getEpics();
        System.out.println("Список всех эпиков.");
        System.out.println("___________________");
        for (Epic epic : epics) {
            System.out.println("Название эпика: " + epic.getName() + ". Статус: "
                    + epic.getStatus()
                    + ". (ID " + epic.getId() + ")");
        }
        System.out.println("___________________");
    }

    public static void showAllTasks() {
        List<Task> tasks = fileBackedTaskManager.getTasks();
        System.out.println("Список всех простых задач.");
        System.out.println("___________________________");
        for (Task task : tasks) {
            System.out.println("Название простой задачи: " + task.getName() + ". Статус: "
                    + task.getStatus()
                    + ". (ID " + task.getId() + ")");
        }
        System.out.println("___________________________");
    }

    public static void showSubtasksFromEpic() {
        System.out.println("Введите ID эпика:");
        int epicID = scanner.nextInt();
        System.out.println("Получить все подзадачи эпика " + epicID + ".");
        System.out.println("__________________________________________");
        for (Subtask subtask : fileBackedTaskManager.showSubtasksFromEpic(epicID)) {
            System.out.println(subtask.getName());
        }
        System.out.println("__________________________________");
    }

    public static void showTaskByID() {
        System.out.println("Какую задачу надо найти?");
        System.out.println("1 - эпик");
        System.out.println("2 - подзадачу");
        System.out.println("3 - простую задачу");
        int taskType = scanner.nextInt();
        int result;
        switch (taskType) {
            case 1:
                System.out.println("Введите ID эпика:");
                result = scanner.nextInt();
                System.out.println(fileBackedTaskManager.getEpicById(result).getName());
                break;
            case 2:
                System.out.println("Введите ID подзадачи");
                result = scanner.nextInt();
                System.out.println(fileBackedTaskManager.getSubtaskById(result).getName());
                break;
            case 3:
                System.out.println("Введите ID простой задачи.");
                result = scanner.nextInt();
                System.out.println(fileBackedTaskManager.getTaskById(result).getName());
                break;
            default:
                System.out.println("Такой команды нет");
        }
    }

    public static void updateTask() {
        System.out.println("Какую задачу надо обновить?");
        System.out.println("1 - эпик");
        System.out.println("2 - подзадачу");
        System.out.println("3 - простую задачу");
        int command = scanner.nextInt();
        switch (command) {
            case 1:
                System.out.println("Введите ID эпика.");
                int id = scanner.nextInt();
                Epic epic = creator.createEpic();
                fileBackedTaskManager.updateEpic(epic, id);
                break;
            case 2:
                System.out.println("Введите ID подзадачи.");
                id = scanner.nextInt();
                Subtask subtask = creator.createSubtask();
                subtask.setStatus(chooseStatus());
                fileBackedTaskManager.updateSubtask(subtask, id);
                break;
            case 3:
                System.out.println("Введите ID простой задачи.");
                id = scanner.nextInt();
                Task task = creator.createTask();
                task.setStatus(chooseStatus());
                fileBackedTaskManager.updateTask(task, id);
                break;
            default:
                System.out.println("Такой команды нет");
        }
    }

    public static void deleteTask() {
        System.out.println("Удаление ранее добавленных задач.");
        System.out.println("Выберите действие: ");
        System.out.println("1 - удалить все задачи");
        System.out.println("2 - удалить подзадачу по ID");
        System.out.println("3 - удалить эпик по ID");
        System.out.println("4 - удалить простую задачу по ID");
        int newCommand = scanner.nextInt();
        switch (newCommand) {
            case 1:
                fileBackedTaskManager.deleteAllTasks();
                break;
            case 2:
                System.out.println("Введите ID подзадачи:");
                int id = scanner.nextInt();
                fileBackedTaskManager.deleteSubtask(id);
                break;
            case 3:
                System.out.println("Введите ID эпика");
                id = scanner.nextInt();
                fileBackedTaskManager.deleteEpic(id);
                break;
            case 4:
                System.out.println("Введите ID простой задачи.");
                id = scanner.nextInt();
                fileBackedTaskManager.deleteTask(id);
                break;
            default:
                System.out.println("Такой команды нет.");
        }
    }

    public static void showHistory() {
        for (Task task : fileBackedTaskManager.showHistory()) {
            System.out.println(task.getName());
        }
    }

    public static Status chooseStatus() {
        Status result = null;
        System.out.println("Выберите новый статус: " + "\n" + "1 - NEW"
                + "\n" + "2 - IN_PROGRESS" + "\n" + "3 - DONE");
        int variant = scanner.nextInt();
        switch (variant) {
            case 1:
                result = Status.NEW;
                break;
            case 2:
                result = Status.IN_PROGRESS;
                break;
            case 3:
                result = Status.DONE;
                break;
            default:
                System.out.println("Такой команды нет");
        }
        return result;
    }

    public static void exit(){
        fileBackedTaskManager.save();
    }

    public static void printMenu() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1 - добавить новую задачу");
        System.out.println("2 - посмотреть список подзадач.");
        System.out.println("3 - посмотреть список эпиков.");
        System.out.println("4 - посмотреть список простых задач.");
        System.out.println("5 - получить подзадачи конкретного эпика.");
        System.out.println("6 - получить задачу по ID.");
        System.out.println("7 - обновить задачу по ID");
        System.out.println("8 - удалить задачи");
        System.out.println("9 - история просмотров");
        System.out.println("0 - выход из программы.");
    }
}