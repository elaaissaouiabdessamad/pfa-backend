package com.example.pfabackend.Controllers;

import com.example.pfabackend.dto.FeedBackDTO;
import com.example.pfabackend.entities.FeedBack;
import com.example.pfabackend.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/feedback")
public class FeedBackController {

    private final FeedBackService feedBackService;

    @Autowired
    public FeedBackController(FeedBackService feedBackService) {
        this.feedBackService = feedBackService;
    }

    /*@GetMapping
    public List<FeedBack> getAllFeedBacks() {
        return feedBackService.getAllFeedBacks();
    }*/
    @GetMapping
    public List<FeedBackDTO> getAllFeedBacks() {
        List<FeedBack> feedBacks = feedBackService.getAllFeedBacks();
        return feedBacks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<FeedBack> getAllFeedBacksForRestaurant(@PathVariable Long restaurantId) {
        return feedBackService.getAllFeedBacksForRestaurant(restaurantId);
    }

    @GetMapping("/client/{clientId}")
    public List<FeedBack> getAllFeedBacksForClient(@PathVariable Long clientId) {
        return feedBackService.getAllFeedBacksForClient(clientId);
    }

    @PostMapping("/restaurant/{restaurantId}/client/{clientId}")
    public FeedBack postFeedBack(@PathVariable Long restaurantId, @PathVariable Long clientId, @RequestBody FeedBack feedBack) {
        return feedBackService.postFeedBack(restaurantId, clientId, feedBack);
    }

    @DeleteMapping("/{feedBackId}")
    public void deleteFeedBack(@PathVariable Long feedBackId) {
        feedBackService.deleteFeedBack(feedBackId);
    }

    private FeedBackDTO convertToDTO(FeedBack feedBack) {
        FeedBackDTO dto = new FeedBackDTO();
        dto.setId(feedBack.getId());
        dto.setDescription(feedBack.getDescription());
        dto.setRating(feedBack.getRating());
        return dto;
    }
}
