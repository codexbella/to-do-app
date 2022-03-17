package de.codexbella.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
   private final UserRepository userRepository;

   public String createUser(UserDocument userDocument) {
      if (userRepository.findByUsernameIgnoreCase(userDocument.getUsername()).isEmpty()) {
         userRepository.save(userDocument);
         return "New user created with username "+userDocument.getUsername();
      }
      return "Username "+userDocument.getUsername()+" already in use.";
   }

   public Optional<UserDocument> findByUsername(String username) {
      return userRepository.findByUsernameIgnoreCase(username);
   }
}
