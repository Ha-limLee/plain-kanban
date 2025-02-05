package plainkanban.backend.user;

import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping(path = "/api/v1/sign-up")
  public ResponseEntity<?> signUp(@RequestBody UserDto body) {
    var users = userService.findByEmail(body.getEmail());
    if (!users.isEmpty()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    userService.signUp(body);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping(path = "/api/v1/user/test")
  public ResponseEntity<?> testAuthRoute() {
    return ResponseEntity.status(HttpStatus.OK).build();
  }

}
