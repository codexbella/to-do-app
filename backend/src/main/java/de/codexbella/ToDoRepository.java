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
        if (toDoList.stream().noneMatch(item -> item.getTitle().equals(toDoItem.getTitle()))) {
            return toDoList.add(toDoItem);
        }
        return false;
    }

    public boolean changeItem(ToDoItem toDoItemChanged) {
        Optional<ToDoItem> itemToChangeOptional = toDoList.stream().filter(item -> item.getId().equals(toDoItemChanged.getId())).findFirst();
        if (itemToChangeOptional.isPresent()) {
                ToDoItem itemToChange = itemToChangeOptional.get();
            if (toDoList.stream().filter(item -> item.getTitle().equals(toDoItemChanged.getTitle())).findFirst().isEmpty()) {
                itemToChange.setTitle(toDoItemChanged.getTitle());
            }
                itemToChange.setDescription(toDoItemChanged.getDescription());
                itemToChange.setDone(toDoItemChanged.isDone());
                return true;
        } else {
            return false;
        }
    }

    public boolean deleteItem(String id) {
        for (int i = 0; i < toDoList.size(); i++) {
            ToDoItem currentItem = toDoList.get(i);
            if (currentItem.getId().equals(id)) {
                toDoList.remove(i);
                return true;
            }
        }
        return false;
    }
}
