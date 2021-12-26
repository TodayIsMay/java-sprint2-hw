package main;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import utilities.Creator;
import utilities.Updater;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        Updater updater = new Updater(manager.getEpics(), manager.getTasks());
        Creator creator = new Creator();
        Scanner scanner = new Scanner(System.in);

        loop:
        while (true) {
            printMenu();
            int command = scanner.nextInt();
            switch (command) {
                case 1:
                    System.out.println("Какого типа будет эта задача?");
                    System.out.println("1 - эпик");
                    System.out.println("2 - подзадача");
                    System.out.println("3 - простая задача");
                    int type = scanner.nextInt();
                    if (type == 1) {
                        manager.addEpic(creator.createEpic());
                    } else if (type == 2) {
                        manager.addSubtask(creator.createSubtask());
                    } else if (type == 3) {
                        manager.addTask(creator.createTask());
                    } else {
                        System.out.println("Такой команды нет.");
                    }
                    break;
                case 2:
                    ArrayList<ArrayList<Subtask>> list = manager.showAllSubtasksList();
                    System.out.println("Список всех подзадач.");
                    System.out.println("_____________________");
                    for (ArrayList<Subtask> subtasks : list) {
                        for (Subtask sub : subtasks) {
                            System.out.println("Название подзадачи: " + sub.getName() + ". Статус: " + sub.getStatus()
                                    + ". (ID " + sub.getID() + ")");
                        }
                    }
                    System.out.println("_____________________");
                    break;
                case 3:
                    ArrayList<Epic> epics = manager.getEpics();
                    System.out.println("Список всех эпиков.");
                    System.out.println("___________________");
                    for (Epic epic : epics) {
                        System.out.println("Название эпика: " + epic.getName() + ". Статус: "
                                + epic.getStatus(epics, epic.getSubtasks())
                                + ". (ID " + epic.getID() + ")");
                    }
                    System.out.println("___________________");
                    break;
                case 4:
                    ArrayList<Task> tasks = manager.getTasks();
                    System.out.println("Список всех простых задач.");
                    System.out.println("___________________________");
                    for (Task task : tasks) {
                        System.out.println("Название простой задачи: " + task.getName() + ". Статус: "
                                + task.getStatus()
                                + ". (ID " + task.getID() + ")");
                    }
                    System.out.println("___________________________");
                    break;
                case 5:
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Введите название:");
                    String name = scanner1.nextLine();
                    System.out.println("Получить все подзадачи эпика " + name + ".");
                    System.out.println("__________________________________________");
                    for (Subtask subtask : manager.showSubtasksFromEpic(name)) {
                        System.out.println(subtask.getName());
                    }
                    System.out.println("__________________________________");

                    break;
                case 6:
                    System.out.println("Введите ID:");
                    int result = scanner.nextInt();
                    System.out.println("Получение задачи по ID.");
                    System.out.println("__________________________________");
                    manager.showAnyTaskByID(result);
                    System.out.println("__________________________________");
                    break;
                case 7:
                    System.out.println("Выберите один из вариантов ниже: ");
                    System.out.println("1 - обновить статус подзадачи");
                    System.out.println("2 - обновить статус простой задачи");
                    int variant = scanner.nextInt();
                    if (variant == 1) {
                        System.out.println("Введите ID подзадачи");
                        int ID = scanner.nextInt();
                        updater.updateSubtaskStatus(ID);
                    } else if (variant == 2) {
                        System.out.println("Введите ID простой задачи");
                        int ID = scanner.nextInt();
                        updater.updateTaskStatus(ID);
                    }
                    break;
                case 8:
                    System.out.println("Удаление ранее добавленных задач.");
                    System.out.println("Выберите действие: ");
                    System.out.println("1 - удалить все задачи");
                    System.out.println("2 - удалить подзадачу по ID");
                    System.out.println("3 - удалить эпик по ID");
                    System.out.println("4 - удалить простую задачу по ID");
                    int newCommand = scanner.nextInt();

                    if (newCommand == 1) {
                        int ID = 0;
                        manager.delete(ID, newCommand);
                    } else if (newCommand == 2) {
                        System.out.println("Введите ID подзадачи:");
                        int ID = scanner.nextInt();
                        manager.delete(ID, newCommand);
                    } else if (newCommand == 3) {
                        System.out.println("Введите ID эпика");
                        int ID = scanner.nextInt();
                        manager.delete(ID, newCommand);
                    } else if (newCommand == 4) {
                        System.out.println("Введите ID простой задачи.");
                        int ID = scanner.nextInt();
                        manager.delete(ID, newCommand);
                    } else {
                        System.out.println("Такой команды нет.");
                    }
                    break;

                case 0:
                    break loop;
            }
        }
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
        System.out.println("0 - выход из программы.");
    }
}