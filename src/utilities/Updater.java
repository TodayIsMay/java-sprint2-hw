package utilities;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Updater {
    Scanner newScan = new Scanner(System.in);
    ArrayList<Task> tasks;
    ArrayList<Epic> epics;

    public Updater(ArrayList<Epic> epics, ArrayList<Task> tasks) {
        this.epics = epics;
        this.tasks = tasks;
    }

    public void updateSubtaskStatus(int ID) {
        for (Epic epic : epics) {
            for (Subtask sub : epic.getSubtasks()) {
                if (sub.getID() == ID) {
                    System.out.println("Выберите новый статус: " + "\n" + "1 - NEW"
                            + "\n" + "2 - IN_PROGRESS" + "\n" + "3 - DONE");
                    int variant = newScan.nextInt();
                    switch (variant) {
                        case 1:
                            sub.setStatus("NEW");
                            break;
                        case 2:
                            sub.setStatus("IN_PROGRESS");
                            break;
                        case 3:
                            sub.setStatus("DONE");
                            break;
                    }
                }
            }
        }
    }

    public void updateTaskStatus(int ID) {
        for (Task task : tasks) {
            if (task.getID() == ID) {
                System.out.println("Выберите новый статус: " + "\n" + "1 - NEW"
                        + "\n" + "2 - IN_PROGRESS" + "\n" + "3 - DONE");
                int variant = newScan.nextInt();
                switch (variant) {
                    case 1:
                        task.setStatus("NEW");
                        break;
                    case 2:
                        task.setStatus("IN_PROGRESS");
                        break;
                    case 3:
                        task.setStatus("DONE");
                        break;
                }
            }
        }
    }
}