package lk.ijse.backend.service;

import lk.ijse.backend.dto.ProfileDTO;
import java.util.List;

public interface ProfileService {
    List<ProfileDTO> getAllProfiles();
    ProfileDTO saveProfile(ProfileDTO profileDTO);
    ProfileDTO updateProfile(ProfileDTO profileDTO);
    String deleteProfile(ProfileDTO profileDTO);
}
