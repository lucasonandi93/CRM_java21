package company.cryo.crm.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import company.cryo.crm.dto.UsersDto;
import company.cryo.crm.mapper.UserMapper;
import company.cryo.crm.model.Customers;
import company.cryo.crm.model.UserGrant;
import company.cryo.crm.model.Users;
import company.cryo.crm.repository.UsersRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

	@Autowired
	private UsersRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	public UsersDto getUserById(Integer id) {
		Optional<Users> optionalUser = userRepository.findById(id);
		if (!optionalUser.isPresent()) {
			throw new RuntimeException("User not found");
		}
		Users user = optionalUser.get();
		return userMapper.toDto(user);
	}

	public void saveUser(UsersDto userDto) {
		Users user = userMapper.toModel(userDto);
		userRepository.save(user);
	}

	public List<UsersDto> showListeUsers(){
        List<Users> usersList = new ArrayList<>();
        usersList.addAll(userRepository.findAll());
        return usersList.stream().map(userMapper::toDto).collect(Collectors.toList());
    }
	public List<UsersDto> getUsersByFilter(UserGrant userGrant, String nom) {
        
        List<Users> users = userRepository.findByGrantAndByNom(userGrant, nom);
        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }
	
}