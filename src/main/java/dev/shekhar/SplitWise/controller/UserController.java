package dev.shekhar.SplitWise.controller;

import dev.shekhar.SplitWise.dto.UserLoginRequestDTO;
import dev.shekhar.SplitWise.dto.UserRegistrationRequestDTO;
import dev.shekhar.SplitWise.entity.User;
import dev.shekhar.SplitWise.exception.UserLoginInvalidDataException;
import dev.shekhar.SplitWise.exception.UserRegistrationInvalidDataException;
import dev.shekhar.SplitWise.mapper.EntityDTOMapper;
import dev.shekhar.SplitWise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    private ResponseEntity signUp(@RequestBody UserRegistrationRequestDTO userRegistrationRequestDTO) {
        validateUserRegistrationRequestDTO(userRegistrationRequestDTO);
        User savedUser = userService.signUp(
            userRegistrationRequestDTO.getName(),
            userRegistrationRequestDTO.getEmail(),
            userRegistrationRequestDTO.getPassword()
        );
        return ResponseEntity.ok(
            EntityDTOMapper.toUserLoginResponseDTO(savedUser)
        );
    }

    private void validateUserRegistrationRequestDTO(UserRegistrationRequestDTO userRegistrationRequestDTO) {
        if(userRegistrationRequestDTO.getEmail() == null
                || userRegistrationRequestDTO.getName() == null
                || userRegistrationRequestDTO.getPassword() == null) {
            throw new UserRegistrationInvalidDataException("Invalid signup data");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginRequestDTO userLoginRequestDTO) {
         validateUserLoginRequestDTO(userLoginRequestDTO);
         User savedUser = userService.logIn(
             userLoginRequestDTO.getEmail(),
             userLoginRequestDTO.getPassword()
         );
         return ResponseEntity.ok(
             EntityDTOMapper.toUserLoginResponseDTO(savedUser)
         );
    }

    private void validateUserLoginRequestDTO(UserLoginRequestDTO userLoginRequestDTO) {
        if(userLoginRequestDTO.getEmail() == null || userLoginRequestDTO.getPassword() == null) {
            throw new UserLoginInvalidDataException("Invalid login data");
        }
    }
}
