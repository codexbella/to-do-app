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

    public List<ToDoItem> setTitle(ToDoItem toDoItem, String title) {
        toDoRepository.setTitle(toDoItem, title);
        return getToDoList();
    }

    public List<ToDoItem> setDescription(ToDoItem toDoItem, String description) {
        toDoRepository.setDescription(toDoItem, description);
        return getToDoList();
    }

    public List<ToDoItem> deleteItem(String title) {
        toDoRepository.deleteItem(title);
        return getToDoList();
    }

    public List<ToDoItem> setAsDone(ToDoItem toDoItem) {
        List<ToDoItem> toDoList = toDoRepository.getToDoList();
            for (int i = 0; i < toDoList.size(); i++) {
                ToDoItem currentItem = toDoList.get(i);
                if (currentItem.equals(toDoItem)) {
                    currentItem.setDone(true);
                    return toDoList;
                }
            }
        return toDoList;
    }

    public List<ToDoItem> setAsNotDone(ToDoItem toDoItem) {
        List<ToDoItem> toDoList = toDoRepository.getToDoList();
            for (int i = 0; i < toDoList.size(); i++) {
                ToDoItem currentItem = toDoList.get(i);
                if (currentItem.equals(toDoItem)) {
                    currentItem.setDone(false);
                    return toDoList;
                }
            }
        return toDoList;
    }

}