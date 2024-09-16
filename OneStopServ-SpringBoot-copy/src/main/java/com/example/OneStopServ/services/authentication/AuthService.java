package com.example.OneStopServ.services.authentication;

import com.example.OneStopServ.dto.SignupRequestDTO;
import com.example.OneStopServ.dto.UserDto;

public interface AuthService {
    UserDto signupClient(SignupRequestDTO signupRequestDTO);

    Boolean representByEmail(String email);

    UserDto signupCompany(SignupRequestDTO signupRequestDTO);
}
