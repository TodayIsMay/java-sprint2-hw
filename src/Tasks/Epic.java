package Tasks;

public class Epic extends Task{
    private String epicDescription;

    public Epic(String name, String epicDescription, int ID, String status) {
        super(name, ID, status);
        this.epicDescription = epicDescription;
    }

    public void setEpicDescription(String epicDescription) {
        this.epicDescription = epicDescription;
    }

    public String getEpicDescription() {
        return epicDescription;
    }
}

