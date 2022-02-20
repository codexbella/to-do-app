package de.codexbella;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todoapp")
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
    public boolean addToDoItem(@RequestBody ToDoItem toDoItemToAdd) {
        return toDoService.addItem(toDoItemToAdd);
    }

    @PostMapping("/setasdone")
    public boolean setItemAsDone(@RequestBody ToDoItem toDoItem) {
        return toDoService.setAsDone(toDoItem);
    }
    @PostMapping("/setasnotdone")
    public boolean setItemAsNotDone(@RequestBody ToDoItem toDoItem) {
        return toDoService.setAsNotDone(toDoItem);
    }
    @PostMapping("/settitle/{title}")
    public boolean setTitle(@RequestBody ToDoItem toDoItem, @PathVariable String title) {
        return toDoService.setTitle(toDoItem, title);
    }
    @PostMapping("/setdescription/{description}")
    public boolean setDescription(@RequestBody ToDoItem toDoItem, @PathVariable String description) {
        return toDoService.setDescription(toDoItem, description);
    }
    @PostMapping("/delete")
    public boolean deleteItem(@RequestBody ToDoItem toDoItem) {
        return toDoService.deleteItem(toDoItem);
    }
}
