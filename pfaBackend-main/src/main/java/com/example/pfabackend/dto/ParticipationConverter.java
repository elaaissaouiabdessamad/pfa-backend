package com.example.pfabackend.dto;

import com.example.pfabackend.entities.Participation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class ParticipationConverter {
    public ParticipationDTO convertToDto(Participation participation) {
        return new ParticipationDTO(
                participation.getClient().getId(),
                participation.getRestaurant().getId(),
                participation.getPoints()
        );
    }

    public List<ParticipationDTO> convertToDtoList(List<Participation> participations) {
        return participations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
