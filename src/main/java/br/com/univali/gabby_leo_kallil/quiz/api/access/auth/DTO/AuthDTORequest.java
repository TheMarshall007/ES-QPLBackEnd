package br.com.univali.gabby_leo_kallil.quiz.api.access.auth.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @NoArgsConstructor
public class AuthDTORequest {

    private String username;
    private String password;

}
