package de.codexbella;

import de.codexbella.user.LoginData;
import de.codexbella.user.RegisterData;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ToDoControllerIntegrationTest {
   @Autowired
   private TestRestTemplate restTemplate;

   @Test
   void integrationTest() {
      // shouldRegisterANewUser
      RegisterData registerDataUser1 = new RegisterData();
      registerDataUser1.setUsername("whoever");
      registerDataUser1.setPassword("very-safe-password");
      registerDataUser1.setPasswordAgain("very-safe-password");

      ResponseEntity<String> responseRegister = restTemplate.postForEntity("/api/users/register", registerDataUser1, String.class);

      assertThat(responseRegister.getStatusCode()).isEqualTo(HttpStatus.CREATED);
      assertEquals("New user created with username " + registerDataUser1.getUsername(), responseRegister.getBody());

      // shouldNotRegisterANewUserBecausePasswordsDoNotMatch
      RegisterData registerDataUser2 = new RegisterData();
      registerDataUser2.setUsername(registerDataUser1.getUsername());
      registerDataUser2.setPassword("tadada");
      registerDataUser2.setPasswordAgain("tududu");

      ResponseEntity<String> responseNotRegister1 = restTemplate.postForEntity("/api/users/register", registerDataUser2,
            String.class);

      assertThat(responseNotRegister1.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
      assertEquals("Passwords mismatched.", responseNotRegister1.getBody());

      // shouldNotRegisterANewUserBecauseUsernameAlreadyInUse
      registerDataUser2.setPasswordAgain("tadada");

      ResponseEntity<String> responseNotRegister2 = restTemplate.postForEntity("/api/users/register", registerDataUser2,
            String.class);

      assertThat(responseNotRegister2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
      assertEquals("Username " + registerDataUser2.getUsername() + " already in use.", responseNotRegister2.getBody());

      // shouldLoginUser
      LoginData user1 = new LoginData();
      user1.setUsername("whoever");
      user1.setPassword("very-safe-password");

      ResponseEntity<String> responseLoginUser1 = restTemplate.postForEntity("/api/users/login", user1, String.class);

      assertThat(responseLoginUser1.getStatusCode()).isEqualTo(HttpStatus.OK);

      // shouldNotLoginUser
      LoginData user2 = new LoginData();
      user2.setUsername(registerDataUser1.getUsername());
      user2.setPassword("xxx");

      ResponseEntity<String> responseNoLogin = restTemplate.postForEntity("/api/users/login", user2, String.class);

      assertThat(responseNoLogin.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

      user2.setPassword("extremely-safe-password");

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

      ResponseEntity<ToDoItem[]> responseAdding = restTemplate.postForEntity("/api/todoitems/additem",
            new HttpEntity<>(testToDo1, headerForUser1), ToDoItem[].class);

      assertThat(responseAdding.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseAdding.getBody()).isNotNull();
      ToDoItem[] arrayAdding = responseAdding.getBody();

      assertThat(arrayAdding[0].getTitle()).isEqualTo("Einkauf");
      assertThat(arrayAdding[0].getDescription()).isEqualTo("");
      assertFalse(arrayAdding[0].isDone());

      // shouldReturnMatchingToDoItemsByTitle
      ToDoItem testToDo2 = new ToDoItem("Fenster putzen");
      ToDoItem testToDo3 = new ToDoItem("Impfung");
      ToDoItem testToDo4 = new ToDoItem("Obi-Einkauf");

      restTemplate.exchange("/api/todoitems/additem", HttpMethod.POST, new HttpEntity<>(testToDo2, headerForUser1),
            ToDoItem[].class);
      restTemplate.exchange("/api/todoitems/additem", HttpMethod.POST, new HttpEntity<>(testToDo3, headerForUser1),
            ToDoItem[].class);
      restTemplate.exchange("/api/todoitems/additem", HttpMethod.POST, new HttpEntity<>(testToDo4, headerForUser1),
            ToDoItem[].class);

      ResponseEntity<ToDoItem[]> responseMatching = restTemplate.exchange("/api/todoitems/eink",
            HttpMethod.GET, httpEntityUser1Get, ToDoItem[].class);
      assertThat(responseMatching.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseMatching.getBody()).isNotNull();
      ToDoItem[] arrayMatching = responseMatching.getBody();

      assertThat(arrayMatching.length).isEqualTo(2);
      assertThat(arrayMatching[0].getTitle()).isEqualTo("Einkauf");
      assertThat(arrayMatching[0].getDescription()).isEqualTo("");
      assertFalse(arrayMatching[0].isDone());
      assertThat(arrayMatching[1].getTitle()).isEqualTo("Obi-Einkauf");
      assertThat(arrayMatching[1].getDescription()).isEqualTo("");
      assertFalse(arrayMatching[1].isDone());

      // shouldReturnCompleteListOfToDoItems
      ResponseEntity<ToDoItem[]> responseAll = restTemplate.exchange("/api/todoitems/getall", HttpMethod.GET,
            httpEntityUser1Get, ToDoItem[].class);
      assertThat(responseAll.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseAll.getBody()).isNotNull();
      ToDoItem[] arrayAll = responseAll.getBody();

      assertThat(arrayAll.length).isEqualTo(4);
      assertThat(arrayAll[0].getTitle()).isEqualTo("Einkauf");
      assertThat(arrayAll[1].getTitle()).isEqualTo("Fenster putzen");
      assertThat(arrayAll[2].getTitle()).isEqualTo("Impfung");
      assertThat(arrayAll[3].getTitle()).isEqualTo("Obi-Einkauf");

      // shouldSetToDoItemAsDone
      testToDo3.setDone(true);
      testToDo3.setId(arrayAll[2].getId());

      ResponseEntity<ToDoItem[]> responseDone = restTemplate.exchange("/api/todoitems/" + testToDo3.getId(),
            HttpMethod.PUT, new HttpEntity<>(testToDo3, headerForUser1), ToDoItem[].class);
      assertThat(responseDone.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseDone.getBody()).isNotNull();
      ToDoItem[] arrayDone = responseDone.getBody();

      assertThat(arrayDone.length).isEqualTo(4);
      assertThat(arrayDone[3].getTitle()).isEqualTo("Impfung");
      assertThat(arrayDone[3].getDescription()).isEqualTo("");
      assertTrue(arrayDone[3].isDone());

      // shouldReturnAllToDoItemsThatAreNotDone
      ResponseEntity<ToDoItem[]> responseAllNotDone = restTemplate.exchange("/api/todoitems/getallnotdone",
            HttpMethod.GET, httpEntityUser1Get, ToDoItem[].class);
      assertThat(responseAllNotDone.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseAllNotDone.getBody()).isNotNull();
      ToDoItem[] arrayAllNotDone = responseAllNotDone.getBody();

      assertThat(arrayAllNotDone.length).isEqualTo(3);
      assertThat(arrayAllNotDone[0].getTitle()).isEqualTo("Einkauf");
      assertThat(arrayAllNotDone[1].getTitle()).isEqualTo("Fenster putzen");
      assertThat(arrayAllNotDone[2].getTitle()).isEqualTo("Obi-Einkauf");

      // shouldSetToDoItemAsNotDone
      testToDo3.setDone(false);

      ResponseEntity<ToDoItem[]> responseNotDone = restTemplate.exchange("/api/todoitems/" + testToDo3.getId(),
            HttpMethod.PUT, new HttpEntity<>(testToDo3, headerForUser1), ToDoItem[].class);
      assertThat(responseNotDone.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseNotDone.getBody()).isNotNull();
      ToDoItem[] arrayNotDone = responseNotDone.getBody();

      assertThat(arrayNotDone.length).isEqualTo(4);
      assertThat(arrayNotDone[2].getTitle()).isEqualTo("Impfung");
      assertThat(arrayNotDone[2].getDescription()).isEqualTo("");
      assertThat(arrayNotDone[2].isDone()).isEqualTo(false);

      // shouldNotAddNewToDoItemBecauseAlreadyInList
      ResponseEntity<ToDoItem[]> responseNoDoubleAdding = restTemplate.exchange("/api/todoitems/additem",
            HttpMethod.POST, new HttpEntity<>(testToDo1, headerForUser1), ToDoItem[].class);
      assertThat(responseNoDoubleAdding.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseNoDoubleAdding.getBody()).isNotNull();
      ToDoItem[] arrayNoDoubleAdding = responseNotDone.getBody();

      assertThat(arrayNoDoubleAdding.length).isEqualTo(4);

      // shouldNotAddNewToDoItemBecauseDuplicateTitle
      ToDoItem testToDoX = new ToDoItem("einKAUf");
      ResponseEntity<ToDoItem[]> responseNoDoubleTitleAdding = restTemplate.exchange("/api/todoitems/additem",
            HttpMethod.POST, new HttpEntity<>(testToDoX, headerForUser1), ToDoItem[].class);
      assertThat(responseNoDoubleTitleAdding.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseNoDoubleTitleAdding.getBody()).isNotNull();
      ToDoItem[] arrayNoDoubleTitleAdding = responseNotDone.getBody();

      assertThat(arrayNoDoubleTitleAdding.length).isEqualTo(4);
      assertThat(arrayNoDoubleTitleAdding[0].getTitle()).isEqualTo("Einkauf");

      // shouldChangeItemTitle
      ToDoItem testToDo3changed1 = new ToDoItem("Masern-Impfung", testToDo3.getDescription(), testToDo3.isDone());
      testToDo3changed1.setId(testToDo3.getId());

      ResponseEntity<ToDoItem[]> responseAfterTitleChange =
            restTemplate.exchange("/api/todoitems/" + testToDo3.getId(), HttpMethod.PUT,
                  new HttpEntity<>(testToDo3changed1, headerForUser1), ToDoItem[].class);
      assertThat(responseAfterTitleChange.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseAfterTitleChange.getBody()).isNotNull();
      ToDoItem[] arrayAfterTitleChange = responseAfterTitleChange.getBody();

      assertThat(arrayAfterTitleChange[2].getTitle()).isEqualTo("Masern-Impfung");

      // shouldNotChangeItemTitleBecauseDuplicateTitle
      ToDoItem testToDo3changed2 = new ToDoItem("fenster PUtzen", testToDo3.getDescription(), testToDo3.isDone());
      testToDo3changed2.setId(testToDo3.getId());

      ResponseEntity<ToDoItem[]> responseAfterNoTitleChange =
            restTemplate.exchange("/api/todoitems/" + testToDo3.getId(), HttpMethod.PUT,
                  new HttpEntity<>(testToDo3changed2, headerForUser1), ToDoItem[].class);
      assertThat(responseAfterNoTitleChange.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseAfterNoTitleChange.getBody()).isNotNull();
      ToDoItem[] arrayAfterNoTitleChange = responseAfterNoTitleChange.getBody();

      assertThat(arrayAfterNoTitleChange[2].getTitle()).isEqualTo("Masern-Impfung");

      // shouldChangeItemDescription
      ToDoItem testToDo3changed3 = new ToDoItem(testToDo3.getTitle(), "in 6 Monaten", testToDo3.isDone());
      testToDo3changed3.setId(testToDo3.getId());

      ResponseEntity<ToDoItem[]> responseAfterDescriptionChange =
            restTemplate.exchange("/api/todoitems/" + testToDo3.getId(), HttpMethod.PUT,
                  new HttpEntity<>(testToDo3changed3, headerForUser1), ToDoItem[].class);
      assertThat(responseAfterDescriptionChange.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseAfterDescriptionChange.getBody()).isNotNull();
      ToDoItem[] arrayAfterDescriptionChange = responseAfterDescriptionChange.getBody();

      assertThat(arrayAfterDescriptionChange[2].getDescription()).isEqualTo("in 6 Monaten");

      // shouldReturnFalseBecauseNoSuchItemToSetDescriptionOf
      ToDoItem itemNotInList = new ToDoItem("ladidadida");
      itemNotInList.setId(UUID.randomUUID().toString());
      itemNotInList.setDescription("will never make it into databank");

      ResponseEntity<ToDoItem[]> responseAfterNoDescriptionChange =
            restTemplate.exchange("/api/todoitems/" + itemNotInList.getId(), HttpMethod.PUT,
                  new HttpEntity<>(itemNotInList, headerForUser1), ToDoItem[].class);
      assertThat(responseAfterNoDescriptionChange.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseAfterNoDescriptionChange.getBody()).isNotNull();
      ToDoItem[] arrayAfterNoDescriptionChange = responseAfterNoDescriptionChange.getBody();

      assertThat(arrayAfterNoDescriptionChange.length).isEqualTo(4);
      assertThat(arrayAfterNoDescriptionChange[0].getTitle()).isEqualTo("Einkauf");
      assertThat(arrayAfterNoDescriptionChange[1].getTitle()).isEqualTo("Fenster putzen");
      assertThat(arrayAfterNoDescriptionChange[2].getTitle()).isEqualTo("Impfung");
      assertThat(arrayAfterNoDescriptionChange[3].getTitle()).isEqualTo("Obi-Einkauf");

      // shouldDeleteItem
      ResponseEntity<ToDoItem[]> responseAfterDelete = restTemplate.exchange("/api/todoitems/" + testToDo3.getId(),
            HttpMethod.DELETE, new HttpEntity<>(headerForUser1), ToDoItem[].class);
      assertThat(responseAfterDelete.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseAfterDelete.getBody()).isNotNull();
      ToDoItem[] arrayAfterDelete = responseAfterDelete.getBody();

      assertThat(arrayAfterDelete.length).isEqualTo(3);
      assertThat(arrayAfterDelete[0].getTitle()).isEqualTo("Einkauf");
      assertThat(arrayAfterDelete[1].getTitle()).isEqualTo("Fenster putzen");
      assertThat(arrayAfterDelete[2].getTitle()).isEqualTo("Obi-Einkauf");

      // shouldReturnEmptyListBecauseToDoItemNotInList
      ResponseEntity<ToDoItem[]> responseGetEmptyList2 = restTemplate.exchange("/api/todoitems/" + testToDo3.getId(),
            HttpMethod.GET, httpEntityUser1Get, ToDoItem[].class);

      assertThat(responseGetEmptyList2.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseGetEmptyList2.getBody()).isNotNull();
      assertThat(Arrays.stream(responseGetEmptyList2.getBody()).toList()).isEqualTo(List.of());
   }
}