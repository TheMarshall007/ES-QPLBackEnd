package br.com.univali.gabby_leo_kallil.quiz.api.access.passwordReset;

import br.com.univali.gabby_leo_kallil.quiz.api.access.passwordReset.DTO.PasswordResetDTOCode;
import br.com.univali.gabby_leo_kallil.quiz.api.access.passwordReset.DTO.PasswordResetDTOConfirmation;
import br.com.univali.gabby_leo_kallil.quiz.api.access.user.User;
import br.com.univali.gabby_leo_kallil.quiz.api.access.user.UserRepository;
import br.com.univali.gabby_leo_kallil.quiz.api.access.user.UserService;
import br.com.univali.gabby_leo_kallil.quiz.component.email.EmailService;
import br.com.univali.gabby_leo_kallil.quiz.security.security.jwt.JwtUtils;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PasswordResetService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordResetRepository passwordResetRepository;
	
	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private EmailService emailService;
	
	public void postPasswordResetRequest(String email){
		String token = jwtUtils.generateAnonymousToken(userService.findUserByEmail(email));
		String code = createResetPasswordCode();
		
		sendResetPasswordEmail(email, code);
		
		PasswordReset passwordReset = new PasswordReset();
		passwordReset.setEmail(email);
		passwordReset.setCode(code);
		passwordReset.setToken(token);
		passwordResetRepository.save(passwordReset);
	}
	
	public PasswordReset postPasswordResetCode(PasswordResetDTOCode resetCodeForm)  {
		return passwordResetRepository.findOneByEmailAndCode(resetCodeForm.getEmail(), resetCodeForm.getCode());
	}
	
	public User postPasswordResetConfirmation(PasswordResetDTOConfirmation dto)  {
		User user = userService.findUserByEmail(dto.getEmail());
		
		String password = encryptPassword(dto.getPassword());
		user.setPassword(password);
		userRepository.save(user);
		
		return user;
	}

	public String createResetPasswordCode() throws JWTCreationException{
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		int codeSize = 6;
		
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < codeSize) { // length of the code.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
		return salt.toString();
	}

	
	private void sendResetPasswordEmail(String email, String code) {
		emailService.sendResetPasswordEmail(email, code);
	}

	private String encryptPassword(String password) {
		// Encrypt password using the sha256 algorithm
		return passwordEncoder.encode(password);
	}
}
