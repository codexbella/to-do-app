package de.codexbella;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public List<ToDoItem> getToDoList(String username) {
        List<ToDoItem> toDoItemList = toDoRepository.findAllByUsername(username);
        //List<ToDoItem> toDoItemList = toDoRepository.findAll().stream()
        //      .filter(item -> item.getUsername().equalsIgnoreCase(username)).toList();
        Stream<ToDoItem> notDone = toDoItemList.stream().filter(item -> !item.isDone());
        Stream<ToDoItem> done = toDoItemList.stream().filter(item -> item.isDone());
        return Stream.concat(notDone, done).toList();
    }

    public List<ToDoItem> getMatchingToDoItems(String searchTerm, String username) {
        return getToDoList(username).stream()
                .filter(item -> item.getUsername().equalsIgnoreCase(username)
                      && item.getTitle().toLowerCase().contains(searchTerm.toLowerCase())).toList();
    }

    public List<ToDoItem> getAllItemsNotDone(String username) {
        return toDoRepository.findAll().stream()
              .filter(item -> item.getUsername().equalsIgnoreCase(username) && !item.isDone()).toList();
    }

    public List<ToDoItem> addItem(ToDoItem toDoItem, String username) {
        toDoItem.setUsername(username);
        if (toDoRepository.findByTitleIgnoreCase(toDoItem.getTitle()).isEmpty()) {
            toDoRepository.save(toDoItem);
        }
        return getToDoList(username);
    }

    public List<ToDoItem> changeItem(ToDoItem toDoItemChanged, String username) {
        Optional<ToDoItem> toDoItemOptional = toDoRepository.findById(toDoItemChanged.getId());
        if (toDoItemOptional.isPresent() && toDoItemOptional.get().getUsername().equalsIgnoreCase(username)) {
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
        }
        return getToDoList(username);
    }

    public List<ToDoItem> deleteItem(String id, String username) {
        Optional<ToDoItem> toDoItemOptional = toDoRepository.findById(id);
        if (toDoItemOptional.isPresent() && toDoItemOptional.get().getUsername().equalsIgnoreCase(username)) {
            toDoRepository.deleteById(id);
        }
        return getToDoList(username);
    }
}