package br.com.univali.gabby_leo_kallil.quiz.api.access.user.DTO;

import br.com.univali.gabby_leo_kallil.quiz.api.access.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserDTOResponse {

    private Integer id;
    private String username;
    private String email;
    private String registration;
    private Boolean active;
    private Set<Role> roles;

}
