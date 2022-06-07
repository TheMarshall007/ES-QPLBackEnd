package br.com.univali.gabby_leo_kallil.quiz.api.question;

import br.com.univali.gabby_leo_kallil.quiz.api.access.user.UserService;
import br.com.univali.gabby_leo_kallil.quiz.api.question.DTO.*;
import br.com.univali.gabby_leo_kallil.quiz.api.subject.SubjectService;
import br.com.univali.gabby_leo_kallil.quiz.component.pagination.PaginationDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.security.exception.WarningException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserService userService;

    public Question insert(QuestionDTOInsert dto){
        Question question = new Question();
        question.setQuestion(dto.getQuestion());
        question.setAnswers(dto.getAnswers());
        question.setCorrectAnswer(question.getCorrectAnswer());
        question.setProfessor(userService.findById(dto.getProfessorId()));
        question.setSubject(subjectService.findById(dto.getSubjectId()));
        return questionRepository.save(question);
    }

    public Question update(QuestionDTOUpdate dto){
        Question question = findById(dto.getId());
        question.setQuestion(dto.getQuestion());
        question.setAnswers(dto.getAnswers());
        question.setCorrectAnswer(question.getCorrectAnswer());
        question.setSubject(subjectService.findById(dto.getSubjectId()));
        return questionRepository.save(question);
    }

    public Question findById(Integer id){
        Optional<Question> opt = questionRepository.findById(id);
        if(opt.isEmpty()){
            throw new WarningException("Pergunta não foi encontrada");
        }
        return opt.get();
    }

    public PaginationDTOResponse<QuestionDTOResponse> pagination(QuestionDTOSearch dto){
        PageRequest pageable = PageRequest.of(dto.getPage(), 15, Sort.Direction.ASC, "id");
        Page<Question> questionPage;
        if(dto.getSubjectId() != null){
            if(dto.getProfessorId() != null){
                questionPage = questionRepository.findAllByProfessorAndSubject(dto.getProfessorId(),
                        dto.getSubjectId(), pageable);
            }else{
                questionPage = questionRepository.findAllBySubject(dto.getSubjectId(), pageable);
            }
        }else{
            if(dto.getProfessorId() != null){
                questionPage = questionRepository.findAllByProfessor(dto.getProfessorId(), pageable);
            }else{
                questionPage = questionRepository.findAllOrderById(pageable);
            }
        }
        return new PaginationDTOResponse<>(
                questionPage.getContent().stream().map(Question::getDTOResponse).collect(Collectors.toList()),
                dto.getPage(), questionPage.getNumberOfElements(), questionPage.getTotalPages());
    }

}
