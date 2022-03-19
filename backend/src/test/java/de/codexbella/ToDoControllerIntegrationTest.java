package de.codexbella;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ToDoControllerIntegrationTest {
   @LocalServerPort
   private int port;
   @Autowired
   private TestRestTemplate restTemplate;
   /*
   @Test
   void integrationTest() {
      // shouldReturnEmptyListBecauseToDoItemNotInList
      ResponseEntity<ToDoItem[]> responseGetEmptyList = restTemplate.getForEntity("/api/todoitems/tuedelue",
            ToDoItem[].class);
      Assertions.assertThat(Arrays.stream(responseGetEmptyList.getBody()).toList()).isEqualTo(List.of());

      // shouldAddNewToDoItem
      ToDoItem testToDo1 = new ToDoItem("Einkauf");

      ResponseEntity<ToDoItem[]> responseAdding = restTemplate.postForEntity("/api/todoitems/additem", testToDo1,
            ToDoItem[].class);
      List<ToDoItem> listAdding = Arrays.stream(responseAdding.getBody()).toList();

      Assertions.assertThat(listAdding.get(0).getTitle()).isEqualTo("Einkauf");
      Assertions.assertThat(listAdding.get(0).getDescription()).isEqualTo("");
      assertFalse(listAdding.get(0).isDone());

      // shouldReturnMatchingToDoItemsByTitle
      ToDoItem testToDo2 = new ToDoItem("Fenster putzen");
      ToDoItem testToDo3 = new ToDoItem("Impfung");
      ToDoItem testToDo4 = new ToDoItem("Obi-Einkauf");

      restTemplate.postForEntity("/api/todoitems/additem", testToDo2, ToDoItem[].class);
      restTemplate.postForEntity("/api/todoitems/additem", testToDo3, ToDoItem[].class);
      restTemplate.postForEntity("/api/todoitems/additem", testToDo4, ToDoItem[].class);

      ResponseEntity<ToDoItem[]> responseMatching = restTemplate.getForEntity("/api/todoitems/eink", ToDoItem[].class);
      List<ToDoItem> listMatching = Arrays.stream(responseMatching.getBody()).toList();

      Assertions.assertThat(listMatching.size()).isEqualTo(2);
      Assertions.assertThat(listMatching.get(0).getTitle()).isEqualTo("Einkauf");
      Assertions.assertThat(listMatching.get(0).getDescription()).isEqualTo("");
      assertFalse(listMatching.get(0).isDone());
      Assertions.assertThat(listMatching.get(1).getTitle()).isEqualTo("Obi-Einkauf");
      Assertions.assertThat(listMatching.get(1).getDescription()).isEqualTo("");
      assertFalse(listMatching.get(1).isDone());

      // shouldReturnCompleteListOfToDoItems

      ResponseEntity<ToDoItem[]> responseAll = restTemplate.getForEntity("/api/todoitems/getall", ToDoItem[].class);
      List<ToDoItem> listAll = Arrays.stream(responseAll.getBody()).toList();

      Assertions.assertThat(listAll.size()).isEqualTo(4);
      Assertions.assertThat(listAll.get(0).getTitle()).isEqualTo("Einkauf");
      Assertions.assertThat(listAll.get(1).getTitle()).isEqualTo("Fenster putzen");
      Assertions.assertThat(listAll.get(2).getTitle()).isEqualTo("Impfung");
      Assertions.assertThat(listAll.get(3).getTitle()).isEqualTo("Obi-Einkauf");

      // shouldSetToDoItemAsDone
      testToDo3.setDone(true);
      testToDo3.setId(listAll.get(2).getId());

      restTemplate.put("/api/todoitems/" + testToDo3.getId(), testToDo3);

      ResponseEntity<ToDoItem[]> responseDone = restTemplate.getForEntity("/api/todoitems/getall", ToDoItem[].class);
      List<ToDoItem> listDone = Arrays.stream(responseDone.getBody()).toList();

      Assertions.assertThat(listDone.size()).isEqualTo(4);
      Assertions.assertThat(listDone.get(3).getTitle()).isEqualTo("Impfung");
      Assertions.assertThat(listDone.get(3).getDescription()).isEqualTo("");
      assertTrue(listDone.get(3).isDone());

      // shouldReturnAllToDoItemsThatAreNotDone
      ResponseEntity<ToDoItem[]> responseAllNotDone = restTemplate.getForEntity("/api/todoitems/getallnotdone",
            ToDoItem[].class);
      List<ToDoItem> listAllNotDone = Arrays.stream(responseAllNotDone.getBody()).toList();

      Assertions.assertThat(listAllNotDone.size()).isEqualTo(3);
      Assertions.assertThat(listAllNotDone.get(0).getTitle()).isEqualTo("Einkauf");
      Assertions.assertThat(listAllNotDone.get(1).getTitle()).isEqualTo("Fenster putzen");
      Assertions.assertThat(listAllNotDone.get(2).getTitle()).isEqualTo("Obi-Einkauf");

      // shouldSetToDoItemAsNotDone
      testToDo3.setDone(false);
      restTemplate.put("/api/todoitems/" + testToDo3.getId(), testToDo3);

      ToDoItem[] arrayNotDone = restTemplate.getForObject("/api/todoitems/getall", ToDoItem[].class);

      Assertions.assertThat(arrayNotDone.length).isEqualTo(4);
      Assertions.assertThat(arrayNotDone[2].getTitle()).isEqualTo("Impfung");
      Assertions.assertThat(arrayNotDone[2].getDescription()).isEqualTo("");
      assertFalse(arrayNotDone[2].isDone());

      // shouldNotAddNewToDoItemBecauseAlreadyInList
      ToDoItem[] arrayNoDoubleAdding = restTemplate.postForObject("/api/todoitems/additem", testToDo1,
            ToDoItem[].class);

      Assertions.assertThat(arrayNoDoubleAdding.length).isEqualTo(4);

      // shouldNotAddNewToDoItemBecauseDuplicateTitle
      ToDoItem testToDoX = new ToDoItem("einKAUf");
      ToDoItem[] arrayNoDoubleTitleAdding = restTemplate.postForObject("/api/todoitems/additem", testToDoX,
            ToDoItem[].class);

      Assertions.assertThat(arrayNoDoubleTitleAdding.length).isEqualTo(4);
      Assertions.assertThat(listAllNotDone.get(0).getTitle()).isEqualTo("Einkauf");

      // shouldChangeItemTitle
      ToDoItem testToDo3changed1 = new ToDoItem("Masern-Impfung", testToDo3.getDescription(), testToDo3.isDone());
      testToDo3changed1.setId(testToDo3.getId());

      restTemplate.put("/api/todoitems/" + testToDo3.getId(), testToDo3changed1);

      ToDoItem[] arrayAfterTitleChange = restTemplate.getForObject("/api/todoitems/getall", ToDoItem[].class);

      Assertions.assertThat(arrayAfterTitleChange[2].getTitle()).isEqualTo("Masern-Impfung");

      // shouldNotChangeItemTitleBecauseDuplicateTitle
      ToDoItem testToDo3changed2 = new ToDoItem("fenster PUtzen", testToDo3.getDescription(), testToDo3.isDone());
      testToDo3changed2.setId(testToDo3.getId());

      restTemplate.put("/api/todoitems/" + testToDo3.getId(), testToDo3changed2);

      ToDoItem[] arrayAfterNoTitleChange = restTemplate.getForObject("/api/todoitems/getall", ToDoItem[].class);

      Assertions.assertThat(arrayAfterNoTitleChange[2].getTitle()).isEqualTo("Masern-Impfung");

      // shouldChangeItemDescription
      ToDoItem testToDo3changed3 = new ToDoItem(testToDo3.getTitle(), "in 6 Monaten", testToDo3.isDone());
      testToDo3changed3.setId(testToDo3.getId());

      restTemplate.put("/api/todoitems/" + testToDo3.getId(), testToDo3changed3);

      ToDoItem[] arrayAfterDescriptionChange = restTemplate.getForObject("/api/todoitems/getall", ToDoItem[].class);

      Assertions.assertThat(arrayAfterDescriptionChange[3].getDescription()).isEqualTo("in 6 Monaten");

      // shouldReturnFalseBecauseNoSuchItemToSetDescriptionOf
      ToDoItem itemNotInList = new ToDoItem("ladidadida");
      itemNotInList.setId(UUID.randomUUID().toString());

      restTemplate.put("/api/todoitems/" + itemNotInList.getId(), itemNotInList);

      ToDoItem[] arrayAfterNoDescriptionChange = restTemplate.getForObject("/api/todoitems/getall", ToDoItem[].class);

      Assertions.assertThat(arrayAfterNoDescriptionChange.length).isEqualTo(4);
      Assertions.assertThat(arrayAfterNoDescriptionChange[0].getTitle()).isEqualTo("Einkauf");
      Assertions.assertThat(arrayAfterNoDescriptionChange[1].getTitle()).isEqualTo("Fenster putzen");
      Assertions.assertThat(arrayAfterNoDescriptionChange[2].getTitle()).isEqualTo("Obi-Einkauf");
      Assertions.assertThat(arrayAfterNoDescriptionChange[3].getTitle()).isEqualTo("Impfung");

      // shouldDeleteItem
      restTemplate.delete("/api/todoitems/" + testToDo3.getId());

      ToDoItem[] arrayAfterDelete = restTemplate.getForObject("/api/todoitems/getall", ToDoItem[].class);

      Assertions.assertThat(arrayAfterDelete.length).isEqualTo(3);
      Assertions.assertThat(arrayAfterDelete[0].getTitle()).isEqualTo("Einkauf");
      Assertions.assertThat(arrayAfterDelete[1].getTitle()).isEqualTo("Fenster putzen");
      Assertions.assertThat(arrayAfterDelete[2].getTitle()).isEqualTo("Obi-Einkauf");

      // shouldReturnEmptyListBecauseToDoItemNotInList
      ToDoItem[] arrayGetEmptyList2 = restTemplate.getForObject("/api/todoitems/tuedelue", ToDoItem[].class);

      Assertions.assertThat(Arrays.stream(arrayGetEmptyList2).toList()).isEqualTo(List.of());
   }
   */
}