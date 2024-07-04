package company.cryo.crm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import company.cryo.crm.model.Users;
import company.cryo.crm.repository.UsersRepository;

/**
 * Objectif : récuzpérer les données en BDD du user associé à un username
 */
public class MonUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		
		Users user = this.userRepository.findByEmail(username);
		if (null == user) {
			throw new UsernameNotFoundException("Je ne vous reconnais pas !");
		}
		return new MonUserDetails(user);
	}

}
