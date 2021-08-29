package project.dailynail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.dailynail.models.entities.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String > {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("SELECT u.fullName FROM User u " +
            "WHERE u.email = :email")
       Optional<String> getFullNameByEmail(@Param("email") String email);

    @Modifying
    @Query("UPDATE User u " +
            "SET u.fullName = :fullName " +
            "WHERE u.id = :id")
    void updateUserFullNameById(@Param("fullName") String fullName, @Param("id") String id);

    @Modifying
    @Query("UPDATE User u " +
            "SET u.email = :email " +
            "WHERE u.id = :id")
    void updateUserEmailById(@Param("email") String email, @Param("id") String id);

    @Query("SELECT u.id AS id, u.fullName AS fullName FROM User u " +
            "WHERE u.email = :email")
    List<Map<String, String>> getIdAndFullNameByEmail(@Param("email") String email);

    @Query("SELECT u.password FROM User u " +
            "WHERE u.email = :email")
    Optional<String> getPasswordByEmail(@Param("email") String email);

    @Modifying
    @Query("UPDATE User u " +
            "SET u.password = :newPassword " +
            "WHERE u.email = :email")
    void updatePasswordByEmail(@Param("newPassword") String newPassword, @Param("email") String principalEmail);
}
