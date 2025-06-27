package com.ecommerce.User.controller;

import com.ecommerce.User.dto.UserRequest;
import com.ecommerce.User.dto.UserResponse;
import com.ecommerce.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllusers() {

        return ResponseEntity.ok(userService.fetchAllusers());
    }

    @GetMapping("/{id}")
    public  ResponseEntity<UserResponse> FetchAUser(@PathVariable String id) {
//        Optional<User> user = userService.fetchAuser(id);
//        if(user == null)
//            return ResponseEntity.notFound().build();
//        return ResponseEntity.ok(user);
        return userService.fetchAuser(id)
                            .map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public String createusers(@RequestBody UserRequest user) {
        userService.addUser(user);
        return "Added Successfully";

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> createusers(@RequestBody UserRequest user, @PathVariable String id) {
         boolean updated = userService.updateAUser(id,user);
        if(updated)
            return ResponseEntity.ok("User updated successfully");
         return ResponseEntity.notFound().build();
        }


}
