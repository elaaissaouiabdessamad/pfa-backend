package com.example.pfabackend.service;

import com.example.pfabackend.entities.Client;
import com.example.pfabackend.entities.FeedBack;
import com.example.pfabackend.entities.Restaurant;
import com.example.pfabackend.repository.ClientRepository;
import com.example.pfabackend.repository.FeedBackRepository;
import com.example.pfabackend.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.util.List;

@Service
public class FeedBackService {

    @Autowired
    private  FeedBackRepository feedBackRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private ClientRepository clientRepository;



    public List<FeedBack> getAllFeedBacks() {
        return feedBackRepository.findAll();
    }

    public List<FeedBack> getAllFeedBacksForRestaurant(Long restaurantId) {
        return feedBackRepository.findByRestaurantId(restaurantId);
    }

    public List<FeedBack> getAllFeedBacksForClient(Long clientId) {
        return feedBackRepository.findByClientId(clientId);
    }

    public FeedBack postFeedBack(Long restaurantId, Long clientId, FeedBack feedBack) {
        // Assuming feedBack already has the client and restaurant objects set correctly
        Restaurant restaurant=restaurantRepository.findById(restaurantId).get();
        Client client=clientRepository.findById(clientId).get();
        feedBack.setRestaurant(restaurant);
        feedBack.setClient(client);
        return feedBackRepository.save(feedBack);
    }

    public void deleteFeedBack(Long feedBackId) {
        feedBackRepository.deleteById(feedBackId);
    }
}
