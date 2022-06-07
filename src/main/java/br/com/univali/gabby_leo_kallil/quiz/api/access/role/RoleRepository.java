package br.com.univali.gabby_leo_kallil.quiz.api.access.role;

import br.com.univali.gabby_leo_kallil.quiz.api.access.enum_role.EnumRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Role findByName(@Param("name") EnumRole name);

}
