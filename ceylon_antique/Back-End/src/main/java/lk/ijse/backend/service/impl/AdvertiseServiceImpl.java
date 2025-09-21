package lk.ijse.backend.service.impl;

import lk.ijse.backend.dto.AdvertiseDTO;
import lk.ijse.backend.entity.Advertise;
import lk.ijse.backend.entity.User;
import lk.ijse.backend.repository.AdvertiseRepository;
import lk.ijse.backend.repository.UserRepository;
import lk.ijse.backend.service.AdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertiseServiceImpl implements AdvertiseService {

    @Autowired
    private AdvertiseRepository advertiseRepository;

    @Autowired
    private UserRepository userRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<AdvertiseDTO> getAllAdvertises() {
        return advertiseRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AdvertiseDTO saveAdvertise(AdvertiseDTO advertiseDTO) {
        Advertise advertise = convertToEntity(advertiseDTO);
        advertise.setPostedDate(LocalDateTime.now());

        // Set default values if not provided
        if (advertise.getImpressions() == null) advertise.setImpressions(0);
        if (advertise.getClicks() == null) advertise.setClicks(0);
        if (advertise.getStatus() == null) advertise.setStatus("active");

        Advertise saved = advertiseRepository.save(advertise);
        return convertToDTO(saved);
    }

    @Override
    public AdvertiseDTO updateAdvertise(AdvertiseDTO advertiseDTO) {
        Advertise existingAd = advertiseRepository.findById(advertiseDTO.getId())
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        // Update fields
        if (advertiseDTO.getTitle() != null) existingAd.setTitle(advertiseDTO.getTitle());
        if (advertiseDTO.getImageUrl() != null) existingAd.setImageUrl(advertiseDTO.getImageUrl());
        if (advertiseDTO.getLink() != null) existingAd.setLink(advertiseDTO.getLink());
        if (advertiseDTO.getStatus() != null) existingAd.setStatus(advertiseDTO.getStatus());
        if (advertiseDTO.getType() != null) existingAd.setType(advertiseDTO.getType());
        if (advertiseDTO.getImpressions() != null) existingAd.setImpressions(advertiseDTO.getImpressions());
        if (advertiseDTO.getClicks() != null) existingAd.setClicks(advertiseDTO.getClicks());
        if (advertiseDTO.getBudget() != null) existingAd.setBudget(advertiseDTO.getBudget());

        Advertise updated = advertiseRepository.save(existingAd);
        return convertToDTO(updated);
    }

    @Override
    public String deleteAdvertise(AdvertiseDTO advertiseDTO) {
        advertiseRepository.deleteById(advertiseDTO.getId());
        return "Advertise deleted successfully!";
    }

    @Override
    public List<AdvertiseDTO> getAdvertisesByUserId(Long userId) {
        return advertiseRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdvertiseDTO> getAdvertisesByStatus(String status) {
        return advertiseRepository.findByStatus(status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdvertiseDTO> getAdvertisesByType(String type) {
        return advertiseRepository.findByType(type)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdvertiseDTO> searchAdvertises(String title) {
        return advertiseRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // === Helper methods ===
    private AdvertiseDTO convertToDTO(Advertise advertise) {
        return AdvertiseDTO.builder()
                .id(advertise.getId())
                .title(advertise.getTitle())
                .imageUrl(advertise.getImageUrl())
                .link(advertise.getLink())
                .postedDate(advertise.getPostedDate() != null ? advertise.getPostedDate().format(formatter) : null)
                .userId(advertise.getUser() != null ? advertise.getUser().getId() : null)
                .status(advertise.getStatus())
                .type(advertise.getType())
                .impressions(advertise.getImpressions())
                .clicks(advertise.getClicks())
                .budget(advertise.getBudget())
                .build();
    }

    private Advertise convertToEntity(AdvertiseDTO dto) {
        User user = null;
        if (dto.getUserId() != null) {
            user = userRepository.findById(dto.getUserId()).orElse(null);
        }

        LocalDateTime postedDate = null;
        if (dto.getPostedDate() != null) {
            postedDate = LocalDateTime.parse(dto.getPostedDate(), formatter);
        }

        return Advertise.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .imageUrl(dto.getImageUrl())
                .link(dto.getLink())
                .postedDate(postedDate)
                .user(user)
                .status(dto.getStatus())
                .type(dto.getType())
                .impressions(dto.getImpressions())
                .clicks(dto.getClicks())
                .budget(dto.getBudget())
                .build();
    }
}