package es.upm.miw.devops.repositories;
import es.upm.miw.devops.code.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}

