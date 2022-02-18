package de.codexbella;

import java.util.List;

public class ToDoService {
    private ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepo) {
        this.toDoRepository = toDoRepo;
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

    @Override
    public String toString() {
        return "ToDoService{" +
                "toDoRepo=" + toDoRepository +
                '}';
    }

    public List<ToDoItem> getAllItemsNotDone() {
        return toDoRepository.getAllItemsNotDone();
    }
}
