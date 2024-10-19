package com.example.pfabackend.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ParticipationId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    public ParticipationId() {
    }

    public ParticipationId(Long clientId, Long restaurantId) {
        this.clientId = clientId;
        this.restaurantId = restaurantId;
    }


    // Equals and hashCode implementation
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipationId that = (ParticipationId) o;
        return Objects.equals(clientId, that.clientId) &&
                Objects.equals(restaurantId, that.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, restaurantId);
    }
}
