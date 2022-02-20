package de.codexbella;

public class ToDoItem {
    private String title;
    private String description;
    private boolean done;

    public ToDoItem(String title, String taskDescription, boolean done) {
        this.title = title;
        this.description = taskDescription;
        this.done = done;
    }
    public ToDoItem(String title, String taskDescription) {
        this.title = title;
        this.description = taskDescription;
        this.done = false;
    }
    public ToDoItem(String title, boolean done) {
        this.title = title;
        this.description = "";
        this.done = done;
    }
    public ToDoItem(String title) {
        this.title = title;
        this.description = "";
        this.done = false;
    }

    public ToDoItem() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "title='" + title + '\'' +
                ", taskDescription='" + description + '\'' +
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