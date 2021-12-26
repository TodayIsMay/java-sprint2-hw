package utilities;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Creator {
    private ArrayList<Integer> list = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    Scanner intScanner = new Scanner(System.in);

    public Epic createEpic() {
        System.out.println("Введите название эпика");
        String name = scanner.nextLine();
        System.out.println("Введите описание");
        String description = scanner.nextLine();
        int epicID = generateID();
        String status = "NEW";
        return new Epic(name, description, epicID, status);
    }

    public Subtask createSubtask() {
        System.out.println("К какому эпику относится подзадача(ID)?");
        int belonging = intScanner.nextInt();
        System.out.println("Введите название подзадачи");
        String name = scanner.nextLine();
        int ID = generateID();
        String status = "NEW";
        return new Subtask(belonging, name, ID, status);
    }

    public Task createTask() {
        System.out.println("Введите название задачи.");
        String name = scanner.nextLine();
        int ID = generateID();
        String status = "NEW";
        return new Task(name, ID, status);
    }

    public int generateID() {
        int result = 1;
        for (Integer number: list) {
            if(number == result) {
                result++;
            }
        }
        list.add(result);
        return result;
    }
}