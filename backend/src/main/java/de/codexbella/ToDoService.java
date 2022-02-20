package de.codexbella;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService {
    private ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }
    public ToDoService() {
        this.toDoRepository = new ToDoRepository();
    }

    @Override
    public String toString() {
        return "ToDoService{" +
                "toDoRepo=" + toDoRepository +
                '}';
    }

    public List<ToDoItem> getToDoList() {
        return toDoRepository.getToDoList();
    }

    public List<ToDoItem> getMatchingToDoItems(String searchTerm) {
        return toDoRepository.getMatchingToDoItems(searchTerm);
    }

    public List<ToDoItem> getAllItemsNotDone() {
        return toDoRepository.getAllItemsNotDone();
    }

    public boolean addItem(ToDoItem toDoItem) {
        return toDoRepository.addItem(toDoItem);
    }

    public boolean setTitle(ToDoItem toDoItem, String title) {
        return toDoRepository.setTitle(toDoItem, title);
    }

    public boolean setDescription(ToDoItem toDoItem, String description) {
        return toDoRepository.setDescription(toDoItem, description);
    }

    public boolean deleteItem(ToDoItem toDoItem) {
        return toDoRepository.deleteItem(toDoItem);
    }

    public boolean setAsDone(ToDoItem toDoItem) {
        return toDoRepository.setItemAsDone(toDoItem);
    }

    public boolean setAsNotDone(ToDoItem toDoItem) {
        return toDoRepository.setItemAsNotDone(toDoItem);
    }
}