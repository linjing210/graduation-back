package cn.edu.aiit.gradution.exception;

public class TransactionFailedException extends RuntimeException{
	public TransactionFailedException(){
		super();
	}

	public TransactionFailedException(String msg){
		super(msg);
	}
}
