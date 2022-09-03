package com.xpress.auth.test.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xpress.auth.test.dto.UserDTO;
import com.xpress.auth.test.entities.UserEntity;
import com.xpress.auth.test.exceptions.UserServiceException;
import com.xpress.auth.test.repositories.BannedIpRepository;
import com.xpress.auth.test.repositories.UserRepository;
import com.xpress.auth.test.services.UserService;
import com.xpress.auth.test.utils.ErrorMessages;
import com.xpress.auth.test.utils.UtilSecurity;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BannedIpRepository repositoryBannedIp;
	
	@Autowired
	UtilSecurity utils;

	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;

	@Override
	public UserDTO createUser(UserDTO userDTO)
	{
		UserEntity userEntityByEmail = userRepository.findByEmail(userDTO.getEmail());
		if (userEntityByEmail != null)
		{
			throw new RuntimeException("Cet utilisateur existe déjà");
		}
		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
		String publicUserId = utils.generateUserId(20);
		userEntity.setUserId(publicUserId);
		userEntity.setEncryptedPassword(bcryptPasswordEncoder.encode(userDTO.getPassword()));
		UserEntity storedUserEntity = userRepository.save(userEntity);
		UserDTO returnUserDTO = modelMapper.map(storedUserEntity, UserDTO.class) ;
		return returnUserDTO;
	}

	@Override
	public UserDTO getUser(String email)
	{
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
		{
			throw new UsernameNotFoundException(email);
		}
		UserDTO returnUserDTO = new UserDTO();
		BeanUtils.copyProperties(userEntity, returnUserDTO);
		return returnUserDTO;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
	{
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
		{
			throw new UsernameNotFoundException(email);
		}
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
				new ArrayList<>());
	}

	@Override
	public UserDTO getUserByUserId(String userId)
	{
		UserEntity userEntityByUserId = userRepository.findByUserId(userId);
		if (userEntityByUserId == null)
		{
			throw new UsernameNotFoundException(" l'utilisateur avec l'id ("+userId+") introuvable");
		}
		ModelMapper modelMapper = new ModelMapper();
		UserDTO returnUserDTO = modelMapper.map(userEntityByUserId, UserDTO.class);
		return returnUserDTO;
	}

	@Override
	@Transactional
	public UserDTO updateUser(String userId, UserDTO userDTO)
	{ 
		UserEntity userEntityByUserId = userRepository.findByUserId(userId);
		if (userEntityByUserId == null)
		{
			throw new UserServiceException(ErrorMessages.RECORD_NOT_FOUND.getErrorMessage());
		}
        String prenom = userDTO.getPrenom();
        if(prenom != null && prenom.length() > 0 && 
        		!Objects.equals(prenom, userEntityByUserId.getPrenom())) {
        	userEntityByUserId.setPrenom(prenom);
        }
        String nom = userDTO.getNom();
        if(nom != null && nom.length() > 0 && 
        		!Objects.equals(nom, userEntityByUserId.getNom())) {
        	userEntityByUserId.setNom(nom);
        }
		String email = userDTO.getEmail();
		if(email != null && email.length() > 0 && 
        		!Objects.equals(email, userEntityByUserId.getEmail())) {
        	userEntityByUserId.setEmail(email);
        }
		String password = userDTO.getPassword();
		if(password != null && password.length() > 0) {
			userEntityByUserId
			.setEncryptedPassword(bcryptPasswordEncoder.encode(password));
		}
		ModelMapper modelMapper = new ModelMapper();
		UserDTO returnUserDTO = modelMapper.map(userEntityByUserId, UserDTO.class);
		return returnUserDTO;
	}

	@Override
	public void deleteUser(String userId)
	{
		UserEntity userEntityByUserId = userRepository.findByUserId(userId);
		if (userEntityByUserId == null)
		{
			throw new UserServiceException(ErrorMessages.RECORD_NOT_FOUND.getErrorMessage());
		}
		userRepository.delete(userEntityByUserId);
	}

	@Override
	public List<UserDTO> getUsers()
	{
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		List<UserEntity> userListRet = userRepository.findAll();
		for(UserEntity userRet : userListRet) {
			ModelMapper modelMapper = new ModelMapper();
			UserDTO userAdd = modelMapper.map(userRet, UserDTO.class);
			userDTOList.add(userAdd);
		}
		return userDTOList;
	}
}
