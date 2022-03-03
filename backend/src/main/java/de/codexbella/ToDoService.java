package de.codexbella;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<ToDoItem> toDoItemsWithSearchTerm = toDoRepository.getToDoList().stream()
                .filter(todo -> todo.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
        return toDoItemsWithSearchTerm;
    }

    public List<ToDoItem> getAllItemsNotDone() {
        return toDoRepository.getToDoList().stream().filter(item -> !item.isDone()).toList();
    }

    public List<ToDoItem> addItem(ToDoItem toDoItem) {
        if (toDoRepository.getToDoList().stream().noneMatch(item -> item.getTitle().equalsIgnoreCase(toDoItem.getTitle()))) {
            toDoRepository.add(toDoItem);
        }
        return getToDoList();
    }

    public List<ToDoItem> changeItem(ToDoItem toDoItemChanged) {
        for (int i = 0; i < toDoRepository.getToDoList().size(); i++) {
            ToDoItem currentItem = toDoRepository.getToDoList().get(i);
            if (currentItem.getId().equals(toDoItemChanged.getId())) {
                toDoRepository.delete(i);
                if (toDoRepository.getToDoList().stream().filter(item -> item.getTitle().equalsIgnoreCase(toDoItemChanged.getTitle())).findFirst().isEmpty()) {
                    currentItem.setTitle(toDoItemChanged.getTitle());
                }
                currentItem.setDescription(toDoItemChanged.getDescription());
                currentItem.setDone(toDoItemChanged.isDone());
                toDoRepository.add(i, currentItem);
            }
        }
        return getToDoList();
    }

    public List<ToDoItem> deleteItem(String id) {
        for (int i = 0; i < toDoRepository.getToDoList().size(); i++) {
            ToDoItem currentItem = toDoRepository.getToDoList().get(i);
            if (currentItem.getId().equals(id)) {
                toDoRepository.delete(i);
            }
        }
        return getToDoList();
    }
}