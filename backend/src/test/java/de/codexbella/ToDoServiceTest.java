package de.codexbella;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ToDoServiceTest {
    @Test
    void shouldReturnListOfToDos() {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen");
        ToDoItem testToDo3 = new ToDoItem("Impfung");

        List<ToDoItem> testToDos = List.of(testToDo1, testToDo2, testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        assertEquals(testToDos, testToDoService.getToDos());
    }
    @Test
    void shouldReturnMatchingToDos() {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen");
        ToDoItem testToDo3 = new ToDoItem("Impfung");

        List<ToDoItem> testToDos = List.of(testToDo1, testToDo2, testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        assertEquals(List.of(testToDo2), testToDoService.getMatchingToDoItems("fenster"));
    }
    @Test
    void shouldReturnEmptyListBecauseToDoNotInList() {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen");
        ToDoItem testToDo3 = new ToDoItem("Impfung");

        List<ToDoItem> testToDos = List.of(testToDo1, testToDo2, testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        List<ToDoItem> emptyList = List.of();
        List<ToDoItem> actualList = testToDoService.getMatchingToDoItems("aufräumen");

        assertEquals(emptyList, actualList);
    }
    @Test
    void shouldSetToDoAsDone() {
        ToDoItem testToDo3 = new ToDoItem("Impfung");

        List<ToDoItem> testToDos = List.of(testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        testToDoService.setAsDone(testToDo3);

        Optional<ToDoItem> resultingToDo = testToDoService.getToDos().stream().findFirst();

        assertTrue(resultingToDo.get().isDone());
    }
    @Test
    void shouldReturnAllItemsThatAreNotDone() {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen", true);
        ToDoItem testToDo3 = new ToDoItem("Impfung");

        List<ToDoItem> testToDos = List.of(testToDo1, testToDo2, testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        assertEquals(List.of(testToDo1, testToDo3), testToDoService.getAllItemsNotDone());
    }
}