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

    public List<ToDoItem> addItem(ToDoItem toDoItem) {
        toDoRepository.addItem(toDoItem);
        return getToDoList();
    }

    public List<ToDoItem> changeItem(ToDoItem toDoItemChanged) {
        toDoRepository.changeItem(toDoItemChanged);
        return getToDoList();
    }

    public List<ToDoItem> deleteItem(String id) {
        toDoRepository.deleteItem(id);
        return getToDoList();
    }
}