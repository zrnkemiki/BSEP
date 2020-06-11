package ftn.bsep.pkiapp.services;

import ftn.bsep.pkiapp.model.User;


public interface UserService {
	//public User saveUser(UserDTO userDTO);
	
	public boolean saveAdmin(User user);

	//public boolean saveAdmin(String username, String email, UserType userType, Long companyId);
	
	
	
	public User findByuuid(String uuid); 
	
	public User findByUsername(String username);
	
	public User getLoggedUser();

}
