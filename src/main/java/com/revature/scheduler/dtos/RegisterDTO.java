package com.revature.scheduler.dtos;

import com.revature.scheduler.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
