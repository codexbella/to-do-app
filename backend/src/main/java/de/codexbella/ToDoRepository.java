package de.codexbella;

import java.util.List;
import java.util.stream.Collectors;

public class ToDoRepository {
    private List<ToDoItem> toDoList;

    public ToDoRepository(List<ToDoItem> toDos) {
        this.toDoList = toDos;
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

    public List<ToDoItem> getMatchingToDoItems(String searchTerm) {
        String searchTermLowercase = searchTerm.toLowerCase();
        List<ToDoItem> toDoList = this.toDoList;
        List<ToDoItem> toDosWithSearchTerm = toDoList.stream()
                .filter(todo -> todo.getTitle().toLowerCase().contains(searchTermLowercase))
                .collect(Collectors.toList());
        return toDosWithSearchTerm;
    }

    public List<ToDoItem> getAllItemsNotDone() {
        return toDoList.stream().filter(item -> !item.isDone()).toList();
    }
}
