package de.codexbella.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
   private final UserService userService;
   private final PasswordEncoder passwordEncoder;
   private final AuthenticationManager authenticationManager;
   private final JwtService jwtService;

   @PostMapping("/register")
   public String createUser(@RequestBody UserDocument user) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      return userService.createUser(user);
   }

   @PostMapping("/login")
   public String login(@RequestBody LoginData loginData) {
      Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.getUsername(), loginData.getPassword()));
      List<String> roles = auth.getAuthorities().stream().map(ga -> ga.getAuthority()).toList();
      Map<String, Object> claims = new HashMap<>();
      claims.put("roles", roles);
      return jwtService.createToken(claims, loginData.getUsername());
   }
}