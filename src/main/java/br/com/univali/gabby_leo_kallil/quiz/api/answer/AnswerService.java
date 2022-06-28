package br.com.univali.gabby_leo_kallil.quiz.api.answer;

import br.com.univali.gabby_leo_kallil.quiz.api.access.user.UserService;
import br.com.univali.gabby_leo_kallil.quiz.api.answer.DTO.AnswerDTOInsert;
import br.com.univali.gabby_leo_kallil.quiz.api.answer.DTO.AnswerDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.question.Question;
import br.com.univali.gabby_leo_kallil.quiz.api.question.QuestionService;
import br.com.univali.gabby_leo_kallil.quiz.api.trail.TrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    @Lazy
    private TrailService trailService;

    @Autowired
    private UserService userService;

    public Answer insert(AnswerDTOInsert dto){
        Answer answer = new Answer();
        Question question = questionService.findById(dto.getQuestionId());
        answer.setQuestion(question);
        answer.setTrail(trailService.findById(dto.getTrailId()));
        answer.setUser(userService.findById(dto.getStudentId()));
        answer.setSentAnswer(dto.getSentAnswer());
        answer.setIsCorrect(question.getCorrectAnswer().equals(dto.getSentAnswer()));
        return answerRepository.save(answer);
    }

    public List<AnswerDTOResponse> getResults(Integer trailId, Integer studentId){
        return answerRepository.findAllByTrailAndUserId(trailId, studentId).stream().map(Answer::getDTOResponse)
                .collect(Collectors.toList());
    }

}
