package uz.springsecurityapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.springsecurityapp.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepo extends JpaRepository<Authority, String> {

    Authority findByName(String name);
}
