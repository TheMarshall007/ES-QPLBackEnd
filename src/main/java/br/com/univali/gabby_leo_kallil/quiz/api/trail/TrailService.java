package br.com.univali.gabby_leo_kallil.quiz.api.trail;

import br.com.univali.gabby_leo_kallil.quiz.api.answer.Answer;
import br.com.univali.gabby_leo_kallil.quiz.api.answer.AnswerService;
import br.com.univali.gabby_leo_kallil.quiz.api.answer.DTO.AnswerDTOInsert;
import br.com.univali.gabby_leo_kallil.quiz.api.answer.DTO.AnswerDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.phase.DTO.PhaseDTOInsert;
import br.com.univali.gabby_leo_kallil.quiz.api.phase.Phase;
import br.com.univali.gabby_leo_kallil.quiz.api.phase.PhaseService;
import br.com.univali.gabby_leo_kallil.quiz.api.question.DTO.QuestionDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.question.DTO.QuestionDTOSearch;
import br.com.univali.gabby_leo_kallil.quiz.api.question.Question;
import br.com.univali.gabby_leo_kallil.quiz.api.question.QuestionService;
import br.com.univali.gabby_leo_kallil.quiz.api.trail.DTO.*;
import br.com.univali.gabby_leo_kallil.quiz.component.pagination.PaginationDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.security.exception.WarningException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrailService {

    @Autowired
    private TrailRepository trailRepository;

    @Autowired
    private PhaseService phaseService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    public Trail insert(TrailDTOInsert dto) {
        Trail trail = new Trail();
        trail.setName(dto.getName());
        trail.setDifficulty(dto.getDifficulty());
        trail = trailRepository.save(trail);
        List<Phase> phaseList = new LinkedList<>();
        for (PhaseDTOInsert phaseDTOInsert : dto.getPhases()) {
            phaseList.add(phaseService.insert(phaseDTOInsert, trail));
        }
        trail.setPhases(phaseList);
        return trailRepository.save(trail);
    }

    public PaginationDTOResponse<TrailDTOResponseSimple> pagination(TrailDTOSearch dto) {
        PageRequest pageable = PageRequest.of(dto.getPage(), 15, Sort.Direction.ASC, "id");
        Page<Trail> trailPage;
        if (dto.getName() != null) {
            if (dto.getDifficulty() != null) {
                trailPage = trailRepository.findAllByNameLikeIgnoreCaseAndDifficulty(dto.getName(), dto.getDifficulty(), pageable);
            } else {
                trailPage = trailRepository.findAllByNameLikeIgnoreCase(dto.getName(), pageable);
            }
        } else {
            if (dto.getDifficulty() != null) {
                trailPage = trailRepository.findAllByDifficulty(dto.getDifficulty(), pageable);
            } else {
                trailPage = trailRepository.findAll(pageable);
            }
        }
        return new PaginationDTOResponse<>(
                trailPage.getContent().stream().map(Trail::getDTOResponseSimple).collect(Collectors.toList()),
                dto.getPage(), trailPage.getNumberOfElements(), trailPage.getTotalPages());
    }

    public Trail findById(Integer id) {
        Optional<Trail> opt = trailRepository.findById(id);
        if (!opt.isPresent()) {
            throw new WarningException("Trilha não encontrada");
        }
        return opt.get();
    }

    public List<AnswerDTOResponse> answer(TrailDTOAnswer dto, Integer studentId) {
        List<Answer> responses = new LinkedList<>();
        Trail trail = findById(dto.getTrailId());
        for(TrailDTOQuestionAnswer dtoQuestionAnswer : dto.getAnswers()){
            Question question = questionService.findById(dtoQuestionAnswer.getQuestionId());
            AnswerDTOInsert insert = new AnswerDTOInsert();
            insert.setTrailId(trail.getId());
            insert.setQuestionId(question.getId());
            insert.setStudentId(studentId);
            insert.setSentAnswer(dtoQuestionAnswer.getAnswer());
            Answer answer = answerService.insert(insert);
            responses.add(answer);
            questionService.addAnswers(question, answer);
        }
        return responses.stream().map(Answer::getDTOResponse).collect(Collectors.toList());
    }

    public List<AnswerDTOResponse> getAnswers(Integer trailId, Integer studentId){
       return answerService.getResults(trailId, studentId);
    }

}
