package br.com.univali.gabby_leo_kallil.quiz.api.question;

import br.com.univali.gabby_leo_kallil.quiz.api.trail.Difficulty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer> {

    @Query(value = "SELECT q FROM Question q ORDER BY q.id")
    Page<Question> findAllOrderById(Pageable pageable);

    @Query(value = "SELECT q FROM Question q WHERE q.professor.id = :professor_id ORDER BY q.id")
    Page<Question> findAllByProfessor(@Param("professor_id") Integer professorId,
                                      Pageable pageable);

    @Query(value = "SELECT q FROM Question q WHERE q.subject.id = :subject_id ORDER BY q.id")
    Page<Question> findAllBySubject(@Param("subject_id") Integer subjectId,
                                    Pageable pageable);

    @Query(value = "SELECT q FROM Question q WHERE q.professor.id = :professor_id AND q.subject.id = :subject_id ORDER BY q.id")
    Page<Question> findAllByProfessorAndSubject(@Param("professor_id") Integer professorId,
                                                @Param("subject_id") Integer subjectId,
                                                Pageable pageable);

}
