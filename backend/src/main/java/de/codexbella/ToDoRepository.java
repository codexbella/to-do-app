package de.codexbella;

public class ToDoRepository {
    private String title;
    private String taskDescription;
    private boolean done;

    public ToDoRepository(String title, String taskDescription, boolean done) {
        this.title = title;
        this.taskDescription = taskDescription;
        this.done = done;
    }
    public ToDoRepository(String title, String taskDescription) {
        this.title = title;
        this.taskDescription = taskDescription;
        this.done = false;
    }
    public ToDoRepository() {
    }
}
