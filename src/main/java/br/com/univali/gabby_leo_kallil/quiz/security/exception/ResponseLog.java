package br.com.univali.gabby_leo_kallil.quiz.security.exception;

public class ResponseLog {

	private String log;
	
	public ResponseLog(){
		
	}
	
	public ResponseLog(String log){
		this.log = log;
	}
	
	public String getLog(){
		return log;
	}
	
	public void setLog(String log){
		this.log = log;
	}
}
