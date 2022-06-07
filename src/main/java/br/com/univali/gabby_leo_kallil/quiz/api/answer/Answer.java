package br.com.univali.gabby_leo_kallil.quiz.api.answer;

import br.com.univali.gabby_leo_kallil.quiz.api.access.user.User;
import br.com.univali.gabby_leo_kallil.quiz.api.answer.DTO.AnswerDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.question.Question;
import br.com.univali.gabby_leo_kallil.quiz.api.trail.Trail;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "answer")
@Data
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_generator")
    @SequenceGenerator(name="answer_generator", sequenceName="answer_id_seq", allocationSize=1)
    @Column(updatable=false, nullable=false)
    private Integer id;

    private Boolean isCorrect;

    private String sentAnswer;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "trail_id")
    private Trail trail;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User user;

    public AnswerDTOResponse getDTOResponse(){
        AnswerDTOResponse dto = new AnswerDTOResponse();
        dto.setCorrectAnswer(dto.getCorrectAnswer());
        dto.setIsCorrect(dto.getIsCorrect());
        dto.setSentAnswer(dto.getSentAnswer());
        dto.setIsCorrect(dto.getIsCorrect());
        dto.setQuestion(getQuestion().getQuestion());
        return dto;
    }


}
