package com.xpress.auth.test.services;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.xpress.auth.test.dto.UserDTO;

public interface UserService extends UserDetailsService{
	
	public UserDTO createUser(UserDTO userDTO);
	public UserDTO getUser(String email);
	public UserDTO getUserByUserId(String userId);
	public UserDTO updateUser(String userId,UserDTO userDTO);
	public void deleteUser(String userId);
	public List<UserDTO> getUsers();
	public UserDTO banningUser(UserDTO userDTO);
}
