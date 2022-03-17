package de.codexbella.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
   private final UserService userService;
   private final PasswordEncoder passwordEncoder;

   @PostMapping("/register")
   public String createUser(@RequestBody UserDocument user) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      return userService.createUser(user);
   }
}
