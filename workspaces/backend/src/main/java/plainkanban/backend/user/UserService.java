package plainkanban.backend.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public void signUp(UserDto userDto) {
    UserEntity userEntity = UserEntity.builder()
        .name(userDto.getName())
        .email(userDto.getEmail())
        .password(userDto.getPassword())
        .build();

    userRepository.save(userEntity);
  }
}
