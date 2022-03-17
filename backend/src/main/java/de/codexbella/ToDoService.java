package de.codexbella;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ToDoService {
    private final ToDoRepository toDoRepository;

    @Override
    public String toString() {
        return "ToDoService{" +
                "toDoRepo=" + toDoRepository +
                '}';
    }

    public List<ToDoItem> getToDoList() {
        List<ToDoItem> toDoItemList = toDoRepository.findAll();
        Stream<ToDoItem> notdone = toDoItemList.stream().filter(item -> !item.isDone());
        Stream<ToDoItem> done = toDoItemList.stream().filter(item -> item.isDone());
        return Stream.concat(notdone, done).toList();
    }

    public List<ToDoItem> getMatchingToDoItems(String searchTerm) {
        return getToDoList().stream()
                .filter(todo -> todo.getTitle().toLowerCase().contains(searchTerm.toLowerCase())).toList();
    }

    public List<ToDoItem> getAllItemsNotDone() {
        return toDoRepository.findAll().stream().filter(item -> !item.isDone()).toList();
    }

    public List<ToDoItem> addItem(ToDoItem toDoItem) {
        if (toDoRepository.findByTitleIgnoreCase(toDoItem.getTitle()).isEmpty()) {
            toDoRepository.save(toDoItem);
        }
        return getToDoList();
    }

    public List<ToDoItem> changeItem(ToDoItem toDoItemChanged) {
        List<ToDoItem> listBefore = toDoRepository.findAll();
        for (ToDoItem currentItem : listBefore) {
            if (currentItem.getId().equals(toDoItemChanged.getId())) {
                if (toDoRepository.findByTitleIgnoreCase(toDoItemChanged.getTitle()).isEmpty()) {
                    currentItem.setTitle(toDoItemChanged.getTitle());
                }
                currentItem.setDescription(toDoItemChanged.getDescription());
                currentItem.setDone(toDoItemChanged.isDone());
                toDoRepository.save(currentItem);
            }
        }
        return getToDoList();
    }

    public List<ToDoItem> deleteItem(String id) {
        toDoRepository.deleteById(id);
        return getToDoList();
    }
}