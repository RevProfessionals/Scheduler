package com.revature.scheduler.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
