package br.com.univali.gabby_leo_kallil.quiz.security.security.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.stream.Collectors;

import br.com.univali.gabby_leo_kallil.quiz.api.access.role.Role;
import br.com.univali.gabby_leo_kallil.quiz.api.access.user.User;
import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	private static final String USER_ID = "userId";
	private static final String ROLES = "roles";
	private static final Algorithm TOKEN_SIGN_ALGORITHM = getTokenSignAlgorithm();

	private static final String jwtSecret = "quizUnivali2022";

	private int jwtExpirationMs = 86400000;

	/*private int jwtExpirationMs = 3600000;*/

	public String generateJwtToken1(User user){
		return Jwts.builder()
				.setSubject((user.getEmail()))
				.setIssuedAt(new Date())
//				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs * 30L))
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs * 7L))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.claim(USER_ID, user.getId())
				.claim(ROLES, user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
				.compact();
	}

	public String generateAnonymousToken(User user){
		return Jwts.builder()
				.setSubject((user.getEmail()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + 1800000))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.claim(USER_ID, user.getId())
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public Integer getUserIdFromJwtToken(String token) {
		return (Integer) Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token.substring(7)).getBody().get(USER_ID);
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

	private static Algorithm getTokenSignAlgorithm() {
		try {
			return Algorithm.HMAC512(jwtSecret);
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
