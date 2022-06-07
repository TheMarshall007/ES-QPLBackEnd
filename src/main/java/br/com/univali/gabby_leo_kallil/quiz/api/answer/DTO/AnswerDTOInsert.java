package br.com.univali.gabby_leo_kallil.quiz.api.answer.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AnswerDTOInsert {

    private Integer trailId;
    private Integer questionId;
    private Integer studentId;
    private String sentAnswer;

}
