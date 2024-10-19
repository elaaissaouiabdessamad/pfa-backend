package com.example.pfabackend.dto;

import lombok.Data;

@Data
public class FeedBackDTO {
    private Long id;
    private String description;
    private int rating;
}