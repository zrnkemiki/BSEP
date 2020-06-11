package ftn.bsep.pkiapp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.bsep.pkiapp.dto.UserDTO;
import ftn.bsep.pkiapp.model.User;
import ftn.bsep.pkiapp.model.UserStatus;
import ftn.bsep.pkiapp.services.UserServiceImpl;




@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	UserServiceImpl userService;
	
	@PreAuthorize("hasAuthority('EMPLOYEE')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDTO>> getAll() {
		List<User> users = userService.findAll();
		List<UserDTO> usersDTO = new ArrayList<>();
		for (User u : users) {
			usersDTO.add(new UserDTO(u));
		}
		
		try {
			return new ResponseEntity<>(usersDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> getUser(@PathVariable("username") String username) {
		User user = userService.findByUsername(username);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		UserDTO userDTO = new UserDTO(user);
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}

	// Create new user.
	@PostMapping(consumes = "application/json")
	public ResponseEntity<User> addUser(@RequestBody UserDTO userDTO) {
		User user = userService.saveUser(userDTO);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
	/*
	@PutMapping()
	public ResponseEntity<User> updateUser(@RequestBody UserDTO userDTO, Principal principal) {
		return new ResponseEntity<>(userService.update(userDTO, principal), HttpStatus.OK);
	}
	*/



	@GetMapping(value = "/activate/{uuid}")
	public String activateUser(@PathVariable("uuid") String uuid) {
		User user = userService.findByuuid(uuid);
		if (user != null) {
			if (user.getUserStatus() == UserStatus.PENDING) {
				user.setUserStatus(UserStatus.ACTIVATED);
				userService.saveUser(user);
				return String.format("<p>Succesfully activated! <p> <p>%s welcome to site!<p>", user.getUsername());
			} else {
				return "User allready activated!";
			}
		}
		return "Bad activation link!";
	}

}
