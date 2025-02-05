package plainkanban.backend.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public void signUp(UserDto userDto) {
    UserEntity userEntity = UserEntity.builder()
        .name(userDto.getName())
        .email(userDto.getEmail())
        .password(passwordEncoder.encode(userDto.getPassword()))
        .build();

    userRepository.save(userEntity);
  }

  public Optional<UserEntity> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

}
