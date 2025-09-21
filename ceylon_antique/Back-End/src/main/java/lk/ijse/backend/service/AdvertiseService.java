package lk.ijse.backend.service;

import lk.ijse.backend.dto.AdvertiseDTO;

import java.util.List;

public interface AdvertiseService {
    List<AdvertiseDTO> getAllAdvertises();
    AdvertiseDTO saveAdvertise(AdvertiseDTO advertiseDTO);
    AdvertiseDTO updateAdvertise(AdvertiseDTO advertiseDTO);
    String deleteAdvertise(AdvertiseDTO advertiseDTO);
    List<AdvertiseDTO> getAdvertisesByUserId(Long userId);
    List<AdvertiseDTO> getAdvertisesByStatus(String status);
    List<AdvertiseDTO> getAdvertisesByType(String type);
    List<AdvertiseDTO> searchAdvertises(String title);
}