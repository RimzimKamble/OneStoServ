package com.example.OneStopServ.services.authentication;

import com.example.OneStopServ.dto.SignupRequestDTO;
import com.example.OneStopServ.dto.UserDto;
import com.example.OneStopServ.entity.User;
import com.example.OneStopServ.enums.UserRole;
import com.example.OneStopServ.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

   public UserDto signupClient(SignupRequestDTO signupRequestDTO){
       User user = new User();

       user.setFirstName(signupRequestDTO.getFirstName());
       user.setLastName(signupRequestDTO.getLastName());
       user.setEmail(signupRequestDTO.getEmail());
       user.setPhone(signupRequestDTO.getPhone());
       user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));

       user.setRole(UserRole.CLIENT);
       return userRepository.save(user).getDto();



   }

   public Boolean representByEmail(String email){
       return userRepository.findFirstByEmail(email) !=null;
   }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto signupCompany(SignupRequestDTO signupRequestDTO){
        User user = new User();

        user.setFirstName(signupRequestDTO.getFirstName());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));

        user.setRole(UserRole.COMPANY);
        return userRepository.save(user).getDto();

    }
}
