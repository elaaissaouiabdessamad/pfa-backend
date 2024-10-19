package com.example.pfabackend.Controllers;

import com.example.pfabackend.dto.ParticipationConverter;
import com.example.pfabackend.dto.ParticipationDTO;
import com.example.pfabackend.entities.FeedBack;
import com.example.pfabackend.entities.Participation;
import com.example.pfabackend.payload.response.MessageResponse;
import com.example.pfabackend.service.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participation")
public class ParticipationController {

    private final ParticipationService participationService;
    @Autowired
    private ParticipationConverter participationConverter;

    @Autowired
    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @GetMapping
    public ResponseEntity<List<ParticipationDTO>> getAllParticipations() {
        List<Participation> participations = participationService.getAllParticipations();
        List<ParticipationDTO> participationDTOs = participationConverter.convertToDtoList(participations);
        return ResponseEntity.ok(participationDTOs);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<ParticipationDTO>> getAllParticipationsForRestaurant(@PathVariable Long restaurantId) {
        List<Participation> participations = participationService.getAllParticipationsForRestaurant(restaurantId);
        List<ParticipationDTO> participationDTOs = participationConverter.convertToDtoList(participations);
        return ResponseEntity.ok(participationDTOs);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ParticipationDTO>> getAllParticipationsForClient(@PathVariable Long clientId) {
        List<Participation> participations = participationService.getAllParticipationsForClient(clientId);
        List<ParticipationDTO> participationDTOs = participationConverter.convertToDtoList(participations);
        return ResponseEntity.ok(participationDTOs);
    }

    @PostMapping
    public ResponseEntity<ParticipationDTO> createParticipation(@RequestBody ParticipationDTO participationDTO) {
        Participation createdParticipation = participationService.createParticipation(
                participationDTO.getClientId(),
                participationDTO.getRestaurantId(),
                participationDTO.getPoints()
        );
        ParticipationDTO createdParticipationDTO = participationConverter.convertToDto(createdParticipation);
        return ResponseEntity.ok(createdParticipationDTO);
    }

    @DeleteMapping("/{clientId}/{restaurantId}")
    public ResponseEntity<?> deleteParticipation(@PathVariable Long clientId, @PathVariable Long restaurantId) {
        participationService.deleteParticipation(clientId, restaurantId);
        return ResponseEntity.ok(new MessageResponse("Participation with clientId " + clientId + " and restaurantId " + restaurantId + " deleted successfully."));
    }

    @PutMapping("/add-points/{clientId}/{restaurantId}/{pointsToAdd}")
    public ResponseEntity<?> addPoints(@PathVariable Long clientId, @PathVariable Long restaurantId, @PathVariable int pointsToAdd) {
        participationService.addPoints(clientId, restaurantId, pointsToAdd);
        return ResponseEntity.ok(new MessageResponse("Points added successfully!"));
    }

    @PutMapping("/reduce-points/{clientId}/{restaurantId}/{pointsToReduce}")
    public ResponseEntity<?> reducePoints(@PathVariable Long clientId, @PathVariable Long restaurantId, @PathVariable int pointsToReduce) {
        participationService.reducePoints(clientId, restaurantId, pointsToReduce);
        return ResponseEntity.ok(new MessageResponse("Points reduced successfully!"));
    }
}
