package com.app.petsvets.serviceImpl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.app.petsvets.model.LoginResponseModel;
import com.app.petsvets.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtServiceImpl implements JwtService {

	private static final String KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

	/**
	 * To generate token from user details
	 * 
	 * @param username
	 * @param auth authenticated user details
	 * @return response responseModel with token and user details
	 */
	public LoginResponseModel generateToken(String username, Authentication auth) {
		Map<String, Object> claims = new HashMap<>();
		LoginResponseModel response = new LoginResponseModel();
		response.setToken(createToken(claims, username));
		response.setUserName(auth.getName());
		response.setRole(auth.getAuthorities().stream().findFirst().get().toString());
		return response;
	}

	/**
	 * Method uses username, role, expiration, signKey and 
	 * the algorithm to generate the token
	 * 
	 * @param claims
	 * @param username
	 * @return token returns generated token
	 */
	private String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	/**
	 * Method creates a new key based on the secret key provided
	 * 
	 * @return signKey from secret key provided.
	 */
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String getUserDetails(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUserDetails(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

}
