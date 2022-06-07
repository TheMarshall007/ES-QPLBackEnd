package br.com.univali.gabby_leo_kallil.quiz.api.question;

import br.com.univali.gabby_leo_kallil.quiz.api.access.user.User;
import br.com.univali.gabby_leo_kallil.quiz.api.answer.Answer;
import br.com.univali.gabby_leo_kallil.quiz.api.question.DTO.QuestionDTOCompleteResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.question.DTO.QuestionDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.subject.Subject;
import br.com.univali.gabby_leo_kallil.quiz.api.phase.Phase;
import br.com.univali.gabby_leo_kallil.quiz.api.trail.Difficulty;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "question")
@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        )
})
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_generator")
    @SequenceGenerator(name="question_generator", sequenceName="question_id_seq", allocationSize=1)
    @Column(updatable=false, nullable=false)
    private Integer id;

    @NotNull
    @NotBlank
    @Column(columnDefinition="TEXT")
    private String question;

    @NotEmpty
    @Type(type = "string-array")
    @Column(
            name = "answers",
            columnDefinition = "text[]"
    )
    private String[] answers;

    @NotNull
    @NotBlank
    @Column(columnDefinition="TEXT")
    private String correctAnswer;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private User professor;

    @ManyToMany
    @JoinTable(
            name="phase_question",
            joinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "phase_id", referencedColumnName = "id")
    )
    private List<Phase> phases;

    @OneToMany
    private List<Answer> studentsAnswers;

    public QuestionDTOResponse getDTOResponse(){
        QuestionDTOResponse dto = new QuestionDTOResponse();
        dto.setId(getId());
        dto.setQuestion(getQuestion());
        dto.setAnswers(getAnswers());
        dto.setSubject(getSubject().getDTOResponse());
        dto.setProfessor(getProfessor().getDTOResponse());
        return dto;
    }
    public QuestionDTOCompleteResponse getDTOCompleteResponse(){
        QuestionDTOCompleteResponse dto = new QuestionDTOCompleteResponse();
        dto.setId(getId());
        dto.setQuestion(getQuestion());
        dto.setAnswers(getAnswers());
        dto.setSubject(getSubject().getDTOResponse());
        dto.setProfessor(getProfessor().getDTOResponse());
        dto.setCorrectAnswer(getCorrectAnswer());
        return dto;
    }

}
