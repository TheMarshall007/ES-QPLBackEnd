package br.com.univali.gabby_leo_kallil.quiz.api.phase;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhaseRepository extends CrudRepository<Phase, Integer> {
}
