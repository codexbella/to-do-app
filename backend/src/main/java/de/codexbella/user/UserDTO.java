package de.codexbella.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDTO {
   private String username;
   private String password;
   private String passwordAgain;
}
