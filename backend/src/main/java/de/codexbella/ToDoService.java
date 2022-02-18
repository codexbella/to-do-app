package de.codexbella;

import java.util.List;

public class ToDoService {
    private ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepo) {
        this.toDoRepository = toDoRepo;
    }

    @Override
    public String toString() {
        return "ToDoService{" +
                "toDoRepo=" + toDoRepository +
                '}';
    }

    public List<ToDoItem> getToDos() {
        return toDoRepository.getToDoList();
    }

    public List<ToDoItem> getMatchingToDoItems(String searchTerm) {
        return toDoRepository.getMatchingToDoItems(searchTerm);
    }

    public void setAsDone(ToDoItem toDo) {
        toDo.setDone(true);
    }

    public List<ToDoItem> getAllItemsNotDone() {
        return toDoRepository.getAllItemsNotDone();
    }

    public boolean addItem(ToDoItem toDoItem) {
        return toDoRepository.addItem(toDoItem);
    }

    public void setTitle(ToDoItem toDoItem, String title) {
        toDoRepository.setTitle(toDoItem, title);
    }
}
//TODO Ordering the list
//TODO Adding and then filtering with distinct (stream)
