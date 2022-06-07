package br.com.univali.gabby_leo_kallil.quiz.api.phase.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class PhaseDTOInsert {

    private Integer index;
    private List<Integer> questionsIds;

}
