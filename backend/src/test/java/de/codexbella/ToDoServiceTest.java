package de.codexbella;

import org.junit.jupiter.api.Assertions;
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
      String username = "whoever";

      testToDoService.getToDoList(username);

      verify(mockToDoRepo).findAllByUsernameAndDone(username, true);
      verify(mockToDoRepo).findAllByUsernameAndDone(username, false);
   }

   @Test
   void shouldAddNewToDoItemWithMock(){
      ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
      ToDoService testToDoService = new ToDoService(mockToDoRepo);
      String username = "whoever";

      ToDoItem testToDo1 = new ToDoItem("Einkauf");

      testToDoService.addItem(testToDo1, username);

      verify(mockToDoRepo).save(testToDo1);
   }

   @Test
   void shouldNotAddItemWithMock(){
      ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
      ToDoService testToDoService = new ToDoService(mockToDoRepo);
      String username = "whoever";

      ToDoItem testToDo1 = new ToDoItem("Einkauf");
      testToDo1.setId("id-placeholder1");
      testToDo1.setUsername(username);
      ToDoItem testToDo2 = new ToDoItem("Fenster putzen");
      testToDo2.setId("id-placeholder2");
      testToDo2.setUsername(username);
      ToDoItem testToDo3 = new ToDoItem("Impfung");
      testToDo3.setId("id-placeholder3");
      testToDo3.setUsername(username);

      ToDoItem testToDoExtra = new ToDoItem("imPFung");
      testToDoExtra.setId("id-placeholder-extra");

      when(mockToDoRepo.findAll()).thenReturn(List.of(testToDo1, testToDo2, testToDo3));
      when(mockToDoRepo.findByTitleIgnoreCase("imPFung")).thenReturn(Optional.of(testToDo3));

      Assertions.assertThrows(IllegalStateException.class, () -> testToDoService.addItem(testToDoExtra, username));

      verify(mockToDoRepo).findByTitleIgnoreCase("imPFung");
      verify(mockToDoRepo, Mockito.never()).save(any());
      verifyNoMoreInteractions(mockToDoRepo);
   }

   @Test
   void shouldReturnMatchingToDoItemsByTitleWithMock() {
      ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
      ToDoService testToDoService = new ToDoService(mockToDoRepo);
      String username = "whoever";

      ToDoItem testToDo1 = new ToDoItem("Einkauf");
      testToDo1.setId("id-placeholder1");
      ToDoItem testToDo2 = new ToDoItem("Fenster putzen");
      testToDo2.setId("id-placeholder2");
      ToDoItem testToDo3 = new ToDoItem("Impfung");
      testToDo3.setId("id-placeholder3");

      when(mockToDoRepo.findAllByUsernameAndDone(username, false)).thenReturn(List.of(testToDo1, testToDo2, testToDo3));

      assertEquals(List.of(testToDo2), testToDoService.getMatchingToDoItems("fenster", username));
      verify(mockToDoRepo).findAllByUsernameAndDone(username, false);
      verify(mockToDoRepo).findAllByUsernameAndDone(username, true);
      verifyNoMoreInteractions(mockToDoRepo);
   }
   @Test
   void shouldReturnAllItemsNotDoneWithMock() {
      ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
      ToDoService testToDoService = new ToDoService(mockToDoRepo);
      String username = "whoever";

      ToDoItem testToDo1 = new ToDoItem("Einkauf");
      ToDoItem testToDo2 = new ToDoItem("Fenster putzen");
      testToDo2.setDone(true);
      ToDoItem testToDo3 = new ToDoItem("Impfung");
      ToDoItem testToDo4 = new ToDoItem("Pflanzen gießen");

      when(mockToDoRepo.findAllByUsernameAndDone(username, false)).thenReturn(List.of(testToDo1, testToDo3, testToDo4));

      assertEquals(List.of(testToDo1, testToDo3, testToDo4), testToDoService.getAllItemsNotDone(username));
      verify(mockToDoRepo).findAllByUsernameAndDone(username, false);
      verify(mockToDoRepo).findAllByUsernameAndDone(username, true);
      verifyNoMoreInteractions(mockToDoRepo);
   }
   @Test
   void shouldChangeItemWithMock() {
      ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
      ToDoService testToDoService = new ToDoService(mockToDoRepo);
      String username = "whoever";

      String id = "id-placeholder";

      ToDoItem testToDo1 = new ToDoItem("Einkauf");
      testToDo1.setId(id);
      testToDo1.setUsername(username);
      ToDoItem testToDo1Changed = new ToDoItem("Obi-Einkauf");
      testToDo1Changed.setId(id);
      testToDo1Changed.setUsername(username);

      when(mockToDoRepo.findById(id)).thenReturn(Optional.of(testToDo1));

      testToDoService.changeItem(testToDo1Changed, username);

      verify(mockToDoRepo).save(testToDo1Changed);
   }
   @Test
   void shouldNotChangeItemWithMock() {
      ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
      ToDoService testToDoService = new ToDoService(mockToDoRepo);
      String username = "whoever";

      ToDoItem testToDo1 = new ToDoItem("Einkauf");
      testToDo1.setId("id-placeholder");
      testToDo1.setUsername(username);
      ToDoItem testToDo1Changed = new ToDoItem("Obi-Einkauf");
      testToDo1Changed.setId(testToDo1.getId());
      testToDo1Changed.setUsername(username);

      ToDoItem testToDoExtra = new ToDoItem("obi-einKAuf");
      testToDoExtra.setId("id-placeholder-extra");
      testToDoExtra.setUsername(username);

      when(mockToDoRepo.findById(testToDo1.getId())).thenReturn(Optional.of(testToDo1));

      testToDoService.changeItem(testToDo1Changed, username);

      verify(mockToDoRepo).findByTitleIgnoreCase("Obi-Einkauf");
      verify(mockToDoRepo).save(testToDo1);
   }
   @Test
   void shouldDeleteItemWithMock() {
      ToDoRepository mockToDoRepo = Mockito.mock(ToDoRepository.class);
      ToDoService testToDoService = new ToDoService(mockToDoRepo);
      String username = "whoever";
      String id = "id-placeholder";

      ToDoItem testToDo1 = new ToDoItem("Einkauf");
      testToDo1.setId(id);
      testToDo1.setUsername(username);

      when(mockToDoRepo.findById(id)).thenReturn(Optional.of(testToDo1));

      testToDoService.deleteItem(id, username);

      verify(mockToDoRepo).deleteById(id);
   }
}