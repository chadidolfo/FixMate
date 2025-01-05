package com.codework.fixmate.service.Authentication;

import com.codework.fixmate.dao.entities.User;
import com.codework.fixmate.dao.enums.userRole;
import com.codework.fixmate.dao.repositories.UserRepository;
import com.codework.fixmate.service.dtos.UserDTO;
import com.codework.fixmate.service.dtos.signUpRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class authServiceImpl implements authService{

    @Autowired
    private UserRepository userRepository;

    public UserDTO signUpClient (signUpRequestDTO signUprequestDTO){
        User user=new User();

        user.setName(signUprequestDTO.getName());
        user.setLastName(signUprequestDTO.getLastName());
        user.setEmail(signUprequestDTO.getEmail());
        user.setPhone(signUprequestDTO.getPhone());
        user.setPassword(signUprequestDTO.getPassword());
        user.setRole(userRole.client);

        return userRepository.save(user).getDTO();
    }

    public Boolean presentByEmail(String email){

        return userRepository.findByEmail(email)!=null;
    }


    public UserDTO signUpCompany (signUpRequestDTO signUprequestDTO){
        User user=new User();

        user.setName(signUprequestDTO.getName());
        user.setEmail(signUprequestDTO.getEmail());
        user.setPhone(signUprequestDTO.getPhone());
        user.setPassword(signUprequestDTO.getPassword());
        user.setRole(userRole.company);

        return userRepository.save(user).getDTO();
    }


}
