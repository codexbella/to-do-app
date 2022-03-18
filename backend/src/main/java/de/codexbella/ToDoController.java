package de.codexbella;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/todoitems")
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoService toDoService;

    @GetMapping("/getall")
    public List<ToDoItem> getToDoList(Principal principal) {
        return toDoService.getToDoList(principal.getName());
    }
    @GetMapping("/{searchTerm}")
    public List<ToDoItem> getMatchingToDoItems(@PathVariable String searchTerm, Principal principal) {
        return toDoService.getMatchingToDoItems(searchTerm, principal.getName());
    }
    @GetMapping("/getallnotdone")
    public List<ToDoItem> getAllItemsNotDone(Principal principal) {
        return toDoService.getAllItemsNotDone(principal.getName());
    }

    @PostMapping("/additem")
    public List<ToDoItem> addToDoItem(@RequestBody ToDoItem toDoItemToAdd, Principal principal) {
        return toDoService.addItem(toDoItemToAdd, principal.getName());
    }

    @PutMapping("/{id}")
    public List<ToDoItem> changeItem(@RequestBody ToDoItem toDoItemChanged, Principal principal) {
        return toDoService.changeItem(toDoItemChanged, principal.getName());
    }

    @DeleteMapping("/{id}")
    public List<ToDoItem> deleteItem(@PathVariable String id, Principal principal) {
        return toDoService.deleteItem(id, principal.getName());
    }
}
