package br.com.univali.gabby_leo_kallil.quiz.api.access.passwordReset.DTO;

import br.com.univali.gabby_leo_kallil.quiz.security.exception.ResponseLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class PasswordResetDTOResponse {

	private Integer id;
	private String email;
	private String code;
	private String token;
	private ResponseLog log;
}
