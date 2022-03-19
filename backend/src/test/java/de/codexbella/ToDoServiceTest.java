package de.codexbella;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ToDoServiceTest {
    /*
    @Test
    void shouldCallFindAllWithMock() {
        ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
        ToDoService testToDoService = new ToDoService(mockToDoRepo);

        testToDoService.getToDoList();

        verify(mockToDoRepo).findAll();
    }
    @Test
    void shouldAddNewToDoItemWithMock() {
        ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
        ToDoService testToDoService = new ToDoService(mockToDoRepo);

        ToDoItem testToDo1 = new ToDoItem("Einkauf");

        testToDoService.addItem(testToDo1);

        verify(mockToDoRepo).save(testToDo1);
    }
    @Test
    void shouldNotAddItemWithMock() {
        ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
        ToDoService testToDoService = new ToDoService(mockToDoRepo);

        ToDoItem testToDo1 = new ToDoItem("Einkauf");
        testToDo1.setId("id-placeholder1");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen");
        testToDo2.setId("id-placeholder2");
        ToDoItem testToDo3 = new ToDoItem("Impfung");
        testToDo3.setId("id-placeholder3");

        ToDoItem testToDoExtra = new ToDoItem("imPFung");
        testToDoExtra.setId("id-placeholder-extra");

        when(mockToDoRepo.findAll()).thenReturn(List.of(testToDo1, testToDo2, testToDo3));
        when(mockToDoRepo.findByTitleIgnoreCase("imPFung")).thenReturn(Optional.of(testToDo3));

        testToDoService.addItem(testToDoExtra);

        verify(mockToDoRepo).findAll();
        verify(mockToDoRepo).findByTitleIgnoreCase("imPFung");
        verify(mockToDoRepo, Mockito.never()).save(any());
        verifyNoMoreInteractions(mockToDoRepo);
    }
    @Test
    void shouldReturnMatchingToDoItemsByTitleWithMock() {
        ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
        ToDoService testToDoService = new ToDoService(mockToDoRepo);

        ToDoItem testToDo1 = new ToDoItem("Einkauf");
        testToDo1.setId("id-placeholder1");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen");
        testToDo2.setId("id-placeholder2");
        ToDoItem testToDo3 = new ToDoItem("Impfung");
        testToDo3.setId("id-placeholder3");

        when(mockToDoRepo.findAll()).thenReturn(List.of(testToDo1, testToDo2, testToDo3));

        assertEquals(List.of(testToDo2), testToDoService.getMatchingToDoItems("fenster"));
        verify(mockToDoRepo).findAll();
        verifyNoMoreInteractions(mockToDoRepo);
    }
    @Test
    void shouldReturnAllItemsNotDoneWithMock() {
        ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
        ToDoService testToDoService = new ToDoService(mockToDoRepo);

        ToDoItem testToDo1 = new ToDoItem("Einkauf");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen");
        testToDo2.setDone(true);
        ToDoItem testToDo3 = new ToDoItem("Impfung");
        ToDoItem testToDo4 = new ToDoItem("Pflanzen gießen");

        when(mockToDoRepo.findAll()).thenReturn(List.of(testToDo1, testToDo2, testToDo3, testToDo4));

        assertEquals(List.of(testToDo1, testToDo3, testToDo4), testToDoService.getAllItemsNotDone());
        verify(mockToDoRepo).findAll();
        verifyNoMoreInteractions(mockToDoRepo);
    }
    @Test
    void shouldChangeItemWithMock() {
        ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
        ToDoService testToDoService = new ToDoService(mockToDoRepo);

        ToDoItem testToDo1 = new ToDoItem("Einkauf");
        testToDo1.setId("id-placeholder");
        ToDoItem testToDo1Changed = new ToDoItem("Obi-Einkauf");
        testToDo1Changed.setId("id-placeholder");

        when(mockToDoRepo.findAll()).thenReturn(List.of(testToDo1));

        testToDoService.changeItem(testToDo1Changed);

        verify(mockToDoRepo).save(testToDo1Changed);
    }
    @Test
    void shouldNotChangeItemWithMock() {
        ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
        ToDoService testToDoService = new ToDoService(mockToDoRepo);

        ToDoItem testToDo1 = new ToDoItem("Einkauf");
        testToDo1.setId("id-placeholder");
        ToDoItem testToDo1Changed = new ToDoItem("Obi-Einkauf");
        testToDo1Changed.setId(testToDo1.getId());

        ToDoItem testToDoExtra = new ToDoItem("obi-einKAuf");
        testToDoExtra.setId("id-placeholder-extra");

        when(mockToDoRepo.findAll()).thenReturn(List.of(testToDo1, testToDoExtra));

        testToDoService.changeItem(testToDo1Changed);

        verify(mockToDoRepo).findByTitleIgnoreCase("Obi-Einkauf");
        verify(mockToDoRepo).save(testToDo1);
    }
    @Test
    void shouldDeleteItemWithMock() {
        ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
        ToDoService testToDoService = new ToDoService(mockToDoRepo);

        testToDoService.deleteItem("id-placeholder");

        verify(mockToDoRepo).deleteById("id-placeholder");
    }
    */
}