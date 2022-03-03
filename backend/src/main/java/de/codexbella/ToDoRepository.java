package de.codexbella;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ToDoRepository {
    private List<ToDoItem> toDoList;

    public ToDoRepository(List<ToDoItem> toDoItems) {
        this.toDoList = toDoItems;
    }
    public ToDoRepository() {
        this.toDoList = new ArrayList<>();
    }

    public List<ToDoItem> getToDoList() {
        return toDoList;
    }

    @Override
    public String toString() {
        return "ToDoRepository{" +
                "toDoList=" + toDoList +
                '}';
    }

    public void add(ToDoItem toDoItem) {
        toDoList.add(toDoItem);
    }
    public void add(int index, ToDoItem toDoItem) {
        toDoList.add(index, toDoItem);
    }

    public void delete(int index) {
        toDoList.remove(index);
    }
}
