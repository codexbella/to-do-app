package de.codexbella;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToDoRepository extends MongoRepository<ToDoItem, String> {

   Optional<ToDoItem> findByTitleIgnoreCase(String title);

   List<ToDoItem> findAllByUsername(String username);
}
