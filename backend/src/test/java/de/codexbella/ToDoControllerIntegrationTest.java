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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ToDoControllerIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void integrationTest() {
        // shouldReturnEmptyListBecauseToDoItemNotInList
        ResponseEntity<ToDoItem[]> responseGetEmptyList = restTemplate.getForEntity("/todoitems/tuedelue", ToDoItem[].class);
        Assertions.assertThat(Arrays.stream(responseGetEmptyList.getBody()).toList()).isEqualTo(List.of());

        // shouldAddNewToDoItem
        ToDoItem testToDo1 = new ToDoItem("Einkauf");

        List<ToDoItem> testToDos = new ArrayList<>();
        testToDos.add(testToDo1);

        ResponseEntity<ToDoItem[]> responseAdding = restTemplate.postForEntity("/todoitems/additem", testToDo1, ToDoItem[].class);

        Assertions.assertThat(Arrays.stream(responseAdding.getBody()).toList()).isEqualTo(testToDos);

        // shouldReturnMatchingToDoItemsByTitle
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen");
        ToDoItem testToDo3 = new ToDoItem("Impfung");
        ToDoItem testToDo4 = new ToDoItem("Obi-Einkauf");

        restTemplate.postForEntity("/todoitems/additem", testToDo2, ToDoItem[].class);
        restTemplate.postForEntity("/todoitems/additem", testToDo3, ToDoItem[].class);
        restTemplate.postForEntity("/todoitems/additem", testToDo4, ToDoItem[].class);

        ResponseEntity<ToDoItem[]> responseGetMatching = restTemplate.getForEntity("/todoitems/eink", ToDoItem[].class);
        Assertions.assertThat(Arrays.stream(responseGetMatching.getBody()).toList()).isEqualTo(List.of(testToDo1, testToDo4));

        // shouldReturnCompleteListOfToDoItems
        testToDos.add(testToDo2);
        testToDos.add(testToDo3);
        testToDos.add(testToDo4);

        ToDoItem[] listAll = restTemplate.getForObject("/todoitems/getall", ToDoItem[].class);

        Assertions.assertThat(Arrays.stream(listAll).toList()).isEqualTo(testToDos);

        // shouldSetToDoItemAsDone
        testToDo3.setDone(true);
        restTemplate.put("/todoitems/"+testToDo3.getId(), testToDo3);

        ToDoItem[] listDone = restTemplate.getForObject("/todoitems/getall", ToDoItem[].class);

        testToDos.remove(testToDo3);
        testToDos.add(testToDo3);

        Assertions.assertThat(Arrays.stream(listDone).toList()).isEqualTo(testToDos);

        // shouldReturnAllToDoItemsThatAreNotDone
        ToDoItem[] listAllNotDone = restTemplate.getForObject("/todoitems/getallnotdone", ToDoItem[].class);
        testToDos.remove(testToDo3);

        Assertions.assertThat(Arrays.stream(listAllNotDone).toList()).isEqualTo(testToDos);

        // shouldSetToDoItemAsNotDone
        testToDo3.setDone(false);
        restTemplate.put("/todoitems/"+testToDo3.getId(), testToDo3);
        testToDos.add(2, testToDo3);

        ToDoItem[] listNotDone = restTemplate.getForObject("/todoitems/getall", ToDoItem[].class);

        Assertions.assertThat(Arrays.stream(listNotDone).toList()).isEqualTo(testToDos);

        // shouldNotAddNewToDoItemBecauseAlreadyInList
        ResponseEntity<ToDoItem[]> responseNoDoubleAdding = restTemplate.postForEntity("/todoitems/additem", testToDo1, ToDoItem[].class);

        Assertions.assertThat(Arrays.stream(responseNoDoubleAdding.getBody()).toList()).isEqualTo(testToDos);

        // shouldChangeItemTitle
        ToDoItem testToDo3changed1 = new ToDoItem("Masern-Impfung", testToDo3.getDescription(), testToDo3.isDone());
        testToDo3changed1.setId(testToDo3.getId());

        restTemplate.put("/todoitems/"+testToDo3.getId(), testToDo3changed1);

        testToDo3.setTitle("Masern-Impfung");

        ToDoItem[] listAfterTitleChange = restTemplate.getForObject("/todoitems/getall", ToDoItem[].class);

        Assertions.assertThat(Arrays.stream(listAfterTitleChange).toList()).isEqualTo(testToDos);

        // shouldNotChangeItemTitleBecauseDuplicateTitle
        ToDoItem testToDo3changed2 = new ToDoItem("Fenster putzen", testToDo3.getDescription(), testToDo3.isDone());
        testToDo3changed2.setId(testToDo3.getId());

        restTemplate.put("/todoitems/"+testToDo3.getId(), testToDo3changed2);

        ToDoItem[] listAfterNoTitleChange = restTemplate.getForObject("/todoitems/getall", ToDoItem[].class);

        Assertions.assertThat(Arrays.stream(listAfterNoTitleChange).toList()).isEqualTo(testToDos);

        // shouldChangeItemDescription
        ToDoItem testToDo3changed3 = new ToDoItem(testToDo3.getTitle(), "in 6 Monaten", testToDo3.isDone());
        testToDo3changed3.setId(testToDo3.getId());

        restTemplate.put("/todoitems/"+testToDo3.getId(), testToDo3changed3);
        testToDo3.setDescription("in 6 Monaten");

        ToDoItem[] listAfterDescriptionChange = restTemplate.getForObject("/todoitems/getall", ToDoItem[].class);

        Assertions.assertThat(Arrays.stream(listAfterDescriptionChange).toList()).isEqualTo(testToDos);

        // shouldReturnFalseBecauseNoSuchItemToSetDescriptionOf
        ToDoItem itemNotInList = new ToDoItem("ladidadida", testToDo3.getDescription(), testToDo3.isDone());
        itemNotInList.setId(UUID.randomUUID().toString());
        ToDoItem itemNotInListChanged = new ToDoItem();
        itemNotInListChanged.setId(itemNotInList.getId());
        itemNotInListChanged.setTitle(itemNotInList.getTitle());
        itemNotInListChanged.setDescription("");
        itemNotInListChanged.setDone(itemNotInList.isDone());

        restTemplate.put("/todoitems/"+itemNotInList.getId(), itemNotInListChanged);

        ToDoItem[] listAfterNoDescriptionChange = restTemplate.getForObject("/todoitems/getall", ToDoItem[].class);

        Assertions.assertThat(Arrays.stream(listAfterNoDescriptionChange).toList()).isEqualTo(testToDos);

        // shouldDeleteItem
        restTemplate.delete("/todoitems/"+testToDo3.getId());
        testToDos.remove(testToDo3);

        ToDoItem[] listAfterDelete = restTemplate.getForObject("/todoitems/getall", ToDoItem[].class);

        Assertions.assertThat(Arrays.stream(listAfterDelete).toList()).isEqualTo(testToDos);

        // shouldReturnEmptyListBecauseToDoItemNotInList
        ToDoItem[] responseGetEmptyList2 = restTemplate.getForObject("/todoitems/tuedelue", ToDoItem[].class);
        Assertions.assertThat(Arrays.stream(responseGetEmptyList2).toList()).isEqualTo(List.of());
    }
}