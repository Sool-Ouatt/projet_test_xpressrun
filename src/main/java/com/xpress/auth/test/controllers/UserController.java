package com.xpress.auth.test.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xpress.auth.test.dto.UserDTO;
import com.xpress.auth.test.exceptions.UserServiceException;
import com.xpress.auth.test.models.request.UserRequest;
import com.xpress.auth.test.models.responses.OperationStatusModel;
import com.xpress.auth.test.models.responses.UserResponse;
import com.xpress.auth.test.services.UserService;
import com.xpress.auth.test.utils.ErrorMessages;
import com.xpress.auth.test.utils.RequestOperationName;
import com.xpress.auth.test.utils.RequestOperationResult;

@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public UserResponse createUser(HttpServletRequest request, @RequestBody UserRequest userDetails) throws Exception
	{
		if (userDetails.getEmail().isEmpty())
		{
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELDS.getErrorMessage());
		}
		ModelMapper modelMapper = new ModelMapper();
		UserDTO userDTO = modelMapper.map(userDetails, UserDTO.class);
		UserDTO createdUserDTO = userService.createUser(userDTO);
		UserResponse userRest = modelMapper.map(createdUserDTO, UserResponse.class);
		return userRest;
	}

	@GetMapping(path = "/{id}")
	public UserResponse getUser(@PathVariable("id") String userId)
	{
		UserDTO userDTO = userService.getUserByUserId(userId);
		ModelMapper modelMapper = new ModelMapper();
		UserResponse userRest = modelMapper.map(userDTO, UserResponse.class);	
		return userRest;
	}

	@PutMapping(path = "/{id}")
	public UserResponse updateUser(@PathVariable("id") String userId,
			@RequestBody UserRequest userDetails) throws Exception
	{
		if (userDetails.getEmail().isEmpty())
		{
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELDS.getErrorMessage());
		}
		ModelMapper modelMapper = new ModelMapper();
		UserDTO userDTO = modelMapper.map(userDetails, UserDTO.class);

		UserDTO updatedUserDTO = userService.updateUser(userId, userDTO);
		
		UserResponse userRest = modelMapper.map(updatedUserDTO, UserResponse.class);
		return userRest;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable("id") String userId)
	{
		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName(RequestOperationName.DELETE.name());
		
		userService.deleteUser(userId);
		
		operationStatusModel.setOperationResult(RequestOperationResult.SUCCESS.name());
		return operationStatusModel;
	}

	@GetMapping
	public List<UserResponse> getUsers()
	{
		List<UserResponse> userReturnList = new ArrayList<UserResponse>();
		List<UserDTO> userDTOList = userService.getUsers();
		for (UserDTO userDTO : userDTOList)
		{
			ModelMapper modelMapper = new ModelMapper();
			UserResponse userReturn = modelMapper.map(userDTO, UserResponse.class);
			userReturnList.add(userReturn);
		}
		return userReturnList;
	}

}
