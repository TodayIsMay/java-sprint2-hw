package utilities;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Creator {
    private ArrayList<Integer> listOfIDs = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public Epic createEpic() {
        System.out.println("Введите название эпика");
        String name = scanner.nextLine();
        System.out.println("Введите описание");
        String description = scanner.nextLine();
        int epicID = randomize(0, Integer.MAX_VALUE);
        String status = "NEW";
        return new Epic(name, description, epicID, status);
    }

    public Subtask createSubtask() {
        System.out.println("К какому эпику относится подзадача?");
        String belonging = scanner.nextLine();
        System.out.println("Введите название подзадачи");
        String name = scanner.nextLine();
        int ID = randomize(0, Integer.MAX_VALUE);
        String status = "NEW";
        return new Subtask(belonging, name, ID, status);
    }

    public Task createTask() {
        System.out.println("Введите название задачи.");
        String name = scanner.nextLine();
        int ID = randomize(0, Integer.MAX_VALUE);
        String status = "NEW";
        return new Task(name, ID, status);
    }

    public int randomize(int min, int max) {
        Random random = new Random();
        int randomInt = random.nextInt(max - min) + min;
        if (check(randomInt)) {
            randomInt = randomInt / 2 + 17;
        }
        return randomInt;
    }

    public boolean check(int random) {
        return listOfIDs.contains(random);
    }
}