package br.com.univali.gabby_leo_kallil.quiz.api.trail.DTO;

import br.com.univali.gabby_leo_kallil.quiz.api.trail.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TrailDTOSearch {

    private String name;
    private Difficulty difficulty;
    private Integer page;

}
