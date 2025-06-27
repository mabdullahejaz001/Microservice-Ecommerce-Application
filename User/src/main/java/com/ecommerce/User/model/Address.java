package com.ecommerce.User.model;



import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class Address {
    Long id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;

}
