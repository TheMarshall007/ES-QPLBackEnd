package br.com.univali.gabby_leo_kallil.quiz.api.trail.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TrailDTOResponseSimple {

    private Integer id;
    private String name;
    private Integer phaseQuantity;


}
