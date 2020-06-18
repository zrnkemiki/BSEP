package ftn.bsep.pkiapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.bsep.pkiapp.dto.LoginDTO;
import ftn.bsep.pkiapp.dto.UserDTO;
import ftn.bsep.pkiapp.security.TokenUtils;
import ftn.bsep.pkiapp.services.UserServiceImpl;




@RestController
@RequestMapping(value = "/login")
public class AuthenticationController {
	
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	UserServiceImpl userService;
		
	
	@PostMapping()
	public ResponseEntity<?> login(@RequestBody()LoginDTO loginDTO){
		System.out.println("Usao sam u funkciju login!\n");
		try {
        	// Perform the authentication
        	UsernamePasswordAuthenticationToken token = 
        			new UsernamePasswordAuthenticationToken(
					loginDTO.getUsername(), loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);            
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Reload user details so we can generate token
            UserDetails details = userDetailsService.
            		loadUserByUsername(loginDTO.getUsername());
            
            UserDTO user = new UserDTO(userService.getLoggedUser());
            
            user.setJWTToken(tokenUtils.generateToken(details));
            return new ResponseEntity<UserDTO>(
            		user, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
	}
	
	
	


}
