package com.example.pfabackend.service;

import com.example.pfabackend.entities.Product;
import com.example.pfabackend.entities.Reward;
import com.example.pfabackend.repository.ProductRepository;
import com.example.pfabackend.repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RewardService {

    private final RewardRepository rewardRepository;
    private final ProductRepository productRepository;

    @Autowired
    public RewardService(RewardRepository rewardRepository, ProductRepository productRepository) {
        this.rewardRepository = rewardRepository;
        this.productRepository = productRepository;
    }

    public List<Reward> getAllRewards() {
        return rewardRepository.findAll();
    }

    public List<Reward> getAllRewardsForRestaurant(Long restaurantId) {
        return rewardRepository.findAllByRestaurantId(restaurantId);
    }

    public Optional<Reward> getRewardByProductId(Long productId) {
        return rewardRepository.findByProductId(productId);
    }

    public Reward createOrUpdateReward(Long productId, Reward reward) {
        Optional<Reward> existingReward = rewardRepository.findByProductId(productId);

        if (existingReward.isPresent()) {
            Reward currentReward = existingReward.get();
            currentReward.setRequiredPoints(reward.getRequiredPoints());
            currentReward.setProductCollection(reward.getProductCollection());
            return rewardRepository.save(currentReward);
        } else {
            if (productRepository.existsById(productId)) {
                Product product = productRepository.findById(productId).get();
                reward.setProduct(product);
                return rewardRepository.save(reward);
            } else {
                // Handle case when product does not exist
                return null;
            }
        }
    }

    public void deleteReward(Long rewardId) {
        rewardRepository.deleteById(rewardId);
    }
}
