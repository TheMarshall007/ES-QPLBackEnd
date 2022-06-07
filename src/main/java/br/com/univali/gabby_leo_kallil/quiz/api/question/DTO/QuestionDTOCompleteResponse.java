package br.com.univali.gabby_leo_kallil.quiz.api.question.DTO;

import br.com.univali.gabby_leo_kallil.quiz.api.access.user.DTO.UserDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.trail.Difficulty;
import br.com.univali.gabby_leo_kallil.quiz.api.subject.DTO.SubjectDTOResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class QuestionDTOCompleteResponse {

    private Integer id;
    private String question;
    private String[] answers;
    private SubjectDTOResponse subject;
    private UserDTOResponse professor;
    private String correctAnswer;

}
