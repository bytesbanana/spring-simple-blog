package com.bytebanana.simpleblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
