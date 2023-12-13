package gr.aueb.cf.petcity.repository;

import gr.aueb.cf.petcity.model.Dog;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
    Optional<Dog> findById(Long id);
}
