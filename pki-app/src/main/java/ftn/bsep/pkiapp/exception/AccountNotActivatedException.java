package ftn.bsep.pkiapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class AccountNotActivatedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public AccountNotActivatedException(){
		  super("Account isnt activated.");
	  }
}
