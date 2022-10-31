package project.dailynail.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.dailynail.models.entities.UserEntity;
import project.dailynail.models.entities.UserRoleEntity;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String > {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
    @Query("SELECT u.fullName FROM UserEntity u " +
            "WHERE u.email = :email")
       Optional<String> getFullNameByEmail(@Param("email") String email);

    @Modifying
    @Query("UPDATE UserEntity u " +
            "SET u.fullName = :fullName " +
            "WHERE u.id = :id")
    void updateUserFullNameById(@Param("fullName") String fullName, @Param("id") String id);

    @Modifying
    @Query("UPDATE UserEntity u " +
            "SET u.email = :email " +
            "WHERE u.id = :id")
    void updateUserEmailById(@Param("email") String email, @Param("id") String id);

    @Query("SELECT u.id AS id, u.fullName AS fullName FROM UserEntity u " +
            "WHERE u.email = :email")
    List<Map<String, String>> getIdAndFullNameByEmail(@Param("email") String email);

    @Query("SELECT u.password FROM UserEntity u " +
            "WHERE u.email = :email")
    Optional<String> getPasswordByEmail(@Param("email") String email);

    @Modifying
    @Query("UPDATE UserEntity u " +
            "SET u.password = :newPassword " +
            "WHERE u.email = :email")
    void updatePasswordByEmail(@Param("newPassword") String newPassword, @Param("email") String principalEmail);

    @Query("SELECT u.fullName FROM UserEntity u " +
            "WHERE size(u.roles) > 1")
    List<String> findAllUserFullNamesByWhoHasMoreThanOneRole();

    @Query("SELECT u FROM UserEntity u " +
            "WHERE u.email NOT LIKE 'admin@admin.bg' " +
            "ORDER BY size(u.roles) DESC ")
    List<UserEntity> findAllUsersOrderByRolesDesc();


    @Query("SELECT u FROM UserEntity u " +
            "WHERE u.email NOT IN ('admin@admin.bg', 'editor@editor.bg', 'reporter@reporter.bg', 'user@user.bg') " +
            "ORDER BY size(u.roles) DESC ")
    List<UserEntity> findAllUsersExceptInitials();

    @Query("SELECT u FROM UserEntity u " +
            "JOIN FETCH u.roles " +
            "WHERE u.id = :id")
    UserEntity findByIdFetch(@Param("id") String id);
}
