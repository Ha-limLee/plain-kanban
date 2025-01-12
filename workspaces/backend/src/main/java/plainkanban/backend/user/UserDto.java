package plainkanban.backend.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDto {
  private String name;
  private String email;
  private String password;
}
