package br.com.univali.gabby_leo_kallil.quiz.api.subject;

import br.com.univali.gabby_leo_kallil.quiz.api.question.Question;
import br.com.univali.gabby_leo_kallil.quiz.api.subject.DTO.SubjectDTOResponse;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "subject")
@Data
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_generator")
    @SequenceGenerator(name="subject_generator", sequenceName="subject_id_seq", allocationSize=1)
    @Column(updatable=false, nullable=false)
    private Integer id;

    @NotNull
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "subject")
    private List<Question> questions;

    public SubjectDTOResponse getDTOResponse(){
        SubjectDTOResponse dto = new SubjectDTOResponse();
        dto.setId(getId());
        dto.setName(getName());
        return dto;
    }

}
