package br.com.univali.gabby_leo_kallil.quiz.api.phase;

import br.com.univali.gabby_leo_kallil.quiz.api.answer.DTO.AnswerDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.phase.DTO.PhaseDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.question.DTO.QuestionDTOCompleteResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.question.DTO.QuestionDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.question.Question;
import br.com.univali.gabby_leo_kallil.quiz.api.trail.Trail;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "phase")
@Data
public class Phase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trial_generator")
    @SequenceGenerator(name="trial_generator", sequenceName="trial_id_seq", allocationSize=1)
    @Column(updatable=false, nullable=false)
    private Integer id;

    @NotNull
    private Integer index;

    @ManyToOne
    @JoinColumn(name = "trail_id")
    private Trail trail;

    @ManyToMany
    @JoinTable(
            name="phase_question",
            joinColumns = @JoinColumn(name = "phase_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id")
    )
    private List<Question> questions;

    public PhaseDTOResponse<QuestionDTOResponse> getDTOResponseToAnswer(){
        PhaseDTOResponse<QuestionDTOResponse> dto = new PhaseDTOResponse<>();
        dto.setId(getId());
        dto.setIndex(getIndex());
        dto.setQuestions(getQuestions().stream().map(Question::getDTOResponse).collect(Collectors.toList()));
        return dto;
    }

    public PhaseDTOResponse<QuestionDTOCompleteResponse> getDTOResponseToReview(){
        PhaseDTOResponse<QuestionDTOCompleteResponse> dto = new PhaseDTOResponse<>();
        dto.setId(getId());
        dto.setIndex(getIndex());
        dto.setQuestions(getQuestions().stream().map(Question::getDTOCompleteResponse).collect(Collectors.toList()));
        return dto;
    }

}
