package br.com.univali.gabby_leo_kallil.quiz.security.exception.handler;

import br.com.univali.gabby_leo_kallil.quiz.security.exception.DeepenException;
import br.com.univali.gabby_leo_kallil.quiz.security.exception.ErrorException;
import br.com.univali.gabby_leo_kallil.quiz.security.exception.ResponseLog;
import br.com.univali.gabby_leo_kallil.quiz.security.exception.WarningException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalControllerExceptionHandler {
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<ResponseLog> handleIllegalArgumentException(IllegalArgumentException e) {
		return createResponseLog(new WarningException(e.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = WarningException.class)
	public ResponseEntity<ResponseLog> handleWarningException(WarningException e) {
		return createResponseLog(e, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = ErrorException.class)
	public ResponseEntity<ResponseLog> handleErrorException(ErrorException e) {
		return createResponseLog(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<ResponseLog> createResponseLog(DeepenException e, HttpStatus status) {
		return new ResponseEntity<ResponseLog>(new ResponseLog(e.getMessage()), status);
	}

}
