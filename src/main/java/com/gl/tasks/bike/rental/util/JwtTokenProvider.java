package com.gl.tasks.bike.rental.util;

import java.util.Base64;
import java.util.Date;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.gl.tasks.bike.rental.enums.Role;
import com.gl.tasks.bike.rental.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret-key:secret}")
	private String secretKey = "secret";
	
	@Value("${security.jwt.token.expire-length:3600000}")
	private long validityinMilliseconds = 3600000;
	
	@Autowired
	private UserService userService;
	
	private static final String AUTHORIZATION = "Authorization";

	private static final String BEARER = "Bearer ";
	
	private static final Integer INDEX_WITHOUT_BEARER = 7;
	
	@PostConstruct
	protected void init() {
	    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	public String createToken(final String username, final Set<Role> roles) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("roles", roles);
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityinMilliseconds);
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
	
	public Authentication getAuthentication(final String token) {
		UserDetails userDetails = this.userService.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private String getUsername(final String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	public String resolveToken(final HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION);
		if(StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith(BEARER)) {
			return bearerToken.substring(INDEX_WITHOUT_BEARER, bearerToken.length());
		}
		return null;
	}
	
	public boolean validateToken(final String token) {
	    try {
	        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
	        if (claims.getBody().getExpiration().before(new Date())) {
	            return false;
	        }
	        return true;
	    } catch (JwtException | IllegalArgumentException e) {
	        throw new JwtException(Translator.toLocale("expired.jwt.token"));
	    }
	} 
}
