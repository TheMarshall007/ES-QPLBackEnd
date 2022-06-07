package br.com.univali.gabby_leo_kallil.quiz.api.subject;

import br.com.univali.gabby_leo_kallil.quiz.api.subject.DTO.SubjectDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.subject.DTO.SubjectDTOUpdate;
import br.com.univali.gabby_leo_kallil.quiz.security.exception.WarningException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public Subject insert(String name){
        Subject subject = new Subject();
        subject.setName(name);
        return subjectRepository.save(subject);
    }

    public Subject update(SubjectDTOUpdate dto){
        Subject subject = findById(dto.getId());
        if(dto.getName() != null){
            subject.setName(dto.getName());
        }
        return subjectRepository.save(subject);
    }

    public Subject findById(Integer id){
        Optional<Subject> opt = subjectRepository.findById(id);
        if(opt.isEmpty()){
            throw new WarningException("Disciplina não foi encontrada");
        }
        return opt.get();
    }

    public List<SubjectDTOResponse> findAll(){
        return StreamSupport.stream(subjectRepository.findAll().spliterator(), false)
                .map(Subject::getDTOResponse).collect(Collectors.toList());
    }

}
