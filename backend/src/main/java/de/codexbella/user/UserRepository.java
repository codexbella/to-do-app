package de.codexbella.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserDocument, String> {
   Optional<UserDocument> findByUsernameIgnoreCase(String username);
}
