package br.com.univali.gabby_leo_kallil.quiz.api.trail;

import br.com.univali.gabby_leo_kallil.quiz.api.answer.Answer;
import br.com.univali.gabby_leo_kallil.quiz.api.phase.Phase;
import br.com.univali.gabby_leo_kallil.quiz.api.question.DTO.QuestionDTOCompleteResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.question.DTO.QuestionDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.question.Question;
import br.com.univali.gabby_leo_kallil.quiz.api.trail.DTO.TrailDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.trail.DTO.TrailDTOResponseSimple;
import br.com.univali.gabby_leo_kallil.quiz.api.trail.DTO.TrailDTOResult;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "trail")
@Data
public class Trail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_generator")
    @SequenceGenerator(name="quiz_generator", sequenceName="quiz_id_seq", allocationSize=1)
    @Column(updatable=false, nullable=false)
    private Integer id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private Difficulty difficulty;

    @OneToMany(mappedBy = "trail")
    @OrderBy("index asc")
    private List<Phase> phases;


    public TrailDTOResponse<QuestionDTOResponse> getDTOResponseToAnswer(){
        TrailDTOResponse<QuestionDTOResponse> dto = new TrailDTOResponse<>();
        dto.setId(getId());
        dto.setName(getName());
        dto.setDifficulty(getDifficulty());
        dto.setPhases(getPhases().stream().map(Phase::getDTOResponseToAnswer).collect(Collectors.toList()));
        return dto;
    }

    public TrailDTOResponse<QuestionDTOCompleteResponse> getDTOResponseToReview(){
        TrailDTOResponse<QuestionDTOCompleteResponse> dto = new TrailDTOResponse<>();
        dto.setId(getId());
        dto.setName(getName());
        dto.setDifficulty(getDifficulty());
        dto.setPhases(getPhases().stream().map(Phase::getDTOResponseToReview).collect(Collectors.toList()));
        return dto;
    }

    public TrailDTOResponseSimple getDTOResponseSimple(){
        TrailDTOResponseSimple dto = new TrailDTOResponseSimple();
        dto.setId(getId());
        dto.setName(getName());
        dto.setPhaseQuantity(getPhases().size());
        return dto;
    }

    public List<TrailDTOResult> getResults(){
        Map<Integer, TrailDTOResult> results = new HashMap<>();
        for(Phase phase : getPhases()){
            for(Question question : phase.getQuestions()){
                for(Answer answer : question.getStudentsAnswers()){
                    Integer studentId = answer.getUser().getId();
                    if(results.containsKey(studentId)){
                        if(answer.getIsCorrect()) {
                            results.get(studentId).addCorrectAnswers();
                        }
                    }else{
                        TrailDTOResult r = new TrailDTOResult();
                        r.setStudentName(answer.getUser().getUsername());
                        if(answer.getIsCorrect()) {
                            r.addCorrectAnswers();
                        }
                        results.put(studentId, r);
                    }
                }
            }
        }
        return new ArrayList<>(results.values());
    }

}

