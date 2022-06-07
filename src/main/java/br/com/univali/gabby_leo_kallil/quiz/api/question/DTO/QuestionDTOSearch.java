package br.com.univali.gabby_leo_kallil.quiz.api.question.DTO;

import br.com.univali.gabby_leo_kallil.quiz.api.trail.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class QuestionDTOSearch {

    private Integer professorId;
    private Integer subjectId;
    private Integer page;

}
