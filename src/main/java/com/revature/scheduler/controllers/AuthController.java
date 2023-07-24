package com.revature.scheduler.controllers;

import com.revature.scheduler.daos.LocationDAO;
import com.revature.scheduler.daos.RoleDAO;
import com.revature.scheduler.daos.StateDAO;
import com.revature.scheduler.daos.UserDAO;
import com.revature.scheduler.dtos.AuthDTO;
import com.revature.scheduler.dtos.LoginDTO;
import com.revature.scheduler.dtos.RegisterDTO;
import com.revature.scheduler.models.User;
import com.revature.scheduler.security.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:5500"})
public class AuthController {

  private final AuthenticationManager authManager;
  private final UserDAO userDAO;
  private final LocationDAO locationDAO;
  private final StateDAO stateDAO;
  private final RoleDAO roleDAO;
  private final PasswordEncoder passwordEncoder;
  private final TokenGenerator tokenGenerator;

  private String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";


  @Autowired
  public AuthController(AuthenticationManager authManager, UserDAO userDAO,
                        LocationDAO locationDAO, StateDAO stateDAO,
                        RoleDAO roleDAO, PasswordEncoder passwordEncoder,
                        TokenGenerator tokenGenerator) {
    this.authManager = authManager;
    this.userDAO = userDAO;
    this.locationDAO = locationDAO;
    this.stateDAO = stateDAO;
    this.roleDAO = roleDAO;
    this.passwordEncoder = passwordEncoder;
    this.tokenGenerator = tokenGenerator;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
    if(userDAO.existsByEmail(registerDTO.getEmail())) {
      return new ResponseEntity<>("Email is already in use ", HttpStatus.BAD_REQUEST);
    }

    Pattern pattern = Pattern.compile(emailRegex);
    if(!pattern.matcher(registerDTO.getEmail()).matches()){
      return new ResponseEntity<>("Invalid Email", HttpStatus.BAD_REQUEST);
    }

    User user = new User(
        registerDTO.getFirstName(),
        registerDTO.getLastName(),
        registerDTO.getEmail(),
        passwordEncoder.encode(registerDTO.getPassword())
    );


    userDAO.save(user);

    Authentication authentication = new UsernamePasswordAuthenticationToken(
        user.getEmail(),
        user.getPassword()
    );

    String token = tokenGenerator.generateToken(authentication);
    return new ResponseEntity<>(new AuthDTO(token), HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthDTO> login(@RequestBody LoginDTO loginDTO) {
    Authentication authentication = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginDTO.getEmail(),
            loginDTO.getPassword()
        )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return new ResponseEntity<>(
        new AuthDTO(tokenGenerator.generateToken(authentication)),
        HttpStatus.OK
    );
  }
}
