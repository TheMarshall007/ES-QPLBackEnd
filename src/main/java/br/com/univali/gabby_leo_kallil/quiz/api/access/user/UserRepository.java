package br.com.univali.gabby_leo_kallil.quiz.api.access.user;

import br.com.univali.gabby_leo_kallil.quiz.api.access.user.DTO.UserDTOResponse;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User u where u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u where u.email = :email")
    User findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u where u.registration = :registration")
    User findByCPF(@Param("registration") String registration);

    @Query(value = "SELECT u.* from \"user\" u "+
            "inner join user_roles ur on u.id = ur.user_id and ur.role_id = :role", nativeQuery=true)
    List<User> findAllUsersByRole(@Param("role") BigInteger role);

    @Query("SELECT u FROM User u")
    List<User> findAllUsers();

    @Transactional
    @Query(value = "UPDATE \"user\" SET email = :email WHERE id = :id", nativeQuery = true)
    @Modifying
    void updateEmail(@Param("email") String email, @Param("id") Integer id);

    @Transactional
    @Query(value = "UPDATE \"user\" SET cpf = :cpf WHERE id = :id", nativeQuery = true)
    @Modifying
    void updateCPF(@Param("cpf") String cpf, @Param("id") Integer id);

}
