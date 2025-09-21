package lk.ijse.backend.controller;

import lk.ijse.backend.dto.ProfileDTO;
import lk.ijse.backend.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/getprofiles")
    public List<ProfileDTO> getProfiles() {
        return profileService.getAllProfiles();
    }

    @PostMapping("/addprofile")
    public ProfileDTO saveProfile(@RequestBody ProfileDTO profileDTO) {
        return profileService.saveProfile(profileDTO);
    }

    @PutMapping("/updateprofile")
    public ProfileDTO updateProfile(@RequestBody ProfileDTO profileDTO) {
        return profileService.updateProfile(profileDTO);
    }

    @DeleteMapping("/deleteprofile")
    public String deleteProfile(@RequestBody ProfileDTO profileDTO) {
        return profileService.deleteProfile(profileDTO);
    }
}
