package com.softwarehouse.helpdesk.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.softwarehouse.helpdesk.models.Role;
import com.softwarehouse.helpdesk.models.User;
import com.softwarehouse.helpdesk.repositories.RolesRepository;
import com.softwarehouse.helpdesk.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;
	@Autowired
	private RolesRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder, RolesRepository roleRepository) {
		this.repository = repository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.roleRepository = roleRepository;
	}
	
	@Override
	public List<User> findAll() {
		return this.repository.findAll();
	}

	@Override
	public User create(User user) {
		user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
		
		Role userRole = this.roleRepository.findByName("USER");
		
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		
		return this.repository.save(user);
	}

	@Override
	public Boolean delete(Long id) {
		User user = findById(id);
		
		if(user != null) {
			this.repository.delete(user);
			return true;
		}
		
		return false;
	}

	@Override
	public User show(Long id) {
		return findById(id);
	}

	@Override
	public Boolean update(Long id, User user) {
		User userExists = findById(id);
		
		if (userExists != null) {
			userExists.setId(user.getId());
			userExists.setName(user.getName());
			userExists.setLastName(user.getLastName());
			userExists.setEmail(user.getEmail());
			userExists.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
			userExists.setActive(user.getActive());
			
			Role userRole = this.roleRepository.findByName(user.getRoles().iterator().next().getName());
			
			userExists.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
			
			this.repository.save(userExists);
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public List<User> findAllWhereRoleEquals(Long role_id, Long user_id) {
		return this.repository.findAllWhereRoleEquals(role_id, user_id);
	}	
	
	private User findById(Long id) {
		Optional<User> optional = this.repository.findById(id);
 		return optional.get();
	}

	@Override
	public User findCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String username = auth.getName();
		
		User userLogged = this.repository.findByEmail(username);
		
		return userLogged;
	}
}
