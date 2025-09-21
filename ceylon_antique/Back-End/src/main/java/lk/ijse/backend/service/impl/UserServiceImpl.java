package lk.ijse.backend.service.impl;

import lk.ijse.backend.dto.UserDTO;
import lk.ijse.backend.entity.Role;
import lk.ijse.backend.entity.User;
import lk.ijse.backend.repository.UserRepository;
import lk.ijse.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
//                .password(user.getPassword()) // include only if you really want it exposed
                .title(user.getTitle())
                .address(user.getAddress())
                .phone(user.getPhone())
                .dob(user.getDob())
                .street(user.getStreet())
                .city(user.getCity())
                .postalCode(user.getPostalCode())
                .imageUrl(user.getImageUrl())

                .build();
    }

    private User mapToEntity(UserDTO userDTO) {
        // Generate a random password for new users if not provided
        String password = userDTO.getPassword() != null ?
                passwordEncoder.encode(userDTO.getPassword()) :
                passwordEncoder.encode("TempPassword123!");

        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(password)
                .role(Role.valueOf(userDTO.getRole()))
                .title(userDTO.getTitle())
                .address(userDTO.getAddress())
                .phone(userDTO.getPhone())
                .dob(userDTO.getDob())
                .street(userDTO.getStreet())
                .city(userDTO.getCity())
                .postalCode(userDTO.getPostalCode())
                .imageUrl(userDTO.getImageUrl())
                .build();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        // Check if username already exists
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Check if email already exists
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = mapToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        if (userDTO.getId() == null) throw new RuntimeException("User ID is required for update");

        User existingUser = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if username is being changed to one that already exists (excluding current user)
        if (!existingUser.getUsername().equals(userDTO.getUsername()) &&
                userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Check if email is being changed to one that already exists (excluding current user)
        if (!existingUser.getEmail().equals(userDTO.getEmail()) &&
                userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setRole(Role.valueOf(userDTO.getRole()));
        existingUser.setTitle(userDTO.getTitle());
        existingUser.setAddress(userDTO.getAddress());
        existingUser.setPhone(userDTO.getPhone());
        existingUser.setDob(userDTO.getDob());
        existingUser.setStreet(userDTO.getStreet());
        existingUser.setCity(userDTO.getCity());
        existingUser.setPostalCode(userDTO.getPostalCode());
        existingUser.setImageUrl(userDTO.getImageUrl());
        // Only update password if provided
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        User updatedUser = userRepository.save(existingUser);
        return mapToDTO(updatedUser);
    }

    @Override
    public String deleteUser(UserDTO userDTO) {
        if (userDTO.getId() == null) throw new RuntimeException("User ID is required for delete");

        if (!userRepository.existsById(userDTO.getId())) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(userDTO.getId());
        return "User deleted successfully";
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user);
    }
}