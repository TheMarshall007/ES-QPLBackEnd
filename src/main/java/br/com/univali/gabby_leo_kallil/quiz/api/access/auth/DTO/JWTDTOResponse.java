package br.com.univali.gabby_leo_kallil.quiz.api.access.auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTDTOResponse {

    private String token;
    private String type = "Bearer";
    private Integer id;
    private String username;
    private String email;
    private String color;
    private Boolean firstAccess;
    private List<String> roles;

}
