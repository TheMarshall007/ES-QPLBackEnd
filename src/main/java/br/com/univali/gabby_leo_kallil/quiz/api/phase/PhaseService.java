package br.com.univali.gabby_leo_kallil.quiz.api.phase;

import br.com.univali.gabby_leo_kallil.quiz.api.phase.DTO.PhaseDTOInsert;
import br.com.univali.gabby_leo_kallil.quiz.api.question.QuestionService;
import br.com.univali.gabby_leo_kallil.quiz.api.trail.Trail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PhaseService {

    @Autowired
    private PhaseRepository phaseRepository;

    @Autowired
    private QuestionService questionService;

    public Phase insert(PhaseDTOInsert dto, Trail trail){
        Phase phase = new Phase();
        phase.setIndex(dto.getIndex());
        phase.setQuestions(dto.getQuestionsIds().stream().map(index -> questionService.findById(index)).collect(Collectors.toList()));
        phase.setTrail(trail);
        return phaseRepository.save(phase);
    }

}
