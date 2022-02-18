package de.codexbella;

import java.util.Set;

public class ToDoService {
    private ToDoRepository toDoRepo;

    public ToDoService(ToDoRepository toDoRepo) {
        this.toDoRepo = toDoRepo;
    }

    public Set<ToDo> getToDos() {
        return toDoRepo.getToDos();
    }
}
