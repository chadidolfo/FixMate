package com.codework.fixmate.service.Authentication;

import com.codework.fixmate.service.dtos.UserDTO;
import com.codework.fixmate.service.dtos.signUpRequestDTO;

public interface authService {
    UserDTO signUpClient (signUpRequestDTO signUprequestDTO);
    Boolean presentByEmail(String email);

    UserDTO signUpCompany (signUpRequestDTO signUprequestDTO);
}
