package de.codexbella;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ToDoServiceTest {
    @Test
    void shouldReturnListOfToDos() {
        ToDo testToDo1 = new ToDo("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDo testToDo2 = new ToDo("Fenster putzen");
        ToDo testToDo3 = new ToDo("Impfung");

        List<ToDo> testToDos = List.of(testToDo1, testToDo2, testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        assertEquals(testToDos, testToDoService.getToDos());
    }
    @Test
    void shouldReturnMatchingToDos() {
        ToDo testToDo1 = new ToDo("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDo testToDo2 = new ToDo("Fenster putzen");
        ToDo testToDo3 = new ToDo("Impfung");

        List<ToDo> testToDos = List.of(testToDo1, testToDo2, testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        assertEquals(List.of(testToDo2), testToDoService.getMatchingToDoItems("fenster"));
    }
    @Test
    void shouldReturnEmptyListBecauseToDoNotInList() {
        ToDo testToDo1 = new ToDo("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDo testToDo2 = new ToDo("Fenster putzen");
        ToDo testToDo3 = new ToDo("Impfung");

        List<ToDo> testToDos = List.of(testToDo1, testToDo2, testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        List<ToDo> emptyList = List.of();
        List<ToDo> actualList = testToDoService.getMatchingToDoItems("aufräumen");

        assertEquals(emptyList, actualList);
    }
    @Test
    void shouldSetToDoAsDone() {
        ToDo testToDo3 = new ToDo("Impfung");

        List<ToDo> testToDos = List.of(testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        testToDoService.setAsDone(testToDo3);

        Optional<ToDo> resultingToDo = testToDoService.getToDos().stream().findFirst();

        assertTrue(resultingToDo.get().isDone());
    }

}