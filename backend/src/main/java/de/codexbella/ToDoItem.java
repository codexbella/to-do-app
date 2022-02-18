package de.codexbella;

public class ToDoItem {
    private String title;
    private String taskDescription;
    private boolean done;

    public ToDoItem(String title, String taskDescription, boolean done) {
        this.title = title;
        this.taskDescription = taskDescription;
        this.done = done;
    }
    public ToDoItem(String title, String taskDescription) {
        this.title = title;
        this.taskDescription = taskDescription;
        this.done = false;
    }

    public ToDoItem(String title, boolean done) {
        this.title = title;
        this.taskDescription = "";
        this.done = done;
    }

    public ToDoItem(String title) {
        this.title = title;
        this.taskDescription = "";
        this.done = false;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "title='" + title + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", done=" + done +
                '}';
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }
}
