package ftn.bsep.pkiapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ftn.bsep.pkiapp.dto.UserDTO;
import ftn.bsep.pkiapp.exception.UserDoesntExistException;
import ftn.bsep.pkiapp.model.User;
import ftn.bsep.pkiapp.model.UserStatus;
import ftn.bsep.pkiapp.repositories.UserRepository;



@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	EMailService emailService;
	
	@Override
	public User findByuuid(String uuid) {
		return userRepository.findByuuid(uuid);
	}
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public User findOne(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public User findByID(Long id) {
		Optional<User> rets = userRepository.findById(id);
		if (!rets.isPresent()) {
			throw new UserDoesntExistException();
		}
		return rets.get();
	}
	
	public User saveUser(UserDTO userDTO) {
		User user = new User(userDTO);
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		//USER TYPE?
		user.setUserStatus(UserStatus.PENDING);
		//emailService.sendMail(user, "Activation link",
		//		"Please follow the link below to activate \nhttp://localhost:8080/api/user/activate/"
		//				+ user.getUuid());
		
		userRepository.save(user);
		return user;
	}
	

	
	public User saveUser(User user) {
		userRepository.save(user);
		return user;
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}



	public boolean existsUsername(String username) {
		User user = userRepository.findByUsername(username);
		return user != null;
	}

	public User getUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			//throw new ResourceNotFoundException();
		}
		return user;
	}
	
	public User getLoggedUser(){
		try {
			return getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		} catch (Exception e) {
			if (e instanceof NullPointerException ) {
				return null;
			} 
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public boolean saveAdmin(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
