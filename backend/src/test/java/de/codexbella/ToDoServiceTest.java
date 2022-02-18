package de.codexbella;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class ToDoServiceTest {
    @Test
    void shouldReturnListOfToDos() {
        ToDo testToDo1 = new ToDo("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDo testToDo2 = new ToDo("Fenster putzen");
        ToDo testToDo3 = new ToDo("Obi-Einkauf");

        Set<ToDo> testToDos = Set.of(testToDo1, testToDo2, testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        Assertions.assertEquals(testToDos, testToDoService.getToDos());
    }

}