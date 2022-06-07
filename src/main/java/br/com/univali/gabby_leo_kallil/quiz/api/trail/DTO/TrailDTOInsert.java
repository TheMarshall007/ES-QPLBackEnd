package br.com.univali.gabby_leo_kallil.quiz.api.trail.DTO;

import br.com.univali.gabby_leo_kallil.quiz.api.phase.DTO.PhaseDTOInsert;
import br.com.univali.gabby_leo_kallil.quiz.api.trail.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class TrailDTOInsert {

    private String name;
    private Difficulty difficulty;
    private List<PhaseDTOInsert> phases;

}
