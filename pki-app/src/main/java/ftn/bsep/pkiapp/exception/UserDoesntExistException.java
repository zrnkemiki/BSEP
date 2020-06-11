package ftn.bsep.pkiapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class UserDoesntExistException extends RuntimeException {
	
	public UserDoesntExistException() {
		  super("Korisnik sa tim ID-em ne postoji!");
	  }
	
	private static final long serialVersionUID = 1L;
}
