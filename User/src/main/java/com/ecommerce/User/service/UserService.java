package com.ecommerce.User.service;

import com.ecommerce.User.model.Address;
import com.ecommerce.User.dto.UserRequest;
import com.ecommerce.User.dto.UserResponse;
import com.ecommerce.User.dto.AddressDTO;
import com.ecommerce.User.model.User;
import com.ecommerce.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private List<User> userList = new ArrayList<>();
    long id = 0L;

    public List<UserResponse> fetchAllusers() {
        return userRepository.findAll().stream()
                .map(this::mapUserToUserResponse)
                .collect(Collectors.toList());
    }

    public void addUser( UserRequest userResponse) {
        User user = new User();
        updateUserFromRequest(user , userResponse);
        userRepository.save(user);
    }


    public Optional<UserResponse> fetchAuser(String id) {
       return userRepository.findById(id)
               .map(this::mapUserToUserResponse);
    }

    public boolean updateAUser(String id, UserRequest user) {
        return userRepository.findById(id)
                        .map(existinguser -> {
                            updateUserFromRequest(existinguser,user);
                            userRepository.save(existinguser);
                            return true;
                            }).orElse( false);
    }

    private void updateUserFromRequest(User user, UserRequest userResponse) {

        user.setFirstName(userResponse.getFirstName());
        user.setLastName(userResponse.getLastName());
        user.setEmail(userResponse.getEmail());
        user.setPhone(userResponse.getPhone());

        if (userResponse.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userResponse.getAddress().getStreet());
            address.setCity(userResponse.getAddress().getCity());
            address.setState(userResponse.getAddress().getState());
            address.setCountry(userResponse.getAddress().getCountry());
            address.setZipcode(userResponse.getAddress().getZipcode());
            user.setAddress(address);
        }
    }


    private UserResponse mapUserToUserResponse(User user)
    {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId().toString());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setRole(user.getRole());

        if(user.getAddress() != null)
        {
            AddressDTO addressDTO = new AddressDTO();
          addressDTO.setStreet(user.getAddress().getStreet());
          addressDTO.setCity(user.getAddress().getCity());
          addressDTO.setState( user.getAddress().getState());
          addressDTO.setCountry( user.getAddress().getCountry());
          addressDTO.setZipcode( user.getAddress().getZipcode());
          userResponse.setAddress(addressDTO);
        }

        return userResponse;
    }
}
