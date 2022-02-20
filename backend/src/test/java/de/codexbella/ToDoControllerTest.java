package de.codexbella;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ToDoControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
/*
    @Test
    void shouldAddNewToDoItem() {
        ToDoItem testToDo1 = new ToDoItem("Einkauf");

        ResponseEntity<ToDoItem> actualResponse = restTemplate.postForEntity("/todoapp/additem", testToDo1, ToDoItem.class);

        ResponseEntity<ToDoItem[]> toDoItemResponseEntity = restTemplate.getForEntity("/todoapp/eink", ToDoItem[].class);
        System.out.println(toDoItemResponseEntity);
        // Error:
        // org.springframework.web.client.RestClientException: Error while extracting response for type [class de
        // .codexbella.ToDoItem] and content type [application/json]; nested exception is org.springframework.http
        // .converter.HttpMessageNotReadableException: JSON parse error: Cannot construct instance of `de.codexbella
        // .ToDoItem` (although at least one Creator exists): cannot deserialize from Object value (no delegate- or
        // property-based Creator); nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException:
        // Cannot construct instance of `de.codexbella.ToDoItem` (although at least one Creator exists): cannot
        // deserialize from Object value (no delegate- or property-based Creator)
        // at [Source: (PushbackInputStream); line: 1, column: 2]
        //
    }
 */

/*    @Test
    void shouldReturnCompleteListOfToDoItems() {

    }
    @Test
    void shouldReturnMatchingToDoItemsByTitle() {

    }
    @Test
    void shouldReturnEmptyListBecauseToDoItemNotInList() {

    }
    @Test
    void shouldSetToDoItemAsDone() {

    }
    @Test
    void shouldSetToDoItemAsNotDone() {

    }
    @Test
    void shouldReturnAllToDoItemsThatAreNotDone() {

    }
    @Test
    void shouldNotAddNewToDoItemBecauseAlreadyInList() {

    }
    @Test
    void shouldChangeItemTitle() {

    }
    @Test
    void shouldNotChangeItemTitleBecauseDuplicate() {

    }
    @Test
    void shouldChangeItemDescription() {

    }
    @Test
    void shouldReturnFalseBecauseNoSuchItemToSetDescriptionOf() {

    }
    @Test
    void shouldDeleteItem() {

    }*/


}