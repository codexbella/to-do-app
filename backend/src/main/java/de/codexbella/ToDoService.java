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
      Stream<ToDoItem> notDone = toDoRepository.findAllByUsernameAndDone(username, false).stream();
      Stream<ToDoItem> done = toDoRepository.findAllByUsernameAndDone(username, true).stream();
      return Stream.concat(notDone, done).toList();
   }

   public List<ToDoItem> getMatchingToDoItems(String searchTerm, String username) {
      return getToDoList(username).stream()
            .filter(item -> item.getTitle().toLowerCase().contains(searchTerm.toLowerCase())).toList();
   }

   public List<ToDoItem> getAllItemsNotDone(String username) {
      return getToDoList(username).stream()
            .filter(item -> !item.isDone()).toList();
   }

   public List<ToDoItem> addItem(ToDoItem toDoItem, String username) {
      if (toDoRepository.findByTitleIgnoreCase(toDoItem.getTitle()).isEmpty()) {
         toDoItem.setUsername(username);
         toDoRepository.save(toDoItem);
      }
      return getToDoList(username);
   }

   public List<ToDoItem> changeItem(ToDoItem toDoItemChanged, String username) {
      Optional<ToDoItem> toDoItemOptional = toDoRepository.findById(toDoItemChanged.getId());
      if (toDoItemOptional.isPresent() && toDoItemOptional.get().getUsername().equalsIgnoreCase(username)) {
         ToDoItem toDoItem = toDoItemOptional.get();
         if (toDoRepository.findByTitleIgnoreCase(toDoItemChanged.getTitle()).isEmpty()) {
            toDoItem.setTitle(toDoItemChanged.getTitle());
         }
         toDoItem.setDescription(toDoItemChanged.getDescription());
         toDoItem.setDone(toDoItemChanged.isDone());
         toDoRepository.save(toDoItem);
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