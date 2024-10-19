package com.example.pfabackend;

import com.example.pfabackend.entities.Restaurant;
import com.example.pfabackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class PfaBackendApplication implements CommandLineRunner {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ParticipationRepository participationRepository;
    @Autowired
    private WaiterRepository waiterRepository;
    @Autowired
    private OwnerRepository ownerRepository;


    public static void main(String[] args) {
        SpringApplication.run(PfaBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


       // clientRepository.deleteAll();
        //        waiterRepository.deleteAll();
        //        ownerRepository.deleteAll();
        //        restaurantRepository.deleteAll();
       // restaurantRepository.save(new Restaurant(null,"B13","tilila agadir","0606060066",null,null,null,null));
        //        restaurantRepository.save(new Restaurant(null,"mister chef","tilila agadir","0654327890",null,null,null,null));
        //        restaurantRepository.save(new Restaurant(null,"food station","hay assalam agadir","0698765432",null,null,null,null));
        //
        //        List<Restaurant> restaurants = restaurantRepository.findAll();
        //        restaurants.forEach(r->{
        //            System.out.println(r.toString());
        //        });
        //
        //        Restaurant restaurant= restaurantRepository.findById((Long.valueOf(9))).get();
        //        System.out.println(restaurant.toString());

    }
}
