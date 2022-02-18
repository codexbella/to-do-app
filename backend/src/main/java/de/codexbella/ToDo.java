package de.codexbella;

import java.util.Objects;

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

    public ToDo(String title, boolean done) {
        this.title = title;
        this.taskDescription = "";
        this.done = done;
    }

    public ToDo(String title) {
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
