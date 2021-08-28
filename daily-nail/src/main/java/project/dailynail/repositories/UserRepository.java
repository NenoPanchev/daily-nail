package project.dailynail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.dailynail.models.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String > {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("SELECT u.fullName FROM User u " +
            "WHERE u.email = :email")
    Optional<String> getFullNameByEmail(@Param("email") String email);

}
