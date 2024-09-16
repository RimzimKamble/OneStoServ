package com.example.OneStopServ.controller;
import com.example.OneStopServ.dto.AuthenticationRequest;
import com.example.OneStopServ.dto.AuthenticationRequestDTO;
import com.example.OneStopServ.dto.SignupRequestDTO;
import com.example.OneStopServ.dto.UserDto;
import com.example.OneStopServ.entity.User;
import com.example.OneStopServ.repository.UserRepository;
import com.example.OneStopServ.services.authentication.AuthService;
import com.example.OneStopServ.services.jwt.UserDetailsServiceImpl;
import com.example.OneStopServ.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    @PostMapping("/client/sign-up")
    public ResponseEntity<?> signupClient(@RequestBody SignupRequestDTO signupRequestDTO) {

        if (authService.representByEmail(signupRequestDTO.getEmail())) {
            return new ResponseEntity<>("Client is already registered!", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto createdUser = authService.signupClient(signupRequestDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    @PostMapping("/company/sign-up")
    public ResponseEntity<?> signupCompany(@RequestBody SignupRequestDTO signupRequestDTO) {

        if (authService.representByEmail(signupRequestDTO.getEmail())) {
            return new ResponseEntity<>("Company is already registered!", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto createdUser = authService.signupCompany(signupRequestDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest
            , HttpServletResponse response) throws IOException, JSONException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        String jwt = jwtUtil.generateToken(userDetails.getUsername());
        System.out.println("Generated JWT: " + jwt);
        User user = userRepository.findFirstByEmail(authenticationRequest.getUsername());

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + authenticationRequest.getUsername());
        }
        System.out.println("User ID: " + user.getId());
        System.out.println("User Role: " + user.getRole());

//        AuthenticationRequestDTO responseDTO = new AuthenticationRequestDTO(
//                user.getId(),
//                user.getRole(),
//                TOKEN_PREFIX + jwt
//        );
        response.getWriter().write(new JSONObject()
                .put("userId", user.getId())
                .put("role", user.getRole())
                .toString());

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers", "Authorization" +
                "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");

//        return ResponseEntity.ok()
//                .header(HEADER_STRING, TOKEN_PREFIX + jwt)
//                .body(responseDTO);
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
    }
}
