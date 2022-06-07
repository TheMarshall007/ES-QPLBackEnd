package br.com.univali.gabby_leo_kallil.quiz.security.exception;

public abstract class DeepenException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DeepenException() {
		super();
	}

	public DeepenException(String message) {
		super(message);
	}

	public DeepenException(String message, Throwable t) {
		super(message, t);
	}

}
