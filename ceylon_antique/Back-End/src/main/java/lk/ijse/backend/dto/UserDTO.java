package lk.ijse.backend.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private  String password;
    private String title;
    private String address;
    private String phone;
    private LocalDate dob;
    private String street;
    private String city;
    private String postalCode;
    private String imageUrl;

}
