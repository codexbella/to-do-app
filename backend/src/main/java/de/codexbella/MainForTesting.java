package de.codexbella;

import java.util.ArrayList;
import java.util.List;

public class MainForTesting {
    public static void main(String[] args) {
        ToDoItem testToDo1 = new ToDoItem("Obi-Einkauf", "Wäscheständer, Kabelbinder");
        ToDoItem testToDo2 = new ToDoItem("Fenster putzen", true);
        ToDoItem testToDo3 = new ToDoItem("Impfung");
        ToDoItem testToDo4 = new ToDoItem("Ast zerkleinern");

        List<ToDoItem> testListUnsorted = List.of(testToDo1, testToDo2, testToDo3, testToDo4);
        System.out.println(testListUnsorted.stream().sorted((i1, i2) -> i1.getTitle().compareTo(i2.getTitle())).toList());
    }
}
