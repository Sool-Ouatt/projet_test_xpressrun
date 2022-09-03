package com.xpress.auth.test.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpress.auth.test.context.SpringApplicationContext;
import com.xpress.auth.test.dto.UserDTO;
import com.xpress.auth.test.exceptions.UserServiceException;
import com.xpress.auth.test.models.request.UserLoginRequest;
import com.xpress.auth.test.services.BannedIpService;
import com.xpress.auth.test.services.UserService;
import com.xpress.auth.test.utils.ErrorMessages;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
	private final AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager)
	{
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException
	{
		BannedIpService ipService = (BannedIpService) SpringApplicationContext.getBean("bannedIpServiceImpl");
		if (ipService.isBanned(request.getRemoteAddr()))
		{
			throw new UserServiceException(ErrorMessages.IP_ADD_BANNED.getErrorMessage());
		}
		try
		{
			UserLoginRequest userLoginRequestModel = new ObjectMapper()
					.readValue(request.getInputStream(), UserLoginRequest.class);
			
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userLoginRequestModel.getEmail(),
							userLoginRequestModel.getPassword(), new ArrayList<>()));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, Authentication authResult)
			throws IOException, ServletException
	{
		String userName = ((User) authResult.getPrincipal()).getUsername();

		String token = Jwts.builder().setSubject(userName)
				.setExpiration(
						new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();
		
		UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
		UserDTO userDTO = userService.getUser(userName);
		response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
		response.addHeader("UserID",userDTO.getUserId());

	}

}