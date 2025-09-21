package lk.ijse.backend.controller;

import lk.ijse.backend.dto.AdvertiseDTO;
import lk.ijse.backend.service.AdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/v1/advertise")
public class AdvertiseController {

    @Autowired
    private AdvertiseService advertiseService;

    @GetMapping
    public ResponseEntity<List<AdvertiseDTO>> getAllAdvertises() {
        try {
            List<AdvertiseDTO> advertises = advertiseService.getAllAdvertises();
            return new ResponseEntity<>(advertises, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<AdvertiseDTO> saveAdvertise(@RequestBody AdvertiseDTO advertiseDTO) {
        try {
            AdvertiseDTO savedAdvertise = advertiseService.saveAdvertise(advertiseDTO);
            return new ResponseEntity<>(savedAdvertise, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvertiseDTO> updateAdvertise(@PathVariable Long id, @RequestBody AdvertiseDTO advertiseDTO) {
        try {
            advertiseDTO.setId(id);
            AdvertiseDTO updatedAdvertise = advertiseService.updateAdvertise(advertiseDTO);
            return new ResponseEntity<>(updatedAdvertise, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdvertise(@PathVariable Long id) {
        try {
            AdvertiseDTO dto = new AdvertiseDTO();
            dto.setId(id);
            String result = advertiseService.deleteAdvertise(dto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting advertisement", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AdvertiseDTO>> getAdvertisesByUser(@PathVariable Long userId) {
        try {
            List<AdvertiseDTO> advertises = advertiseService.getAdvertisesByUserId(userId);
            return new ResponseEntity<>(advertises, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}