package br.com.univali.gabby_leo_kallil.quiz.api.trail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrailRepository extends CrudRepository<Trail, Integer> {

    Page<Trail> findAll(Pageable pageable);
    Page<Trail> findAllByNameLikeIgnoreCase(String name, Pageable pageable);
    Page<Trail> findAllByDifficulty(Difficulty difficulty, Pageable pageable);
    Page<Trail> findAllByNameLikeIgnoreCaseAndDifficulty(String name, Difficulty difficulty, Pageable pageable);

}
