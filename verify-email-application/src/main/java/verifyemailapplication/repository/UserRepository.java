package verifyemailapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import verifyemailapplication.entity.AppUser;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findById(Integer integer);

    Optional<AppUser> findByUsername(String username);
}
