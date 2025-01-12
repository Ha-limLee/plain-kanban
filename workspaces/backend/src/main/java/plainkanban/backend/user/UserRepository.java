package plainkanban.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  List<UserEntity> findByEmail(String email);
}
