package br.com.univali.gabby_leo_kallil.quiz.api.access.passwordReset;

import br.com.univali.gabby_leo_kallil.quiz.api.access.passwordReset.DTO.PasswordResetDTOCode;
import br.com.univali.gabby_leo_kallil.quiz.api.access.passwordReset.DTO.PasswordResetDTOConfirmation;
import br.com.univali.gabby_leo_kallil.quiz.api.access.passwordReset.DTO.PasswordResetDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.access.user.DTO.UserDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.security.exception.ResponseLog;
import br.com.univali.gabby_leo_kallil.quiz.security.exception.WarningException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("/access")
@RestController
public class PasswordResetController {

	@Autowired 
	private PasswordResetService accessService;
	
	@Transactional
	@CrossOrigin(value = "*", maxAge = 3600)
	@Operation(summary = "[PERMIT_ALL] Start the process to reset user password, send a code to user email.")
	@RequestMapping(method=RequestMethod.POST, value="/password_reset_request", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseLog> postPasswordResetRequest(@RequestParam String email){
		accessService.postPasswordResetRequest(email);
		return new ResponseEntity<>(new ResponseLog("Email de redefinição de senha enviado"), HttpStatus.OK);
	}
	
	@Transactional
	@CrossOrigin(value = "*", maxAge = 3600)
	@Operation(summary = "[PERMIT_ALL] Password reset confirm code received by email, to check user authenticity, received as response the token to reset the password.")
	@RequestMapping(method=RequestMethod.POST, value="/password_reset_code", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseLog> postResetPasswordCode(@RequestBody PasswordResetDTOCode passwordResetDTOCode){
		PasswordReset passwordReset = accessService.postPasswordResetCode(passwordResetDTOCode);
		if(passwordReset == null) {
			 throw new WarningException("Código de validação não encontrado");
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("Authorization", passwordReset.getToken());
	 
	    return ResponseEntity.ok().headers(responseHeaders).body(new ResponseLog("Código de autenticação informado corretamente"));
	}

	@Transactional
	@CrossOrigin(value = "*", maxAge = 3600)
	@Operation(summary = "[PERMIT_ALL] Password reset confirm code received by email, to check user authenticity, received as response the token to reset the password.")
	@RequestMapping(method=RequestMethod.POST, value="/password_reset_code_two", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PasswordResetDTOResponse> postResetPasswordCodeTwo(@RequestBody PasswordResetDTOCode passwordResetDTOCode){
		PasswordReset passwordReset = accessService.postPasswordResetCode(passwordResetDTOCode);
		if(passwordReset == null) {
			throw new WarningException("Código de validação não encontrado");
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Authorization", passwordReset.getToken());

		return new ResponseEntity<>(passwordReset.getPasswordResetDTOResponse(), responseHeaders, HttpStatus.OK);
	}
	
	@Transactional
	@CrossOrigin(value = "*", maxAge = 3600)
	@Operation(summary = "With temporary token, reset the user password to a valid new one.")
	@RequestMapping(method=RequestMethod.POST, value="/password_reset_confirmation", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTOResponse> postResetPassword(@RequestBody PasswordResetDTOConfirmation dto){
		return new ResponseEntity<>(accessService.postPasswordResetConfirmation(dto).getDTOResponse(), HttpStatus.OK);
	}
}