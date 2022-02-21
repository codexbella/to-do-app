package de.codexbella;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        boolean notAlreadyInList = toDoList.stream()
                .noneMatch(item -> item.getTitle().equals(title));
        if (notAlreadyInList) {
            Optional<ToDoItem> itemToChangeOptional = toDoList.stream().filter(item -> item.equals(toDoItem)).findFirst();
            if (!itemToChangeOptional.isEmpty()) {
                ToDoItem itemChanged = itemToChangeOptional.get();
                itemChanged.setTitle(title);
/*
                for (int i = 0; i < toDoList.size(); i++) {
                    if (toDoList.get(i).equals(toDoItem)) {
                        toDoList.remove(i);
                        return toDoList.add(itemChanged);
                    }
                }
*/
                return true;
            } else {
                ToDoItem newItem = new ToDoItem(title);
                boolean added = toDoList.add(newItem);
                return added;
            }
        }
        return false;
    }

    public boolean setDescription(ToDoItem toDoItem, String description) {
        Optional<ToDoItem> itemToChangeOptional = toDoList.stream().filter(item -> item.equals(toDoItem)).findFirst();
        if (!itemToChangeOptional.isEmpty()) {
            ToDoItem itemChanged = itemToChangeOptional.get();
            itemChanged.setDescription(description);
/*            for (int i = 0; i < toDoList.size(); i++) {
                if (toDoList.get(i).equals(toDoItem)) {
                    toDoList.remove(i);
                    return toDoList.add(itemChanged);
                }
            }
*/
            return true;
        }
        return false;
    }

    public boolean deleteItem(ToDoItem toDoItem) {
        for (int i = 0; i < toDoList.size(); i++) {
            ToDoItem currentItem = toDoList.get(i);
            if (currentItem.equals(toDoItem)) {
                toDoList.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean setItemAsDone(ToDoItem toDoItem) {
        for (int i = 0; i < toDoList.size(); i++) {
            ToDoItem currentItem = toDoList.get(i);
            if (currentItem.equals(toDoItem)) {
                toDoList.remove(i);
                currentItem.setDone(true);
                return toDoList.add(currentItem);
            }
        }
        return false;
    }

    public boolean setItemAsNotDone(ToDoItem toDoItem) {
        for (int i = 0; i < toDoList.size(); i++) {
            ToDoItem currentItem = toDoList.get(i);
            if (currentItem.equals(toDoItem)) {
                toDoList.remove(i);
                currentItem.setDone(false);
                return toDoList.add(currentItem);
            }
        }
        return false;
    }
}
