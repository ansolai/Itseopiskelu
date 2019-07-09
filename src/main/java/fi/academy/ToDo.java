package fi.academy;

public class ToDo {

    private int id;
    private String task;

    public ToDo(){
    }

    public ToDo(String task){
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public String toString(){
        final StringBuilder builder = new StringBuilder("Tehtavat{");
        builder.append("id=").append(id);
        builder.append(", tehtava='").append(task).append('\'');
        builder.append('}');
        return builder.toString();
    }
}
