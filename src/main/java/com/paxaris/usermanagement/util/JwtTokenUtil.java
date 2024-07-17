package com.paxaris.usermanagement.util;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.paxaris.usermanagement.tenant.entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Rohit Mehra
 */
@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	@Value("${userManagement.app.jwtSecret}")
	private String jwtSecret;

	@Value("${userManagement.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public String getAudienceFromToken(String token) {
		return getClaimFromToken(token, Claims::getAudience);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
	}

	public String generateToken(Integer userId, String userName, String tenantOrClientId, Set<Role> set) {
		return doGenerateToken(userId, userName, tenantOrClientId, set);
	}

	private String doGenerateToken(Integer userId, String subject, String tenantOrClientId, Set<Role> set) {

		Claims claims = Jwts.claims().setSubject(subject).setAudience(tenantOrClientId);
		claims.put("scopes", set);
		claims.put("userId", userId);

		return Jwts.builder().setClaims(claims).setIssuer("system").setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
	}

}
