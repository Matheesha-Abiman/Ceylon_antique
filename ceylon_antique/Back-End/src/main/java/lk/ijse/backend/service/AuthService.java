package lk.ijse.backend.service;

import lk.ijse.backend.dto.AuthDTO;
import lk.ijse.backend.dto.AuthResponseDTO;
import lk.ijse.backend.dto.RegisterDTO;
import lk.ijse.backend.entity.Role;
import lk.ijse.backend.entity.User;
import lk.ijse.backend.repository.UserRepository;
import lk.ijse.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final MailSendService mailSendService;

    public AuthResponseDTO authenticate(AuthDTO authDTO) {
        User user=
                userRepository.findByUsername(authDTO.getUsername())
                        .orElseThrow(
                                ()->new UsernameNotFoundException
                                        ("Username not found"));
        if (!passwordEncoder.matches(
                authDTO.getPassword(),
                user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        // Generate JWT token
        String token = jwtUtil.generateToken(authDTO.getUsername());

        // Send login notification email
        try {
            mailSendService.sendLoggedInEmail(user.getUsername(), user.getEmail());
        } catch (Exception e) {
            // Log error instead of breaking auth flow
            System.err.println("Failed to send login email: " + e.getMessage());
        }

        return new AuthResponseDTO(token);
    }
    public String register(RegisterDTO registerDTO) {
        if(userRepository.findByUsername(
                registerDTO.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }
        User user=User.builder()
                .username(registerDTO.getUsername())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(
                        registerDTO.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        // Send registration email
        try {
            mailSendService.sendRegisteredEmail(user.getUsername(), user.getEmail());
        } catch (Exception e) {
            System.err.println("Failed to send registration email: " + e.getMessage());
        }

        return "User Registration Success";
    }

    public String saveAdmin(RegisterDTO registerDTO) {
        User user = User.builder()
                .username(registerDTO.getUsername())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(
                        registerDTO.getPassword()))
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);
        return "Admin Registration Success";
    }
}