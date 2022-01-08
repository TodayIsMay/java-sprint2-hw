package tasks;

import java.util.Scanner;

public class Task {
    Scanner newScan = new Scanner(System.in);
    private String name;
    private int id;
    private Status status;

    public Task(String name, int Id, Status status) {
        this.name = name;
        this.id = Id;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public void update(Task task) {
        this.setName(task.getName());
        this.setStatus(chooseStatus());
    }

    public Status chooseStatus() {
        Status result = null;
        System.out.println("Выберите новый статус: " + "\n" + "1 - NEW"
                + "\n" + "2 - IN_PROGRESS" + "\n" + "3 - DONE");
        int variant = newScan.nextInt();
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
        }
        return result;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}