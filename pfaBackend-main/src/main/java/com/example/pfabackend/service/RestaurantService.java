package com.example.pfabackend.service;

import com.example.pfabackend.entities.Owner;
import com.example.pfabackend.entities.Restaurant;
import com.example.pfabackend.payload.response.MessageResponse;
import com.example.pfabackend.repository.OwnerRepository;
import com.example.pfabackend.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RestaurantService {

    private final Path root = Paths.get("uploads/restaurant");

    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OwnerService ownerService;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        return optionalRestaurant.orElse(null);
    }

    public void createRestaurant(Restaurant restaurant, MultipartFile logoFile, MultipartFile coverFile, String ownerId) {
        init();
        String randomLogoFileName = UUID.randomUUID().toString();
        String fileLogoExtension = StringUtils.getFilenameExtension(logoFile.getOriginalFilename());
        String combinedLogoFileName = randomLogoFileName + "." + fileLogoExtension;
        saveRestaurantImage(logoFile, combinedLogoFileName);

        String randomCoverFileName = UUID.randomUUID().toString();
        String fileCoverExtension = StringUtils.getFilenameExtension(coverFile.getOriginalFilename());
        String combinedCoverFileName = randomCoverFileName + "." + fileCoverExtension;
        saveRestaurantImage(coverFile, combinedCoverFileName);

        System.out.println("Restaurant Created !");
        System.out.println("Email: "+restaurant.getEmail()+" ,desc: "+restaurant.getDescription()+" , id: "+ restaurant.getId());

        restaurant.setLogoUrl(combinedLogoFileName);
        restaurant.setCoverImageUrl(combinedCoverFileName);
        restaurant.setLikes(0);
        restaurant.setRating(0);
        Restaurant restaurantSaved = restaurantRepository.save(restaurant);

        Optional<Owner> optionalOwner = ownerService.getOwnerById(Long.parseLong(ownerId));
        Owner owner = optionalOwner.orElse(null);
        if (owner != null) {
            owner.setRestaurant(restaurantSaved);
            ownerService.updateOwner(Long.parseLong(ownerId), owner);
        } else {
            System.out.println("No owner found");
        }
    }

    public void updateRestaurant(Long id, MultipartFile logoFile, MultipartFile coverFile, Restaurant updatedRestaurant) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            if (updatedRestaurant.getName() != null) {
                restaurant.setName(updatedRestaurant.getName());
            }
            if (logoFile == null || logoFile.isEmpty()) {
                System.out.println("No logo image found");
            }
            else{
                init();
                String randomLogoFileName = UUID.randomUUID().toString();
                String fileLogoExtension = StringUtils.getFilenameExtension(logoFile.getOriginalFilename());
                String combinedLogoFileName = randomLogoFileName + "." + fileLogoExtension;
                saveRestaurantImage(logoFile, combinedLogoFileName);
                restaurant.setLogoUrl(combinedLogoFileName);
            }
            if (coverFile == null || coverFile.isEmpty()) {
                System.out.println("No logo image found");
            }
            else
            {
                init();
                String randomCoverFileName = UUID.randomUUID().toString();
                String fileCoverExtension = StringUtils.getFilenameExtension(coverFile.getOriginalFilename());
                String combinedCoverFileName = randomCoverFileName + "." + fileCoverExtension;
                saveRestaurantImage(coverFile, combinedCoverFileName);
                restaurant.setCoverImageUrl(combinedCoverFileName);
            }
            if (updatedRestaurant.getCuisine() != null) {
                restaurant.setCuisine(updatedRestaurant.getCuisine());
            }
            if (updatedRestaurant.getRating() != 0) {
                restaurant.setRating(updatedRestaurant.getRating());
            }
            if (updatedRestaurant.getDescription() != null) {
                restaurant.setDescription(updatedRestaurant.getDescription());
            }
            if (updatedRestaurant.getInstagram() != null) {
                restaurant.setInstagram(updatedRestaurant.getInstagram());
            }
            if (updatedRestaurant.getPhoneNumber() != null) {
                restaurant.setPhoneNumber(updatedRestaurant.getPhoneNumber());
            }
            if (updatedRestaurant.getEmail() != null) {
                restaurant.setEmail(updatedRestaurant.getEmail());
            }
            if (updatedRestaurant.getLikes() != 0) {
                restaurant.setLikes(updatedRestaurant.getLikes());
            }
            if (updatedRestaurant.getPriceRange() != null) {
                restaurant.setPriceRange(updatedRestaurant.getPriceRange());
            }
            if (updatedRestaurant.getLocation() != null) {
                if (updatedRestaurant.getLocation().getLatitude() != 0) {
                    restaurant.getLocation().setLatitude(updatedRestaurant.getLocation().getLatitude());
                }
                if (updatedRestaurant.getLocation().getLongitude() != 0) {
                    restaurant.getLocation().setLongitude(updatedRestaurant.getLocation().getLongitude());
                }
            }
            restaurantRepository.save(restaurant);
        }
    }

    public void saveRestaurantImage(MultipartFile file, String filename) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(filename));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    public Restaurant getRestaurantByOwnerId(Long id) {
        Optional<Owner> optionalOwner = ownerService.getOwnerById(id);
        if(optionalOwner.isPresent()) {
            Owner owner = optionalOwner.get();
            Restaurant restaurant = owner.getRestaurant();
            return restaurant;
        }
        else{
            return null;
        }
    }
}
