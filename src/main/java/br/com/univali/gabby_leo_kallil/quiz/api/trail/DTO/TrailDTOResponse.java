package br.com.univali.gabby_leo_kallil.quiz.api.trail.DTO;

import br.com.univali.gabby_leo_kallil.quiz.api.phase.DTO.PhaseDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.trail.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class TrailDTOResponse<T> {

    private Integer id;
    private String name;
    private Difficulty difficulty;
    private List<PhaseDTOResponse<T>> phases;

}
