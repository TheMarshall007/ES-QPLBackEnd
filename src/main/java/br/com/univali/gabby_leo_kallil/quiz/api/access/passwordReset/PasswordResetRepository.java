package br.com.univali.gabby_leo_kallil.quiz.api.access.passwordReset;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetRepository extends CrudRepository<PasswordReset, Integer>{
	
	@Query("SELECT pr from PasswordReset pr WHERE pr.email = :email AND pr.code = :code")
	public PasswordReset findOneByEmailAndCode(@Param("email") String email, @Param("code") String code);
	
}
