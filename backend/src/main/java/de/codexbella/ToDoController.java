package de.codexbella;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todoitems")
@RequiredArgsConstructor
public class ToDoController {
    private ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping("/getall")
    public List<ToDoItem> getToDoList() {
        return toDoService.getToDoList();
    }
    @GetMapping("/{searchTerm}")
    public List<ToDoItem> getMatchingToDoItems(@PathVariable String searchTerm) {
        return toDoService.getMatchingToDoItems(searchTerm);
    }
    @GetMapping("/getallnotdone")
    public List<ToDoItem> getAllItemsNotDone() {
        return toDoService.getAllItemsNotDone();
    }

    @PostMapping("/additem")
    public List<ToDoItem> addToDoItem(@RequestBody ToDoItem toDoItemToAdd) {
        return toDoService.addItem(toDoItemToAdd);
    }

    @PutMapping("/{id}")
    public List<ToDoItem> changeItem(@RequestBody ToDoItem toDoItemChanged) {
        return toDoService.changeItem(toDoItemChanged);
    }

    @DeleteMapping("/{id}")
    public List<ToDoItem> deleteItem(@PathVariable String id) {
        return toDoService.deleteItem(id);
    }
}
