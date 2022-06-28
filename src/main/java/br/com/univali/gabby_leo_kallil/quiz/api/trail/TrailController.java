package br.com.univali.gabby_leo_kallil.quiz.api.trail;

import br.com.univali.gabby_leo_kallil.quiz.api.answer.DTO.AnswerDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.question.DTO.QuestionDTOCompleteResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.question.DTO.QuestionDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.question.DTO.QuestionDTOSearch;
import br.com.univali.gabby_leo_kallil.quiz.api.trail.DTO.*;
import br.com.univali.gabby_leo_kallil.quiz.component.pagination.PaginationDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.security.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/trail")
public class TrailController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private TrailService trailService;


    @PostMapping(value = "/insert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<TrailDTOResponse<QuestionDTOCompleteResponse>> insert(@RequestBody TrailDTOInsert dto) {
        return ResponseEntity.ok(trailService.insert(dto).getDTOResponseToReview());
    }

    @PostMapping(value = "/answer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<AnswerDTOResponse>> answer(@RequestHeader("Authorization") String token,
                                                          @RequestBody TrailDTOAnswer dto) {
        return ResponseEntity.ok(trailService.answer(dto, jwtUtils.getUserIdFromJwtToken(token)));
    }

    @PostMapping(value = "/pagination", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaginationDTOResponse<TrailDTOResponseSimple>> pagination(@RequestBody TrailDTOSearch dto) {
        return ResponseEntity.ok(trailService.pagination(dto));
    }

    @GetMapping(value = "/get_by_trail_id", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrailDTOResponse<QuestionDTOResponse>> getTrailById(@RequestParam String id) {
        return ResponseEntity.ok(trailService.findById(Integer.parseInt(id)).getDTOResponse());
    }

    @GetMapping(value = "/get_results_by_trail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<List<TrailDTOResult>> getResultsByTrail(@PathVariable String id) {
        return ResponseEntity.ok(trailService.findById(Integer.parseInt(id)).getResults());
    }



}
