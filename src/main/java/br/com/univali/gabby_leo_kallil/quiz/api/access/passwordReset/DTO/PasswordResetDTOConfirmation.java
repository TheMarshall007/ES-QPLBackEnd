package br.com.univali.gabby_leo_kallil.quiz.api.access.passwordReset.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class PasswordResetDTOConfirmation {

	private String email;
	private String password;
}
