package de.codexbella.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
   public UserDocument toUserDocument(RegisterData registerData) {
      UserDocument userDocument = new UserDocument();
      userDocument.setUsername(registerData.getUsername());
      userDocument.setPassword(registerData.getPassword());
      return userDocument;
   }
}
