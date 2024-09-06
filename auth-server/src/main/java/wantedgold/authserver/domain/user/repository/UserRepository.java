package wantedgold.authserver.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wantedgold.authserver.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
