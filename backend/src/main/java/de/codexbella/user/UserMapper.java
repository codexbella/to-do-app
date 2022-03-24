package de.codexbella.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
   public UserDocument toUserDocument(UserDTO userDTO) {
      UserDocument userDocument = new UserDocument();
      userDocument.setUsername(userDTO.getUsername());
      userDocument.setPassword(userDTO.getPassword());
      return userDocument;
   }
}
