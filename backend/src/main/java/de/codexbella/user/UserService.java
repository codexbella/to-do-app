package de.codexbella.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
   private final UserRepository userRepository;
   private final UserMapper userMapper;
   private final PasswordEncoder passwordEncoder;

   public String createUser(UserDTO userDTO) {
      if (userDTO.getPassword().equals(userDTO.getPasswordAgain())) {
         UserDocument userDocument = userMapper.toUserDocument(userDTO);
         if (userRepository.findByUsernameIgnoreCase(userDocument.getUsername()).isEmpty()) {
            userDocument.setPassword(passwordEncoder.encode(userDocument.getPassword()));
            userRepository.save(userDocument);
            return "New user created with username " + userDocument.getUsername();
         }
         return "Username " + userDocument.getUsername() + " already in use.";
      }
      return "Passwords mismatched.";
   }

   public Optional<UserDocument> findByUsername(String username) {
      return userRepository.findByUsernameIgnoreCase(username);
   }
}
