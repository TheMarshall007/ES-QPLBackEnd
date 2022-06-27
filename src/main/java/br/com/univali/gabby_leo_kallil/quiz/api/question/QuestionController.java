package br.com.univali.gabby_leo_kallil.quiz.api.question;

import br.com.univali.gabby_leo_kallil.quiz.api.question.DTO.*;
import br.com.univali.gabby_leo_kallil.quiz.component.pagination.PaginationDTOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping(value = "/insert", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDTOResponse> insert(@RequestBody QuestionDTOInsert dto) {
        return ResponseEntity.ok(questionService.insert(dto).getDTOResponse());
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDTOResponse> update(@RequestBody QuestionDTOUpdate dto) {
        return ResponseEntity.ok(questionService.update(dto).getDTOResponse());
    }

    @GetMapping(value = "/find_by_id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDTOCompleteResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(questionService.findById(Integer.parseInt(id)).getDTOCompleteResponse());
    }

    @PostMapping(value = "/pagination", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaginationDTOResponse<QuestionDTOResponse>> pagination(@RequestBody QuestionDTOSearch dto) {
        return ResponseEntity.ok(questionService.pagination(dto));
    }


}
