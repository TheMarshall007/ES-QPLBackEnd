package br.com.univali.gabby_leo_kallil.quiz.api.access.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserDTOInsert {

    private String username;
    private String email;
    private String registration;

}
