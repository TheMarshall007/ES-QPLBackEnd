package br.com.univali.gabby_leo_kallil.quiz.api.subject;

import br.com.univali.gabby_leo_kallil.quiz.api.subject.DTO.SubjectDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.subject.DTO.SubjectDTOUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping(value = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubjectDTOResponse> insert(@RequestParam String name){
        return ResponseEntity.ok(subjectService.insert(name).getDTOResponse());
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubjectDTOResponse> update(@RequestBody SubjectDTOUpdate dto){
        return ResponseEntity.ok(subjectService.update(dto).getDTOResponse());
    }

    @GetMapping(value = "/find_all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SubjectDTOResponse>> findAll(){
        return ResponseEntity.ok(subjectService.findAll());
    }

}
