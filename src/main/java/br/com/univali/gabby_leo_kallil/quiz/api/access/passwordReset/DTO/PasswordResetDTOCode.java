package br.com.univali.gabby_leo_kallil.quiz.api.access.passwordReset.DTO;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor
public class PasswordResetDTOCode {

	private String email;
	private String code;
}
