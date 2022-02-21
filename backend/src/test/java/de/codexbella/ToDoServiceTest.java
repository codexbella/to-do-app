package de.codexbella;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ToDoServiceTest {
    @Test
    void shouldReturnCompleteListOfToDoItems() {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen");
        ToDoItem testToDo3 = new ToDoItem("Impfung");

        List<ToDoItem> testToDos = List.of(testToDo1, testToDo2, testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        assertEquals(testToDos, testToDoService.getToDoList());
    }
    @Test
    void shouldReturnMatchingToDoItemsByTitle() {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen");
        ToDoItem testToDo3 = new ToDoItem("Impfung");

        List<ToDoItem> testToDos = List.of(testToDo1, testToDo2, testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        assertEquals(List.of(testToDo2), testToDoService.getMatchingToDoItems("fenster"));
    }
    @Test
    void shouldReturnEmptyListBecauseToDoItemNotInList() {
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
    void shouldSetToDoItemAsDone() {
        ToDoItem testToDo3 = new ToDoItem("Impfung");

        List<ToDoItem> testToDoItems = new ArrayList<>();
        testToDoItems.add(testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDoItems);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        testToDoService.setAsDone(testToDo3);

        Optional<ToDoItem> resultingToDo = testToDoService.getToDoList().stream().findFirst();

        assertTrue(resultingToDo.get().isDone());
    }
    @Test
    void shouldSetToDoItemAsNotDone() {
        ToDoItem testToDo3 = new ToDoItem("Impfung", true);

        List<ToDoItem> testToDoItems = new ArrayList<>();
        testToDoItems.add(testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDoItems);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        testToDoService.setAsNotDone(testToDo3);

        Optional<ToDoItem> resultingToDo = testToDoService.getToDoList().stream().findFirst();

        assertFalse(resultingToDo.get().isDone());
    }
    @Test
    void shouldReturnAllToDoItemsThatAreNotDone() {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen", true);
        ToDoItem testToDo3 = new ToDoItem("Impfung");

        List<ToDoItem> testToDos = List.of(testToDo1, testToDo2, testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        assertEquals(List.of(testToDo1, testToDo3), testToDoService.getAllItemsNotDone());
    }
    @Test
    void shouldAddNewToDoItem() {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen", true);
        ToDoItem testToDo3 = new ToDoItem("Impfung");

        List<ToDoItem> testToDoItems = new ArrayList<>();
        testToDoItems.add(testToDo1);
        testToDoItems.add(testToDo2);


        ToDoRepository testToDoRepo = new ToDoRepository(testToDoItems);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        testToDoService.addItem(testToDo3);

        List<ToDoItem> expectedToDoList = new ArrayList<>();
        expectedToDoList.add(testToDo1);
        expectedToDoList.add(testToDo2);
        expectedToDoList.add(testToDo3);

        List<ToDoItem> actualToDoList = testToDoService.getToDoList();

        assertEquals(expectedToDoList, actualToDoList);
    }
    @Test
    void shouldNotAddNewToDoItemBecauseAlreadyInList() {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen", true);
        ToDoItem testToDo3 = new ToDoItem("Impfung");

        List<ToDoItem> testToDos = new ArrayList<>();
        testToDos.add(testToDo1);
        testToDos.add(testToDo2);
        testToDos.add(testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        testToDoService.addItem(testToDo3);

        assertEquals(testToDos, testToDoService.getToDoList());
    }
    @Test
    void shouldChangeItemTitle() {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen", true);
        ToDoItem testToDo3 = new ToDoItem("Impfung");

        List<ToDoItem> testToDos = new ArrayList<>();
        testToDos.add(testToDo1);
        testToDos.add(testToDo2);
        testToDos.add(testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        testToDoService.setTitle(testToDo3, "Masern-Impfung");

        assertEquals("Masern-Impfung", testToDoService.getMatchingToDoItems("Impfung").get(0).getTitle());
    }
    @Test
    void shouldNotChangeItemTitleBecauseDuplicate() {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen", true);
        ToDoItem testToDo3 = new ToDoItem("Impfung");

        List<ToDoItem> testToDos = new ArrayList<>();
        testToDos.add(testToDo1);
        testToDos.add(testToDo2);
        testToDos.add(testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        List<ToDoItem> setTitle = testToDoService.setTitle(testToDo3, "Fenster putzen");

        assertEquals("Impfung", testToDoService.getMatchingToDoItems("impf").get(0).getTitle());
    }
    @Test
    void shouldChangeItemDescription() {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen", true);
        ToDoItem testToDo3 = new ToDoItem("Impfung", "Masern-Mumps-Röteln");

        List<ToDoItem> testToDos = new ArrayList<>();
        testToDos.add(testToDo1);
        testToDos.add(testToDo2);
        testToDos.add(testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        testToDoService.setDescription(testToDo3, "Keuchhusten-Tetanus-Diphterie");

        assertEquals("Keuchhusten-Tetanus-Diphterie", testToDoService.getMatchingToDoItems("Impfung").get(0).getDescription());
    }
    @Test
    void shouldReturnFalseBecauseNoSuchItemToSetDescriptionOf() {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen", true);
        ToDoItem testToDo3 = new ToDoItem("Impfung", "Masern-Mumps-Röteln");

        List<ToDoItem> testToDos = new ArrayList<>();
        testToDos.add(testToDo1);
        testToDos.add(testToDo2);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        List<ToDoItem> descriptionSet = testToDoService.setDescription(testToDo3, "Keuchhusten-Tetanus-Diphterie");

        assertEquals(testToDos, testToDoService.getToDoList());
    }
    @Test
    void shouldDeleteItem() {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen", true);
        ToDoItem testToDo3 = new ToDoItem("Impfung", "Masern-Mumps-Röteln");

        List<ToDoItem> testToDoItems = new ArrayList<>();
        testToDoItems.add(testToDo1);
        testToDoItems.add(testToDo2);
        testToDoItems.add(testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDoItems);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        testToDoService.deleteItem(testToDo2);

        List<ToDoItem> expected = new ArrayList<>();
        expected.add(testToDo1);
        expected.add(testToDo3);

        assertEquals(expected, testToDoService.getToDoList());
    }
}