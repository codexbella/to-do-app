package de.codexbella;

import java.util.List;

public class ToDoService {
    private ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepo) {
        this.toDoRepository = toDoRepo;
    }

    public List<ToDo> getToDos() {
        return toDoRepository.getToDoListe();
    }

    public List<ToDo> getMatchingToDoItems(String searchTerm) {
        return toDoRepository.getMatchingToDoItems(searchTerm);
    }

    public void setAsDone(ToDo toDo) {
        toDo.setDone(true);
    }

    @Override
    public String toString() {
        return "ToDoService{" +
                "toDoRepo=" + toDoRepository +
                '}';
    }
}
