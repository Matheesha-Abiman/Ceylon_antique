package lk.ijse.backend.controller;

import lk.ijse.backend.dto.ApiResponse;
import lk.ijse.backend.dto.AuthDTO;
import lk.ijse.backend.dto.RegisterDTO;
import lk.ijse.backend.entity.User;
import lk.ijse.backend.repository.UserRepository;
import lk.ijse.backend.service.AuthService;
import lk.ijse.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(
            @RequestBody RegisterDTO registerDTO) {
        return ResponseEntity.ok(new ApiResponse(
                200,
                "OK",
                authService.register(registerDTO)));
    }

    @RequestMapping("/adminRegister")
    public ResponseEntity<ApiResponse> registerAdmin(
            @RequestBody RegisterDTO registerDTO) {
        return ResponseEntity.ok(new ApiResponse(
                200,
                "OK",
                authService.saveAdmin(registerDTO)
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(
            @RequestBody AuthDTO authDTO) {
        return ResponseEntity.ok(new ApiResponse(
                200,
                "OK",
                authService.authenticate(authDTO)));
    }

    @GetMapping("/check-role")
    public ResponseEntity<ApiResponse> checkUserRole(@RequestHeader("Authorization") String token) {
        try {
            String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return ResponseEntity.ok(new ApiResponse(200, "OK", user.getRole().name()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ApiResponse(401, "Unauthorized", "Invalid token"));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getMe(@RequestHeader("Authorization") String token) {
        try {
            String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            user.setPassword(null);

            return ResponseEntity.ok(new ApiResponse(200, "OK", user));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(new ApiResponse(401, "Unauthorized", "Invalid token"));
        }
    }
}