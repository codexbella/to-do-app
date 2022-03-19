package de.codexbella;

import de.codexbella.user.LoginData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MultiValueMap;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ToDoControllerIntegrationTest {
   @LocalServerPort
   private int port;
   @Autowired
   private TestRestTemplate restTemplate;

   @Test
   void integrationTest() {
      // shouldRegisterANewUser
      LoginData user1 = new LoginData();
      user1.setUsername("whoever");
      user1.setPassword("very-safe-password");

      ResponseEntity<String> responseRegister = restTemplate.postForEntity("/api/users/register", user1, String.class);

      assertThat(responseRegister.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertEquals("New user created with username " + user1.getUsername(), responseRegister.getBody());

      // shouldNotRegisterANewUser
      LoginData otherUser = new LoginData();
      otherUser.setUsername(user1.getUsername());
      otherUser.setPassword("extremely-safe-password");

      ResponseEntity<String> responseNotRegister = restTemplate.postForEntity("/api/users/register", user1,
            String.class);

      assertEquals("Username " + otherUser.getUsername() + " already in use.", responseNotRegister.getBody());

      // shouldLoginUser
      ResponseEntity<String> responseLoginUser1 = restTemplate.postForEntity("/api/users/login", user1, String.class);

      assertThat(responseLoginUser1.getStatusCode()).isEqualTo(HttpStatus.OK);

      // shouldNotLoginUser
      otherUser.setPassword("xxx");

      ResponseEntity<String> responseNoLogin = restTemplate.postForEntity("/api/users/login", otherUser, String.class);

      assertThat(responseNoLogin.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

      otherUser.setPassword("extremely-safe-password");

      // shouldReturnEmptyListBecauseToDoItemNotInList
      HttpHeaders headerForUser1 = new HttpHeaders();
      headerForUser1.set("Authorization", "Bearer" + responseLoginUser1.getBody());

      HttpEntity<ToDoItem> httpEntityUser1Get = new HttpEntity<>(headerForUser1);

      ResponseEntity<ToDoItem[]> responseGetEmptyList = restTemplate.exchange("/api/todoitems/tuedelue",
            HttpMethod.GET, httpEntityUser1Get, ToDoItem[].class);

      assertThat(responseGetEmptyList.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseGetEmptyList.getBody()).isNotNull();
      assertThat(Arrays.stream(responseGetEmptyList.getBody()).toList()).isEqualTo(List.of());

      // shouldAddNewToDoItem
      ToDoItem testToDo1 = new ToDoItem("Einkauf");

      ResponseEntity<ToDoItem[]> responseAdding = restTemplate.exchange("/api/todoitems/additem", HttpMethod.POST,
            new HttpEntity<>(testToDo1, headerForUser1), ToDoItem[].class);

      assertThat(responseAdding.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseAdding.getBody()).isNotNull();
      List<ToDoItem> listAdding = Arrays.stream(responseAdding.getBody()).toList();

      Assertions.assertThat(listAdding.get(0).getTitle()).isEqualTo("Einkauf");
      Assertions.assertThat(listAdding.get(0).getDescription()).isEqualTo("");
      assertFalse(listAdding.get(0).isDone());

      // shouldReturnMatchingToDoItemsByTitle
      ToDoItem testToDo2 = new ToDoItem("Fenster putzen");
      ToDoItem testToDo3 = new ToDoItem("Impfung");
      ToDoItem testToDo4 = new ToDoItem("Obi-Einkauf");

      restTemplate.exchange("/api/todoitems/additem", HttpMethod.POST, new HttpEntity<>(testToDo2, headerForUser1), ToDoItem[].class);
      restTemplate.exchange("/api/todoitems/additem", HttpMethod.POST, new HttpEntity<>(testToDo3, headerForUser1), ToDoItem[].class);
      restTemplate.exchange("/api/todoitems/additem", HttpMethod.POST, new HttpEntity<>(testToDo4, headerForUser1), ToDoItem[].class);

      ResponseEntity<ToDoItem[]> responseMatching = restTemplate.exchange("/api/todoitems/eink",
            HttpMethod.GET, httpEntityUser1Get, ToDoItem[].class);
      assertThat(responseMatching.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseMatching.getBody()).isNotNull();
      List<ToDoItem> listMatching = Arrays.stream(responseMatching.getBody()).toList();

      Assertions.assertThat(listMatching.size()).isEqualTo(2);
      Assertions.assertThat(listMatching.get(0).getTitle()).isEqualTo("Einkauf");
      Assertions.assertThat(listMatching.get(0).getDescription()).isEqualTo("");
      assertFalse(listMatching.get(0).isDone());
      Assertions.assertThat(listMatching.get(1).getTitle()).isEqualTo("Obi-Einkauf");
      Assertions.assertThat(listMatching.get(1).getDescription()).isEqualTo("");
      assertFalse(listMatching.get(1).isDone());

      // shouldReturnCompleteListOfToDoItems
      ResponseEntity<ToDoItem[]> responseAll = restTemplate.exchange("/api/todoitems/getall", HttpMethod.GET, httpEntityUser1Get, ToDoItem[].class);
      assertThat(responseAll.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseAll.getBody()).isNotNull();
      List<ToDoItem> listAll = Arrays.stream(responseAll.getBody()).toList();

      Assertions.assertThat(listAll.size()).isEqualTo(4);
      Assertions.assertThat(listAll.get(0).getTitle()).isEqualTo("Einkauf");
      Assertions.assertThat(listAll.get(1).getTitle()).isEqualTo("Fenster putzen");
      Assertions.assertThat(listAll.get(2).getTitle()).isEqualTo("Impfung");
      Assertions.assertThat(listAll.get(3).getTitle()).isEqualTo("Obi-Einkauf");

      // shouldSetToDoItemAsDone
      testToDo3.setDone(true);
      testToDo3.setId(listAll.get(2).getId());

      ResponseEntity<ToDoItem[]> responseDone = restTemplate.exchange("/api/todoitems/" + testToDo3.getId(), HttpMethod.PUT, new HttpEntity<>(testToDo3, headerForUser1), ToDoItem[].class);
      assertThat(responseDone.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseDone.getBody()).isNotNull();
      List<ToDoItem> listDone = Arrays.stream(responseDone.getBody()).toList();

      Assertions.assertThat(listDone.size()).isEqualTo(4);
      Assertions.assertThat(listDone.get(3).getTitle()).isEqualTo("Impfung");
      Assertions.assertThat(listDone.get(3).getDescription()).isEqualTo("");
      assertTrue(listDone.get(3).isDone());

      // shouldReturnAllToDoItemsThatAreNotDone
      ResponseEntity<ToDoItem[]> responseAllNotDone = restTemplate.exchange("/api/todoitems/getallnotdone", HttpMethod.GET, httpEntityUser1Get, ToDoItem[].class);
      assertThat(responseAllNotDone.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseAllNotDone.getBody()).isNotNull();
      List<ToDoItem> listAllNotDone = Arrays.stream(responseAllNotDone.getBody()).toList();

      Assertions.assertThat(listAllNotDone.size()).isEqualTo(3);
      Assertions.assertThat(listAllNotDone.get(0).getTitle()).isEqualTo("Einkauf");
      Assertions.assertThat(listAllNotDone.get(1).getTitle()).isEqualTo("Fenster putzen");
      Assertions.assertThat(listAllNotDone.get(2).getTitle()).isEqualTo("Obi-Einkauf");

      // shouldSetToDoItemAsNotDone
      testToDo3.setDone(false);

      ResponseEntity<ToDoItem[]> responseNotDone = restTemplate.exchange("/api/todoitems/" + testToDo3.getId(), HttpMethod.PUT, new HttpEntity<>(testToDo3, headerForUser1), ToDoItem[].class);
      assertThat(responseNotDone.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseNotDone.getBody()).isNotNull();
      List<ToDoItem> listNotDone = Arrays.stream(responseNotDone.getBody()).toList();

      Assertions.assertThat(listNotDone.size()).isEqualTo(4);
      Assertions.assertThat(listNotDone.get(2).getTitle()).isEqualTo("Impfung");
      Assertions.assertThat(listNotDone.get(2).getDescription()).isEqualTo("");
      Assertions.assertThat(listNotDone.get(2).isDone()).isEqualTo(false);

      // shouldNotAddNewToDoItemBecauseAlreadyInList
      ResponseEntity<ToDoItem[]> responseNoDoubleAdding = restTemplate.exchange("/api/todoitems/additem", HttpMethod.POST, new HttpEntity<>(testToDo1, headerForUser1), ToDoItem[].class);
      assertThat(responseNoDoubleAdding.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseNoDoubleAdding.getBody()).isNotNull();
      List<ToDoItem> listNoDoubleAdding = Arrays.stream(responseNotDone.getBody()).toList();

      Assertions.assertThat(listNoDoubleAdding.size()).isEqualTo(4);

      // shouldNotAddNewToDoItemBecauseDuplicateTitle
      ToDoItem testToDoX = new ToDoItem("einKAUf");
      ResponseEntity<ToDoItem[]> responseNoDoubleTitleAdding = restTemplate.exchange("/api/todoitems/additem", HttpMethod.POST, new HttpEntity<>(testToDoX, headerForUser1), ToDoItem[].class);
      assertThat(responseNoDoubleTitleAdding.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseNoDoubleTitleAdding.getBody()).isNotNull();
      List<ToDoItem> listNoDoubleTitleAdding = Arrays.stream(responseNotDone.getBody()).toList();

      Assertions.assertThat(listNoDoubleTitleAdding.size()).isEqualTo(4);
      Assertions.assertThat(listNoDoubleTitleAdding.get(0).getTitle()).isEqualTo("Einkauf");

      // shouldChangeItemTitle
      ToDoItem testToDo3changed1 = new ToDoItem("Masern-Impfung", testToDo3.getDescription(), testToDo3.isDone());
      testToDo3changed1.setId(testToDo3.getId());

      ResponseEntity<ToDoItem[]> responseAfterTitleChange = restTemplate.exchange("/api/todoitems/" + testToDo3.getId(), HttpMethod.PUT, new HttpEntity<>(testToDo3changed1, headerForUser1), ToDoItem[].class);
      assertThat(responseAfterTitleChange.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseAfterTitleChange.getBody()).isNotNull();
      List<ToDoItem> listAfterTitleChange = Arrays.stream(responseAfterTitleChange.getBody()).toList();

      Assertions.assertThat(listAfterTitleChange.get(2).getTitle()).isEqualTo("Masern-Impfung");

      // shouldNotChangeItemTitleBecauseDuplicateTitle
      ToDoItem testToDo3changed2 = new ToDoItem("fenster PUtzen", testToDo3.getDescription(), testToDo3.isDone());
      testToDo3changed2.setId(testToDo3.getId());

      ResponseEntity<ToDoItem[]> responseAfterNoTitleChange = restTemplate.exchange("/api/todoitems/" + testToDo3.getId(), HttpMethod.PUT, new HttpEntity<>(testToDo3changed2, headerForUser1), ToDoItem[].class);
      assertThat(responseAfterNoTitleChange.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseAfterNoTitleChange.getBody()).isNotNull();
      List<ToDoItem> listAfterNoTitleChange = Arrays.stream(responseAfterNoTitleChange.getBody()).toList();

      Assertions.assertThat(listAfterNoTitleChange.get(2).getTitle()).isEqualTo("Masern-Impfung");

      // shouldChangeItemDescription
      ToDoItem testToDo3changed3 = new ToDoItem(testToDo3.getTitle(), "in 6 Monaten", testToDo3.isDone());
      testToDo3changed3.setId(testToDo3.getId());

      ResponseEntity<ToDoItem[]> responseAfterDescriptionChange = restTemplate.exchange("/api/todoitems/" + testToDo3.getId(), HttpMethod.PUT, new HttpEntity<>(testToDo3changed3, headerForUser1), ToDoItem[].class);
      assertThat(responseAfterDescriptionChange.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseAfterDescriptionChange.getBody()).isNotNull();
      List<ToDoItem> listAfterDescriptionChange = Arrays.stream(responseAfterDescriptionChange.getBody()).toList();

      Assertions.assertThat(listAfterDescriptionChange.get(2).getDescription()).isEqualTo("in 6 Monaten");

      // shouldReturnFalseBecauseNoSuchItemToSetDescriptionOf
      ToDoItem itemNotInList = new ToDoItem("ladidadida");
      itemNotInList.setId(UUID.randomUUID().toString());
      itemNotInList.setDescription("will never make it into databank");

      ResponseEntity<ToDoItem[]> responseAfterNoDescriptionChange = restTemplate.exchange("/api/todoitems/" + itemNotInList.getId(), HttpMethod.PUT, new HttpEntity<>(itemNotInList, headerForUser1), ToDoItem[].class);
      assertThat(responseAfterNoDescriptionChange.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseAfterNoDescriptionChange.getBody()).isNotNull();
      List<ToDoItem> listAfterNoDescriptionChange = Arrays.stream(responseAfterNoDescriptionChange.getBody()).toList();

      Assertions.assertThat(listAfterNoDescriptionChange.size()).isEqualTo(4);
      Assertions.assertThat(listAfterNoDescriptionChange.get(0).getTitle()).isEqualTo("Einkauf");
      Assertions.assertThat(listAfterNoDescriptionChange.get(1).getTitle()).isEqualTo("Fenster putzen");
      Assertions.assertThat(listAfterNoDescriptionChange.get(2).getTitle()).isEqualTo("Impfung");
      Assertions.assertThat(listAfterNoDescriptionChange.get(3).getTitle()).isEqualTo("Obi-Einkauf");

      // shouldDeleteItem
      ResponseEntity<ToDoItem[]> responseAfterDelete = restTemplate.exchange("/api/todoitems/" + testToDo3.getId(), HttpMethod.DELETE, new HttpEntity<>(headerForUser1), ToDoItem[].class);
      assertThat(responseAfterDelete.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseAfterDelete.getBody()).isNotNull();
      List<ToDoItem> listAfterDelete = Arrays.stream(responseAfterDelete.getBody()).toList();

      Assertions.assertThat(listAfterDelete.size()).isEqualTo(3);
      Assertions.assertThat(listAfterDelete.get(0).getTitle()).isEqualTo("Einkauf");
      Assertions.assertThat(listAfterDelete.get(1).getTitle()).isEqualTo("Fenster putzen");
      Assertions.assertThat(listAfterDelete.get(2).getTitle()).isEqualTo("Obi-Einkauf");

      // shouldReturnEmptyListBecauseToDoItemNotInList
      ResponseEntity<ToDoItem[]> responseGetEmptyList2 = restTemplate.exchange("/api/todoitems/" + testToDo3.getId(), HttpMethod.GET, httpEntityUser1Get, ToDoItem[].class);

      assertThat(responseGetEmptyList2.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseGetEmptyList2.getBody()).isNotNull();
      assertThat(Arrays.stream(responseGetEmptyList2.getBody()).toList()).isEqualTo(List.of());
   }
}