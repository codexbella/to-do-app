package de.codexbella;

public class ToDo {
    private String title;
    private String taskDescription;
    private boolean done;

    public ToDo(String title, String taskDescription, boolean done) {
        this.title = title;
        this.taskDescription = taskDescription;
        this.done = done;
    }
    public ToDo(String title, String taskDescription) {
        this.title = title;
        this.taskDescription = taskDescription;
        this.done = false;
    }
    public ToDo(String title) {
        this.title = title;
        this.taskDescription = "";
        this.done = false;
    }
}
