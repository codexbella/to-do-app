package de.codexbella;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ToDoServiceTest {
    @Test
    void shouldReturnCompleteListOfToDoItems() {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen");
        ToDoItem testToDo3 = new ToDoItem("Impfung");

        List<ToDoItem> testToDos = List.of(testToDo1, testToDo2, testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        List<ToDoItem> expectedToDoList = List.of(testToDo1, testToDo2, testToDo3);

        assertEquals(expectedToDoList, testToDoService.getToDoList());
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

        testToDo3.setDone(true);
        testToDoService.changeItem(testToDo3);

        ToDoItem resultingToDo = testToDoService.getToDoList().stream().findFirst().get();

        assertTrue(resultingToDo.isDone());
    }
    @Test
    void shouldSetToDoItemAsNotDone() {
        ToDoItem testToDo3 = new ToDoItem("Impfung", true);

        List<ToDoItem> testToDoItems = new ArrayList<>();
        testToDoItems.add(testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDoItems);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        testToDo3.setDone(false);
        testToDoService.changeItem(testToDo3);

        ToDoItem resultingToDo = testToDoService.getToDoList().stream().findFirst().get();

        assertFalse(resultingToDo.isDone());
    }
    @Test
    void shouldReturnAllToDoItemsThatAreNotDone() {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen", true);
        ToDoItem testToDo3 = new ToDoItem("Impfung");

        List<ToDoItem> testToDos = List.of(testToDo1, testToDo2, testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        List<ToDoItem> expectedToDoList = List.of(testToDo1, testToDo3);

        assertEquals(expectedToDoList, testToDoService.getAllItemsNotDone());
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

        List<ToDoItem> expectedToDoList = List.of(testToDo1, testToDo2, testToDo3);

        List<ToDoItem> actualToDoList = testToDoService.getToDoList();

        assertEquals(expectedToDoList, actualToDoList);
    }
    @Test
    void shouldNotAddNewToDoItemBecauseTitleAlreadyInList() {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen", true);
        ToDoItem testToDo3 = new ToDoItem("Impfung");

        List<ToDoItem> testToDos = new ArrayList<>();
        testToDos.add(testToDo1);
        testToDos.add(testToDo2);
        testToDos.add(testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        ToDoItem testToDo4 = new ToDoItem("IMpfung");
        testToDoService.addItem(testToDo4);

        List<ToDoItem> expectedToDoList = List.of(testToDo1, testToDo2, testToDo3);

        assertEquals(expectedToDoList, testToDoService.getToDoList());
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

        testToDo3.setTitle("Masern-Impfung");
        testToDoService.changeItem(testToDo3);

        assertEquals("Masern-Impfung", testToDoService.getMatchingToDoItems("Impfung").get(0).getTitle());
    }
    @Test
    void shouldNotChangeItemTitleBecauseDuplicateTitle() {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen", true);
        ToDoItem testToDo3 = new ToDoItem("Impfung");

        List<ToDoItem> testToDos = new ArrayList<>();
        testToDos.add(testToDo1);
        testToDos.add(testToDo2);
        testToDos.add(testToDo3);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        ToDoItem testToDo3Duplicate = new ToDoItem("Fenster Putzen", testToDo3.getDescription(), testToDo3.isDone());
        testToDo3Duplicate.setId(testToDo3.getId());
        List<ToDoItem> listAfterAttemptedTitleChange = testToDoService.changeItem(testToDo3);

        List<ToDoItem> expectedToDoList = List.of(testToDo1, testToDo2, testToDo3);

        assertEquals(expectedToDoList, listAfterAttemptedTitleChange);
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

        testToDo3.setDescription("Keuchhusten-Tetanus-Diphterie");
        testToDoService.changeItem(testToDo3);

        assertEquals("Keuchhusten-Tetanus-Diphterie", testToDoService.getMatchingToDoItems("Impfung").get(0).getDescription());
    }
    @Test
    void shouldNotWorkBecauseNoSuchItemToChangeDescriptionOf() {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen", true);
        ToDoItem testToDo3 = new ToDoItem("Impfung", "Masern-Mumps-Röteln");

        List<ToDoItem> testToDos = new ArrayList<>();
        testToDos.add(testToDo1);
        testToDos.add(testToDo2);

        ToDoRepository testToDoRepo = new ToDoRepository(testToDos);
        ToDoService testToDoService = new ToDoService(testToDoRepo);

        ToDoItem testToDo3changed = new ToDoItem("Impfung", "");
        testToDo3changed.setId(testToDo3.getId());
        System.out.println(testToDos);

        testToDoService.changeItem(testToDo3changed);
        System.out.println(testToDos);


        List<ToDoItem> expectedToDoList = List.of(testToDo1, testToDo2);
        System.out.println(expectedToDoList);

        assertEquals(expectedToDoList, testToDoService.getToDoList());
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

        testToDoService.deleteItem(testToDo2.getId());

        List<ToDoItem> expectedToDoList = List.of(testToDo1, testToDo3);

        assertEquals(expectedToDoList, testToDoService.getToDoList());
    }
    @Test
    void shouldAddNewToDoItemWithMock() {
        ToDoItem testToDo3 = new ToDoItem("Impfung");

        ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
        ToDoService testToDoService = new ToDoService(mockToDoRepo);

        testToDoService.addItem(testToDo3);

        verify(mockToDoRepo).add(testToDo3);
    }
    @Test
    void shouldReturnMatchingToDoItemsByTitleWithMock() {
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen");

        ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
        ToDoService testToDoService = new ToDoService(mockToDoRepo);
        when(testToDoService.getMatchingToDoItems("fenster")).thenReturn(List.of(testToDo2));

        assertEquals(List.of(testToDo2), testToDoService.getMatchingToDoItems("fenster"));
    }
}