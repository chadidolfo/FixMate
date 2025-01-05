package com.codework.fixmate.service.dtos;

import com.codework.fixmate.dao.enums.userRole;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String phone;

    private userRole role;
}
