package de.codexbella;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todoitems")
@CrossOrigin
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

    @PostMapping("/setasdone")
    public List<ToDoItem> setItemAsDone(@RequestBody ToDoItem toDoItem) {
        return toDoService.setAsDone(toDoItem);
    }
    @PostMapping("/setasnotdone")
    public List<ToDoItem> setItemAsNotDone(@RequestBody ToDoItem toDoItem) {
        return toDoService.setAsNotDone(toDoItem);
    }
    @PostMapping("/settitle")
    public List<ToDoItem> setTitle(@RequestBody ToDoItem[] toDoItems) {
        return toDoService.setTitle(toDoItems[0], toDoItems[1].getTitle());
    }
    @PostMapping("/setdescription")
    public List<ToDoItem> setDescription(@RequestBody ToDoItem[] toDoItems) {
        return toDoService.setDescription(toDoItems[0], toDoItems[1].getDescription());
    }
    @PostMapping("/delete")
    public List<ToDoItem> deleteItem(@RequestBody ToDoItem toDoItem) {
        return toDoService.deleteItem(toDoItem);
    }
}
