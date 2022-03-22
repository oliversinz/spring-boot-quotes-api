package spring.quotes.api.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Boolean existsByEmail(String email);

    Optional<AppUser> findByEmail(String email);

}
