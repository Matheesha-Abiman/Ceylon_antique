package lk.ijse.backend.service;

import lk.ijse.backend.dto.UserDTO;
import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO saveUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    String deleteUser(UserDTO userDTO);
    UserDTO getUserById(Long id);
}