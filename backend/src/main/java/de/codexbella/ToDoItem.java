package de.codexbella;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
import java.util.UUID;

@Document(collection = "todoitems")
@Data
public class ToDoItem {
    @Id
    private String id;
    private String title;
    private String description = "";
    private boolean done = false;

    public ToDoItem(String title, String taskDescription, boolean done) {
        this.title = title;
        this.description = taskDescription;
        this.done = done;
    }
    public ToDoItem(String title) {
        this.title = title;
        this.description = "";
        this.done = false;
    }
    public ToDoItem() {
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

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
        return "ToDoItem{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", done=" + done +
                '}';
    }
}
