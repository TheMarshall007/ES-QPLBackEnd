package br.com.univali.gabby_leo_kallil.quiz.security.security.services;

import br.com.univali.gabby_leo_kallil.quiz.api.access.user.User;
import br.com.univali.gabby_leo_kallil.quiz.api.access.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username.trim());
		if(user == null){
			throw new UsernameNotFoundException("E-mail não cadastrado: " + username);
		}
		return UserDetailsImpl.build(user);
	}

}
