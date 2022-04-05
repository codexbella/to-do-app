package de.codexbella;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ToDoItem>> addToDoItem(@RequestBody ToDoItem toDoItemToAdd, Principal principal) {
        try {
            List<ToDoItem> toDoList = toDoService.addItem(toDoItemToAdd, principal.getName());
            return new ResponseEntity<>(toDoList, HttpStatus.CREATED);
        } catch (Exception e) {
            List<ToDoItem> toDoList = toDoService.getToDoList(principal.getName());
            return new ResponseEntity<>(toDoList, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<ToDoItem>> changeItem(@RequestBody ToDoItem toDoItemChanged, Principal principal) {
        try {
            List<ToDoItem> toDoList = toDoService.changeItem(toDoItemChanged, principal.getName());
            return new ResponseEntity<>(toDoList, HttpStatus.OK);
        } catch (Exception e) {
            List<ToDoItem> toDoList = toDoService.getToDoList(principal.getName());
            return new ResponseEntity<>(toDoList, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public List<ToDoItem> deleteItem(@PathVariable String id, Principal principal) {
        return toDoService.deleteItem(id, principal.getName());
    }
}
