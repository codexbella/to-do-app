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
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen", true);
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

        ResponseEntity<ToDoItem[]> responseGetAll = restTemplate.getForEntity("/todoitems/getall", ToDoItem[].class);

        Assertions.assertThat(Arrays.stream(responseGetAll.getBody()).toList()).isEqualTo(testToDos);

        // shouldSetToDoItemAsDone

        // shouldSetToDoItemAsNotDone

        // shouldReturnAllToDoItemsThatAreNotDone

        // shouldNotAddNewToDoItemBecauseAlreadyInList

        // shouldChangeItemTitle

        // shouldNotChangeItemTitleBecauseDuplicate

        // shouldChangeItemDescription

        // shouldReturnFalseBecauseNoSuchItemToSetDescriptionOf

        // shouldDeleteItem

    }
}