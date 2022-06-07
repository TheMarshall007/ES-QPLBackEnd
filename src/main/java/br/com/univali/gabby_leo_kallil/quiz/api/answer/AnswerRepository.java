package br.com.univali.gabby_leo_kallil.quiz.api.answer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Integer> {

    @Query(value = "SELECT a FROM Answer a WHERE a.trail.id = :trail_id AND a.user.id = :student_id")
    List<Answer> findAllByTrailAndUserId(@Param("trail_id") Integer trailId,
                                         @Param("student_id") Integer studentId);

}
