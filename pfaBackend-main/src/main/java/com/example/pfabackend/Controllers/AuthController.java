package com.example.pfabackend.Controllers;

import com.example.pfabackend.entities.*;
import com.example.pfabackend.payload.request.LoginRequest;
import com.example.pfabackend.payload.request.SignupRequest;
import com.example.pfabackend.payload.response.JwtResponse;
import com.example.pfabackend.payload.response.MessageResponse;
import com.example.pfabackend.repository.RoleRepository;
import com.example.pfabackend.repository.UserRepository;
import com.example.pfabackend.security.jwt.JwtUtils;
import com.example.pfabackend.security.services.UserDetailsImpl;
import com.example.pfabackend.service.ClientService;
import com.example.pfabackend.service.OwnerService;
import com.example.pfabackend.service.RestaurantService;
import com.example.pfabackend.service.WaiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RestaurantService restaurantService;


  @Autowired
  OwnerService ownerService;

  @Autowired
  ClientService clientService;

  @Autowired
  WaiterService waiterService;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;


  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));


    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      // Check if the user's role is not ROLE_ADMIN
      if (userDetails.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
        String jwt = jwtUtils.generateJwtToken(authentication);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getOwnerId(),
                userDetails.getClientId(),
                userDetails.getWaiterId(),
                roles
        ));
      } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Account role must be owner not client or waiter."));
      }
    } catch (AuthenticationException e) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Invalid username or password"));
    }
  }


  @PostMapping("/client/signin")
  public ResponseEntity<?> authenticateClient(@Valid @RequestBody LoginRequest loginRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));


      SecurityContextHolder.getContext().setAuthentication(authentication);

      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      if (userDetails.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_USER"))) {
        String jwt = jwtUtils.generateJwtToken(authentication);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getOwnerId(),
                userDetails.getClientId(),
                userDetails.getWaiterId(),
                roles
        ));
      } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Account role must be client not owner or waiter."));
      }
    } catch (AuthenticationException e) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Invalid username or password"));
    }
  }


  @PostMapping("/waiter/signin")
  public ResponseEntity<?> authenticateWaiter(@Valid @RequestBody LoginRequest loginRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));


      SecurityContextHolder.getContext().setAuthentication(authentication);

      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      if (userDetails.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_MODERATOR"))) {
        String jwt = jwtUtils.generateJwtToken(authentication);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getOwnerId(),
                userDetails.getClientId(),
                userDetails.getWaiterId(),
                roles
        ));
      } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Account role must be waiter not owner or client."));
      }
    } catch (AuthenticationException e) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Invalid username or password"));
    }
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    User user = new User(signUpRequest.getUsername(),
               signUpRequest.getEmail(),
               encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();
    AtomicReference<String> userType= new AtomicReference<>("");
    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
      Client client = new Client(
              null,
              signUpRequest.getName(),
              signUpRequest.getEmail(),
              signUpRequest.getPhone(),
              null,
              null
      );
      Client savedClient = clientService.createClient(client);
      userType.set("Client");
      user.setRoles(roles);
      user.setClientId(savedClient.getId());
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);
          Owner owner = new Owner(
                  null,
                  signUpRequest.getName(),
                  signUpRequest.getEmail(),
                  signUpRequest.getPhone(),
                  null
          );
          Owner savedOwner = ownerService.createOwner(owner);
          userType.set("ADMIN");
          user.setOwnerId(savedOwner.getId());
          break;
        case "mod":
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);
          Waiter waiter = new Waiter(
                  null,
                  signUpRequest.getName(),
                  null,
                  signUpRequest.getPhone(),
                  signUpRequest.getEmail(),
                  null
          );
          Waiter savedWaiter = waiterService.createWaiterForRestaurant(signUpRequest.getRestaurantId(),waiter);
          userType.set("Waiter");
          user.setWaiterId(savedWaiter.getId());

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
          Client client = new Client(
                  null,
                  signUpRequest.getName(),
                  signUpRequest.getEmail(),
                  signUpRequest.getPhone(),
                  null,
                  null
          );
          Client savedClient = clientService.createClient(client);
          userType.set("Client");
          user.setClientId(savedClient.getId());
        }
      });


    user.setRoles(roles);
    }

    userRepository.save(user);
    return ResponseEntity.ok(new MessageResponse("User of type : "+ userType.get() +" registered successfully!"));

  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest request) {
    SecurityContextHolder.clearContext();
    return ResponseEntity.ok(new MessageResponse("Logout successful"));
  }

}
