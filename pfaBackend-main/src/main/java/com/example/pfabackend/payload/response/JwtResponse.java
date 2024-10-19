package com.example.pfabackend.payload.response;

import java.util.List;

public class JwtResponse {
  private Long ownerId;
  private Long clientId;
  private Long waiterId;

  private String token;
  private String type = "Bearer";
  private Long id;
  private String username;
  private String email;
  private List<String> roles;

  public JwtResponse(String accessToken, Long id, String username, String email, Long ownerId, Long clientId, Long waiterId, List<String> roles) {
    this.token = accessToken;
    this.id = id;
    this.username = username;
    this.email = email;
    this.ownerId = ownerId;
    this.clientId = clientId;
    this.waiterId = waiterId;
    this.roles = roles;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public void setOwnerId(Long ownerId) {
    this.ownerId = ownerId;
  }

  public void setClientId(Long clientId) {
    this.clientId = clientId;
  }

  public void setWaiterId(Long waiterId) {
    this.waiterId = waiterId;
  }

  public Long getOwnerId() {
    return ownerId;
  }

  public Long getClientId() {
    return clientId;
  }

  public Long getWaiterId() {
    return waiterId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<String> getRoles() {
    return roles;
  }
}
