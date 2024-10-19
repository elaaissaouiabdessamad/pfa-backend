package com.example.pfabackend.Controllers;

import com.example.pfabackend.entities.Reward;
import com.example.pfabackend.payload.response.MessageResponse;
import com.example.pfabackend.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reward")
public class RewardController {
    private final RewardService rewardService;
    @Autowired
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }
    @GetMapping
    public ResponseEntity<List<Reward>> getAllRewards() {
        List<Reward> rewards = rewardService.getAllRewards();
        return ResponseEntity.ok(rewards);
    }
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Reward>> getAllRewardsForRestaurant(@PathVariable Long restaurantId) {
        List<Reward> rewards = rewardService.getAllRewardsForRestaurant(restaurantId);
        return ResponseEntity.ok(rewards);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Reward> getRewardByProductId(@PathVariable Long productId) {
        Optional<Reward> reward = rewardService.getRewardByProductId(productId);
        return reward.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<?> createOrUpdateReward(@PathVariable Long productId, @RequestBody Reward reward) {
        Reward createdReward = rewardService.createOrUpdateReward(productId, reward);
        if (createdReward != null) {
            return ResponseEntity.ok(new MessageResponse("Reward of number of points "+ createdReward.getRequiredPoints() +" created successfully of the product "+ createdReward.getProduct().getName()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Reward is empty !!!"));
        }
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<?> UpdateReward(@PathVariable Long productId, @RequestBody Reward reward) {
        Reward updatedReward = rewardService.createOrUpdateReward(productId, reward);
        if (updatedReward != null) {
            return ResponseEntity.ok(new MessageResponse("Reward of number of points "+ updatedReward.getRequiredPoints() +" updated successfully of the product "+ updatedReward.getProduct().getName()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Reward is empty !!!"));
        }
    }

    @DeleteMapping("/{rewardId}")
    public ResponseEntity<Void> deleteReward(@PathVariable Long rewardId) {
        rewardService.deleteReward(rewardId);
        return ResponseEntity.noContent().build();
    }
}
