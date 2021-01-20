package com.bytebanana.simpleblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileRequest {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
