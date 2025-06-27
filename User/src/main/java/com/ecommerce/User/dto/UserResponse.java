package com.ecommerce.User.dto;

import com.ecommerce.User.model.USerRole;
import lombok.Data;

@Data
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private USerRole role;
    private AddressDTO address;
}
