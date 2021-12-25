package Main;

import Utilities.Checker;
import Utilities.Creator;
import Utilities.Updater;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        Checker checker = new Checker(manager.getMap());
        Updater updater = new Updater(manager.getMap(), manager.getTasks());
        Creator creator = new Creator();
        Scanner scanner = new Scanner(System.in);

        loop:
        while (true) {
            printMenu();
            checker.check();
            int command = scanner.nextInt();
            switch (command) {
                case 1:
                    System.out.println("Какого типа будет эта задача?");
                    System.out.println("1 - эпик");
                    System.out.println("2 - простая задача");
                    int type = scanner.nextInt();
                    if (type == 1) {
                        manager.addEpic(creator.createEpic(), creator.createSubtask());
                    } else if (type == 2) {
                        manager.addTask(creator.createTask());
                    } else {
                        System.out.println("Такой команды нет.");
                    }
                    break;
                case 2:
                    manager.showAllSubtasksList();
                    break;
                case 3:
                    manager.showAllEpicsList();
                    break;
                case 4:
                    manager.showAllTasksList();
                    break;
                case 5:
                    System.out.println("Введите название");
                    String name = scanner.nextLine();
                    manager.showSubtasksFromEpic(name);
                    break;
                case 6:
                    System.out.println("Введите ID.");
                    manager.showAnyTaskByID(scanner.nextInt());
                    break;
                case 7:
                    System.out.println("Выберите один из вариантов ниже: ");
                    System.out.println("1 - обновить статус подзадачи");
                    System.out.println("2 - добавить подзадачи в эпик");
                    System.out.println("3 - обновить статус простой задачи");
                    int variant = scanner.nextInt();
                    if (variant == 1) {
                        System.out.println("Введите ID подзадачи");
                        int ID = scanner.nextInt();
                        updater.updateSubtaskStatus(ID);
                    } else if (variant == 2) {
                        System.out.println("Введите ID эпика");
                        int ID = scanner.nextInt();
                        manager.addSubtaskToEpic(ID);
                    } else if (variant == 3) {
                        System.out.println("Введите ID простой задачи");
                        int ID = scanner.nextInt();
                        updater.updateTaskStatus(ID);
                    }
                    break;
                case 8:
                    manager.delete();
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
