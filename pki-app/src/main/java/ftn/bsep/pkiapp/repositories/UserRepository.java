package ftn.bsep.pkiapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.bsep.pkiapp.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
	User findByUsernameAndPassword(String username, String password);
	
	List<User> findAll();

	@SuppressWarnings("unchecked")
	User save(User user);

	void deleteAll(); 
	
	User findByuuid(String uuid);
	
}
