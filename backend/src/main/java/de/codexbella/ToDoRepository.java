package de.codexbella;

import java.util.List;
import java.util.stream.Collectors;

public class ToDoRepository {
    private List<ToDo> toDoListe;

    public ToDoRepository(List<ToDo> toDos) {
        this.toDoListe = toDos;
    }

    public List<ToDo> getToDoListe() {
        return toDoListe;
    }

    @Override
    public String toString() {
        return "ToDoRepository{" +
                "toDoList=" + toDoListe +
                '}';
    }

    public List<ToDo> getMatchingToDoItems(String searchTerm) {
        String searchTermLowercase = searchTerm.toLowerCase();
        List<ToDo> toDoList = this.toDoListe;
        List<ToDo> toDosWithSearchTerm = toDoList.stream()
                .filter(todo -> todo.getTitle().toLowerCase().contains(searchTermLowercase))
                .collect(Collectors.toList());
        return toDosWithSearchTerm;
    }
}
