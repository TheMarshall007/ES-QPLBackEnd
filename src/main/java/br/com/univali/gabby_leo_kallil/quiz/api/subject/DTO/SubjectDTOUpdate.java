package br.com.univali.gabby_leo_kallil.quiz.api.subject.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class SubjectDTOUpdate {

    @Schema(description = "Identificador da disciplina",
            example = "1", required = true)
    private Integer id;
    @Schema(description = "Nome da disciplina",
            example = "Matemática")
    private String name;

}
