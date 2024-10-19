package com.example.pfabackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "participations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participation {

    @EmbeddedId
    private ParticipationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("clientId") // Map clientId attribute of ParticipationId to client_id column
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("restaurantId") // Map restaurantId attribute of ParticipationId to restaurant_id column
    private Restaurant restaurant;

    @Column(name = "points")
    private int points;

}
