package com.example.pfabackend.dto;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ParticipationDTO {
    private Long clientId;
    private Long restaurantId;
    private int points;

    // Constructors, getters, setters
    public ParticipationDTO() {}

    public ParticipationDTO(Long clientId, Long restaurantId, int points) {
        this.clientId = clientId;
        this.restaurantId = restaurantId;
        this.points = points;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}

