package de.codexbella;

import java.util.Set;

public class ToDoRepository {
    private Set<ToDo> toDos;

    public ToDoRepository(Set<ToDo> toDos) {
        this.toDos = toDos;
    }
}
