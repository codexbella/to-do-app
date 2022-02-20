package de.codexbella;

import java.util.Objects;

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
/*
    public ToDoItem(String title) {
        this.title = "";
        this.description = "";
        this.done = false;
    }
    */
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
    public boolean isDone() {
        return done;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "title='" + title + '\'' +
                ", taskDescription='" + description + '\'' +
                ", done=" + done +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ToDoItem toDoItem = (ToDoItem) o;
        return done == toDoItem.done && title.equals(toDoItem.title) && description.equals(toDoItem.description);
    }
    @Override
    public int hashCode() {
        return Objects.hash(title, description, done);
    }
}
