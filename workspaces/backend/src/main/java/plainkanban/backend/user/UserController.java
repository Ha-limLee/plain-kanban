package plainkanban.backend.user;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping(path = "/api/v1/sign-up")
  public ResponseEntity<?> signUp(@RequestBody UserDto body) {
    userService.signUp(body);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

}
