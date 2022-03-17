package de.codexbella;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ToDoServiceTest {
    @Test
    void shouldCallFindAllWithMock() {
        ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
        ToDoService testToDoService = new ToDoService(mockToDoRepo);

        testToDoService.getToDoList();

        verify(mockToDoRepo).findAll();
    }
    @Test
    void shouldAddNewToDoItemWithMock() {
        ToDoItem testToDo3 = new ToDoItem("Impfung");

        ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
        ToDoService testToDoService = new ToDoService(mockToDoRepo);

        testToDoService.addItem(testToDo3);

        verify(mockToDoRepo).save(testToDo3);
    }
    @Test
    void shouldNotAddItemWithMock() {
        ToDoItem testToDo1 = new ToDoItem("Einkauf");
        testToDo1.setId("id-placeholder1");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen");
        testToDo2.setId("id-placeholder2");
        ToDoItem testToDo3 = new ToDoItem("Impfung");
        testToDo3.setId("id-placeholder3");

        ToDoItem testToDoExtra = new ToDoItem("imPFung");
        testToDoExtra.setId("id-placeholder-extra");

        ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
        ToDoService testToDoService = new ToDoService(mockToDoRepo);

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
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen");

        ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
        ToDoService testToDoService = new ToDoService(mockToDoRepo);
        when(mockToDoRepo.findAll()).thenReturn(List.of(testToDo2));

        assertEquals(List.of(testToDo2), testToDoService.getMatchingToDoItems("fenster"));
        verify(mockToDoRepo).findAll();
        verifyNoMoreInteractions(mockToDoRepo);
    }
    @Test
    void shouldReturnAllItemsNotDoneWithMock() {
        ToDoItem testToDo1 = new ToDoItem("Einkauf");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen");
        testToDo2.setDone(true);
        ToDoItem testToDo3 = new ToDoItem("Impfung");
        ToDoItem testToDo4 = new ToDoItem("Pflanzen gießen");

        ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
        ToDoService testToDoService = new ToDoService(mockToDoRepo);
        when(mockToDoRepo.findAll()).thenReturn(List.of(testToDo1, testToDo2, testToDo3, testToDo4));

        assertEquals(List.of(testToDo1, testToDo3, testToDo4), testToDoService.getAllItemsNotDone());
        verify(mockToDoRepo).findAll();
        verifyNoMoreInteractions(mockToDoRepo);
    }
    @Test
    void shouldChangeItemWithMock() {
        ToDoItem testToDo3 = new ToDoItem("Impfung");
        testToDo3.setId("id-placeholder");
        ToDoItem testToDo3Changed = new ToDoItem("Masern-Impfung");
        testToDo3Changed.setId("id-placeholder");

        ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
        ToDoService testToDoService = new ToDoService(mockToDoRepo);

        when(mockToDoRepo.findAll()).thenReturn(List.of(testToDo3));

        testToDoService.changeItem(testToDo3Changed);

        verify(mockToDoRepo).save(testToDo3Changed);
    }
    @Test
    void shouldNotChangeItemWithMock() {
        ToDoItem testToDo3 = new ToDoItem("Impfung");
        testToDo3.setId("id-placeholder3");
        ToDoItem testToDoExtra = new ToDoItem("Masern-Impfung");
        testToDoExtra.setId("id-placeholder");
        ToDoItem testToDo3x = new ToDoItem("Masern-Impfung");
        testToDo3x.setId("id-placeholder3X");

        ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
        ToDoService testToDoService = new ToDoService(mockToDoRepo);

        when(mockToDoRepo.findAll()).thenReturn(List.of(testToDo3, testToDo3x));
        when(mockToDoRepo.findByTitleIgnoreCase("Masern-Impfung")).thenReturn(Optional.of(testToDo3x));

        testToDoService.changeItem(testToDoExtra);

        verify(mockToDoRepo).save(testToDo3); // Object with old title is saved, because new title already exists.
    }
    @Test
    void shouldDeleteItemWithMock() {
        ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
        ToDoService testToDoService = new ToDoService(mockToDoRepo);

        testToDoService.deleteItem("id-placeholder");

        verify(mockToDoRepo).deleteById("id-placeholder");
    }
}