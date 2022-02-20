package de.codexbella;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ToDoRepository {
    private List<ToDoItem> toDoList;

    public ToDoRepository(List<ToDoItem> toDoItems) {
        this.toDoList = toDoItems;
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
        List<ToDoItem> toDoItemsWithSearchTerm = toDoList.stream()
                .filter(todo -> todo.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
        return toDoItemsWithSearchTerm;
    }

    public List<ToDoItem> getAllItemsNotDone() {
        return toDoList.stream().filter(item -> !item.isDone()).toList();
    }

    public boolean addItem(ToDoItem toDoItem) {
        if (toDoList.stream()
                .noneMatch(item -> item.getTitle().equals(toDoItem.getTitle()))) {
            return toDoList.add(toDoItem);
        }
        return false;
    }

    public boolean setTitle(ToDoItem toDoItem, String title) {
        Optional<ToDoItem> itemToChangeOptional = toDoList.stream().filter(item -> item.equals(toDoItem)).findFirst();
        if (!itemToChangeOptional.isEmpty()) {
            ToDoItem itemChanged = itemToChangeOptional.get();
            itemChanged.setTitle(title);
            for (int i = 0; i < toDoList.size(); i++) {
                if (toDoList.get(i).equals(toDoItem)) {
                    toDoList.remove(i);
                    return toDoList.add(itemChanged);
                }
            }
        } else {
            ToDoItem itemToChange = new ToDoItem(title);
            return toDoList.add(itemToChange);
        }
        return false;
    }

    public boolean setDescription(ToDoItem toDoItem, String description) {
        Optional<ToDoItem> itemToChangeOptional = toDoList.stream().filter(item -> item.equals(toDoItem)).findFirst();
        if (!itemToChangeOptional.isEmpty()) {
            ToDoItem itemChanged = itemToChangeOptional.get();
            itemChanged.setDescription(description);
            for (int i = 0; i < toDoList.size(); i++) {
                if (toDoList.get(i).equals(toDoItem)) {
                    toDoList.remove(i);
                    return toDoList.add(itemChanged);
                }
            }
        } else {
            ToDoItem itemToChange = new ToDoItem(description);
            return toDoList.add(itemToChange);
        }
        return false;
    }
}
