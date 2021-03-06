package de.codexbella.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
   private final UserRepository userRepository;
   private final UserMapper userMapper;
   private final PasswordEncoder passwordEncoder;

   public String createUser(RegisterData registerData) {
      if (registerData.getPassword().equals(registerData.getPasswordAgain())) {
         UserDocument userDocument = userMapper.toUserDocument(registerData);
         if (userRepository.findByUsernameIgnoreCase(userDocument.getUsername()).isEmpty()) {
            userDocument.setPassword(passwordEncoder.encode(userDocument.getPassword()));
            userRepository.save(userDocument);
            return "New user created with username " + userDocument.getUsername();
         }
         throw new IllegalStateException("Username " + userDocument.getUsername() + " already in use.");
      }
      throw new InputMismatchException("Passwords mismatched.");
   }

   public Optional<UserDocument> findByUsername(String username) {
      return userRepository.findByUsernameIgnoreCase(username);
   }
}
