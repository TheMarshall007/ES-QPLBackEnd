package br.com.univali.gabby_leo_kallil.quiz.api.trail.DTO;

import lombok.Data;

import java.util.List;

@Data
public class TrailDTOAnswer {

    private Integer trailId;
    private List<TrailDTOQuestionAnswer> answers;

}
