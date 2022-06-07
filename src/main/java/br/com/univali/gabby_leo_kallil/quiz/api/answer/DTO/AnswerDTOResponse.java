package br.com.univali.gabby_leo_kallil.quiz.api.answer.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AnswerDTOResponse {

    private Integer id;
    private Boolean isCorrect;
    private String sentAnswer;
    private String correctAnswer;
    private String question;

}
