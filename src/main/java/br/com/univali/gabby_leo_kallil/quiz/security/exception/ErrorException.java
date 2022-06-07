package br.com.univali.gabby_leo_kallil.quiz.security.exception;

public class ErrorException extends DeepenException{

	private static final long serialVersionUID = 1L;

	public ErrorException(String message, Throwable e) {
		super(message, e);
	}
	
}
