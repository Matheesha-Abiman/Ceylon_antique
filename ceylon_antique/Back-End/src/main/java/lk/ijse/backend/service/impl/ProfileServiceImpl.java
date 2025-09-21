package lk.ijse.backend.service.impl;

import lk.ijse.backend.dto.ProfileDTO;
import lk.ijse.backend.entity.Profile;
import lk.ijse.backend.entity.User;
import lk.ijse.backend.repository.ProfileRepository;
import lk.ijse.backend.repository.UserRepository;
import lk.ijse.backend.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    private ProfileDTO mapToDTO(Profile profile) {
        return ProfileDTO.builder()
                .id(profile.getId())
                .fullName(profile.getFullName())
                .phone(profile.getPhone())
                .address(profile.getAddress())
                .profileImage(profile.getProfileImage())
                .userId(profile.getUser() != null ? profile.getUser().getId() : null)
                .build();
    }

    private Profile mapToEntity(ProfileDTO profileDTO) {
        User user = null;
        if (profileDTO.getUserId() != null) {
            user = userRepository.findById(profileDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }
        return Profile.builder()
                .id(profileDTO.getId())
                .fullName(profileDTO.getFullName())
                .phone(profileDTO.getPhone())
                .address(profileDTO.getAddress())
                .profileImage(profileDTO.getProfileImage())
                .user(user)
                .build();
    }

    @Override
    public List<ProfileDTO> getAllProfiles() {
        return profileRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProfileDTO saveProfile(ProfileDTO profileDTO) {
        Profile profile = mapToEntity(profileDTO);
        Profile savedProfile = profileRepository.save(profile);
        return mapToDTO(savedProfile);
    }

    @Override
    public ProfileDTO updateProfile(ProfileDTO profileDTO) {
        if (profileDTO.getId() == null) throw new RuntimeException("Profile ID is required for update");
        Profile existingProfile = profileRepository.findById(profileDTO.getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        existingProfile.setFullName(profileDTO.getFullName());
        existingProfile.setPhone(profileDTO.getPhone());
        existingProfile.setAddress(profileDTO.getAddress());
        existingProfile.setProfileImage(profileDTO.getProfileImage());

        if (profileDTO.getUserId() != null) {
            User user = userRepository.findById(profileDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            existingProfile.setUser(user);
        }

        Profile updatedProfile = profileRepository.save(existingProfile);
        return mapToDTO(updatedProfile);
    }

    @Override
    public String deleteProfile(ProfileDTO profileDTO) {
        if (profileDTO.getId() == null) throw new RuntimeException("Profile ID is required for delete");
        profileRepository.deleteById(profileDTO.getId());
        return "Profile deleted successfully";
    }
}
